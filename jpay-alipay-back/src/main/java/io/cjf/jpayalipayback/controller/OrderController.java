package io.cjf.jpayalipayback.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import io.cjf.jpayalipayback.dto.ApplyRefundDTO;
import io.cjf.jpayalipayback.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public String getOrderPayPage(@RequestParam Double amount,
                                  @RequestParam(required = false, defaultValue = "false") Boolean useCert) throws AlipayApiException {
        logger.info("get order pay page");

        //todo get order record by orderId
        String orderId = appId + "order" + new Date().getTime();
        logger.info("orderId: {}", orderId);
        String title = "订单支付" + orderId;
        String page = orderService.getOrderPayPage(orderId, amount, title, useCert);
        return page;
    }

    @GetMapping("/getPayResult")
    public AlipayTradeQueryResponse getPayResult(@RequestParam String orderId,
                                                 @RequestParam(required = false) String alipayTradeNo,
                                                 @RequestParam(required = false, defaultValue = "false") Boolean useCert) throws AlipayApiException {
        AlipayTradeQueryResponse payResult = orderService.getPayResult(orderId, alipayTradeNo, useCert);
        String body = payResult.getBody();
        logger.info("order description: {}", body);
        return payResult;
    }

    @PostMapping("/applyRefund")
    public AlipayTradeRefundResponse applyRefund(@RequestBody ApplyRefundDTO applyRefundDTO) throws AlipayApiException {
        String orderId = applyRefundDTO.getOrderId();
        String orderRefundId = applyRefundDTO.getOrderRefundId();
        Double amount = applyRefundDTO.getAmount();
        AlipayTradeRefundResponse response = orderService.applyRefund(orderId, orderRefundId, amount);
        String body = response.getBody();
        logger.info("apply refund body: {}", body);
        return response;
    }

    @GetMapping("/getRefundResult")
    public AlipayTradeFastpayRefundQueryResponse getRefundResult(String orderId,
                                                                 String orderRefundId) throws AlipayApiException {
        AlipayTradeFastpayRefundQueryResponse response = orderService.getRefundResult(orderId, orderRefundId);
        String body = response.getBody();
        logger.info("apply refund body: {}", body);
        return response;
    }

}
