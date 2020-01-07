package io.cjf.jpayalipayback.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import io.cjf.jpayalipayback.client.AlipayCertClientImpl;
import io.cjf.jpayalipayback.client.AlipayClientImpl;
import io.cjf.jpayalipayback.dto.AlipayTradePagePayBizDTO;
import io.cjf.jpayalipayback.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    @Override
    public String getOrderPayPage(String orderId, Double amount, String title, Boolean userCert) throws AlipayApiException {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(returnUrl);
        String notifyUrl = userCert ? this.notifyCertUrl : this.notifyUrl;
        request.setNotifyUrl(notifyUrl);
        AlipayTradePagePayBizDTO bizDTO = new AlipayTradePagePayBizDTO();
        bizDTO.setOutTradeNo(orderId);
        bizDTO.setTotalAmount(amount);
        bizDTO.setSubject(title);
        request.setBizContent(JSON.toJSONString(bizDTO));

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
}
