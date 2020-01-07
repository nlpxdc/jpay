package io.cjf.jpayalipayback.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import io.cjf.jpayalipayback.client.AlipayCertClientImpl;
import io.cjf.jpayalipayback.client.AlipayClientImpl;
import io.cjf.jpayalipayback.dto.AlipayTradePagePayBizDTO;
import io.cjf.jpayalipayback.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AlipayClientImpl alipayClient;

    @Autowired
    private AlipayCertClientImpl alipayCertClient;

    @Value("${alipay.returnUrl}")
    private String returnUrl;

    @Value("${alipay.notifyUrl}")
    private String notifyUrl;

    @Value("${alipay.notifyCertUrl}")
    private String notifyCertUrl;

    @Value("${alipay.sellerId}")
    private String sellerId;

    @Value("${alipay.buyerId}")
    private String buyerId;

    @Override
    public String getOrderPayPage(String orderId, Double amount, String title, Boolean userCert) throws AlipayApiException {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(returnUrl);
        String notifyUrl = userCert ? this.notifyCertUrl : this.notifyUrl;
        request.setNotifyUrl(notifyUrl);

        JSONObject bizJson = new JSONObject();
        bizJson.put("product_code", "FAST_INSTANT_TRADE_PAY");
        bizJson.put("out_trade_no", orderId);
        bizJson.put("total_amount", amount);
        bizJson.put("subject", title);

        Date now = new Date();
        long nowTimestamp = now.getTime();
        long expireTimestamp = nowTimestamp + 30 * 1000;
        Date expireTime = new Date(expireTimestamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String expireTimeStr = simpleDateFormat.format(expireTime);
        bizJson.put("time_expire", expireTimeStr);
        request.setBizContent(bizJson.toJSONString());

        AlipayTradePagePayResponse response;
        if (userCert){
            response = alipayCertClient.pageExecute(request);
        }else {
            response = alipayClient.pageExecute(request);
        }

        String body = response.getBody();

        return body;
    }

    @Override
    public AlipayTradeQueryResponse getPayResult(String orderId, String alipayTradeNo, Boolean userCert) throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizJson = new JSONObject();
        bizJson.put("out_trade_no", orderId);
        bizJson.put("trade_no", alipayTradeNo);
        request.setBizContent(bizJson.toJSONString());

        AlipayTradeQueryResponse response;
        if (userCert){
            response = alipayCertClient.certificateExecute(request);
        }else {
            response = alipayClient.execute(request);
        }

        return response;
    }

    @Override
    public AlipayTradeRefundResponse applyRefund(String orderId, String orderRefundId, Double amount) throws AlipayApiException {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        JSONObject bizJson = new JSONObject();
        bizJson.put("out_trade_no", orderId);
        bizJson.put("out_request_no", orderRefundId);
        bizJson.put("refund_amount", amount);
        request.setBizContent(bizJson.toJSONString());

        AlipayTradeRefundResponse response = alipayCertClient.certificateExecute(request);

        return response;
    }

    @Override
    public AlipayTradeFastpayRefundQueryResponse getRefundResult(String orderId, String orderRefundId) throws AlipayApiException {
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        JSONObject bizJson = new JSONObject();
        bizJson.put("out_trade_no", orderId);
        bizJson.put("out_request_no", orderRefundId);
        request.setBizContent(bizJson.toJSONString());

        AlipayTradeFastpayRefundQueryResponse response = alipayCertClient.certificateExecute(request);

        return response;
    }

    @Override
    public AlipayTradeCloseResponse close(String orderId, String alipayTradeId) throws AlipayApiException {
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        JSONObject bizJson = new JSONObject();
        bizJson.put("out_trade_no", orderId);
        bizJson.put("trade_no", alipayTradeId);
        request.setBizContent(bizJson.toJSONString());

        AlipayTradeCloseResponse response = alipayCertClient.certificateExecute(request);

        return response;
    }

    @Override
    public AlipayTradeQueryResponse getAlipayTradeInfo(String orderId) throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizJson = new JSONObject();
        bizJson.put("out_trade_no", orderId);
        request.setBizContent(bizJson.toJSONString());

        AlipayTradeQueryResponse response = alipayCertClient.certificateExecute(request);

        return response;
    }

    @Override
    public AlipayTradeCreateResponse createAlipayTrade(String orderId, Double amount, String title) throws AlipayApiException {
        AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
        JSONObject bizJson = new JSONObject();
        bizJson.put("out_trade_no", orderId);
        bizJson.put("total_amount", amount.toString());
        bizJson.put("subject", title);
        bizJson.put("buyer_id", buyerId);
//        bizJson.put("seller_id", sellerId);
        request.setBizContent(bizJson.toJSONString());

        AlipayTradeCreateResponse response = alipayCertClient.certificateExecute(request);

        return response;
    }
}
