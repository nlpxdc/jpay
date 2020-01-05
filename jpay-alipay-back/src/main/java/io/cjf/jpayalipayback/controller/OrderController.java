package io.cjf.jpayalipayback.controller;

import com.alipay.api.AlipayApiException;
import io.cjf.jpayalipayback.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/order")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/getOrderPayPage", produces = MediaType.TEXT_HTML_VALUE)
    public String getOrderPayPage() throws AlipayApiException {
        logger.info("get order pay page");

        //todo get order record by orderId
        String orderId = "order"+new Date().getTime();
        Double amount = 0.01;
        String title = "订单支付"+orderId;
        String page = orderService.getOrderPayPage(orderId, amount, title);
        return page;
    }

}
