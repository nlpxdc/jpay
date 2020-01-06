package io.cjf.jpayalipayback.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.cjf.jpayalipayback.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/order")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    @Value("${alipay.appId}")
    private String appId;

    @GetMapping(value = "/getOrderPayPage", produces = MediaType.TEXT_HTML_VALUE)
    public String getOrderPayPage() throws AlipayApiException {
        logger.info("get order pay page");

        //todo get order record by orderId
        String orderId = appId + "order" + new Date().getTime();
        logger.info("orderId: {}", orderId);
        Double amount = 0.01;
        String title = "订单支付" + orderId;
        String page = orderService.getOrderPayPage(orderId, amount, title);
        return page;
    }

    @GetMapping("/getPayResult")
    public AlipayTradeQueryResponse getPayResult(@RequestParam String orderId,
                                                 @RequestParam(required = false) String alipayTradeNo) throws AlipayApiException {
        AlipayTradeQueryResponse payResult = orderService.getPayResult(orderId, alipayTradeNo);
        String body = payResult.getBody();
        logger.info("order description: {}", body);
        return payResult;
    }

}
