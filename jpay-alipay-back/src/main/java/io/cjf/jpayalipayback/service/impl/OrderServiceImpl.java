package io.cjf.jpayalipayback.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import io.cjf.jpayalipayback.client.AlipayCertClientImpl;
import io.cjf.jpayalipayback.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    private AlipayClientImpl alipayClient;

    @Autowired
    private AlipayCertClientImpl alipayCertClient;

    @Value("${alipay.returnUrl}")
    private String returnUrl;

    @Value("${alipay.notifyUrl}")
    private String notifyUrl;

    @Value("${alipay.sellerId}")
    private String sellerId;

    @Value("${alipay.buyerId}")
    private String buyerId;

    @Override
    public String getOrderPayPage(String orderId, Double amount, String title) throws AlipayApiException {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(returnUrl);
        request.setNotifyUrl(notifyUrl);

        JSONObject bizJson = new JSONObject();
        bizJson.put("product_code", "FAST_INSTANT_TRADE_PAY");
        bizJson.put("out_trade_no", orderId);
        bizJson.put("total_amount", amount);
        bizJson.put("subject", title);
        bizJson.put("timeout_express", "1m");
        request.setBizContent(bizJson.toJSONString());

        AlipayTradePagePayResponse response = alipayCertClient.pageExecute(request);
        String body = response.getBody();
        return body;
    }

    @Override
    public String getOrderPayWap(String orderId, Double amount, String title) throws AlipayApiException {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setReturnUrl(returnUrl);
        request.setNotifyUrl(notifyUrl);

        JSONObject bizJson = new JSONObject();
        bizJson.put("product_code", "QUICK_WAP_WAY");
        bizJson.put("out_trade_no", orderId);
        bizJson.put("total_amount", amount);
        bizJson.put("subject", title);
        bizJson.put("timeout_express", "1m");
        request.setBizContent(bizJson.toJSONString());

        AlipayTradeWapPayResponse response = alipayCertClient.pageExecute(request);
        String body = response.getBody();
        return body;
    }

    @Override
    public String getOrderPayApp(String orderId, Double amount, String title) throws AlipayApiException {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setReturnUrl(returnUrl);
        request.setNotifyUrl(notifyUrl);

        JSONObject bizJson = new JSONObject();
        bizJson.put("product_code", "QUICK_MSECURITY_PAY");
        bizJson.put("out_trade_no", orderId);
        bizJson.put("total_amount", amount);
        bizJson.put("subject", title);
        bizJson.put("timeout_express", "1m");
        request.setBizContent(bizJson.toJSONString());

        AlipayTradeAppPayResponse response = alipayCertClient.sdkExecute(request);
        String body = response.getBody();

        return body;
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
        String body = response.getBody();
        logger.info("alipay trade refund body: {}", body);

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
        String body = response.getBody();
        logger.info("alipay refund query body: {}", body);
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
        String body = response.getBody();
        logger.info("alipay trade close body: {}", body);
        return response;
    }

    @Override
    public AlipayTradeQueryResponse getAlipayTradeInfo(String orderId) throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizJson = new JSONObject();
        bizJson.put("out_trade_no", orderId);
        request.setBizContent(bizJson.toJSONString());

        AlipayTradeQueryResponse response = alipayCertClient.certificateExecute(request);
        String body = response.getBody();
        logger.info("alipay trade query body: {}", body);
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
        String body = response.getBody();
        logger.info("alipay trade create body: {}", body);
        return response;
    }

    @Override
    public AlipayDataDataserviceBillDownloadurlQueryResponse getBillUrl(String billType, String billDate) throws AlipayApiException {
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        JSONObject bizJson = new JSONObject();
        bizJson.put("bill_type", billType);
        bizJson.put("bill_date", billDate);
        request.setBizContent(bizJson.toJSONString());

        AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayCertClient.certificateExecute(request);
        String body = response.getBody();
        logger.info("alipay bill url body: {}", body);
        return response;

    }

}
