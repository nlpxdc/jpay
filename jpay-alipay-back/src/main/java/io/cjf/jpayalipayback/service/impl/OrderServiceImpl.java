package io.cjf.jpayalipayback.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
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

    @Value("${alipay.returnUrl}")
    private String returnUrl;

    @Value("${alipay.notifyUrl}")
    private String notifyUrl;

    @Override
    public String getOrderPayPage(String orderId, Double amount, String title) throws AlipayApiException {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(returnUrl);
        request.setNotifyUrl(notifyUrl);
        AlipayTradePagePayBizDTO bizDTO = new AlipayTradePagePayBizDTO();
        bizDTO.setOutTradeNo(orderId);
        bizDTO.setTotalAmount(amount);
        bizDTO.setSubject(title);
        request.setBizContent(JSON.toJSONString(bizDTO));

        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        String body = response.getBody();

        return body;
    }
}
