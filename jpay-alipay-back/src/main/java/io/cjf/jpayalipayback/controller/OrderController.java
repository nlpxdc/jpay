package io.cjf.jpayalipayback.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.*;
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
    public String getOrderPayPage(@RequestParam Double amount) throws AlipayApiException {
        logger.info("get order pay page");

        String orderId = appId + "order" + new Date().getTime();
        logger.info("orderId: {}", orderId);
        String title = "订单支付" + orderId;
        String page = orderService.getOrderPayPage(orderId, amount, title);
        return page;
    }

    @PostMapping("/applyRefund")
    public AlipayTradeRefundResponse applyRefund(@RequestBody ApplyRefundDTO applyRefundDTO) throws AlipayApiException {
        String orderId = applyRefundDTO.getOrderId();
        String orderRefundId = applyRefundDTO.getOrderRefundId();
        Double amount = applyRefundDTO.getAmount();
        AlipayTradeRefundResponse response = orderService.applyRefund(orderId, orderRefundId, amount);
        return response;
    }

    @GetMapping("/getRefundResult")
    public AlipayTradeFastpayRefundQueryResponse getRefundResult(String orderId,
                                                                 String orderRefundId) throws AlipayApiException {
        AlipayTradeFastpayRefundQueryResponse response = orderService.getRefundResult(orderId, orderRefundId);
        return response;
    }

    @GetMapping("/getAlipayTradeInfo")
    public AlipayTradeQueryResponse getAlipayTradeInfo(@RequestParam String orderId) throws AlipayApiException {
        AlipayTradeQueryResponse response = orderService.getAlipayTradeInfo(orderId);
        return response;
    }


    @PostMapping("/close")
    public AlipayTradeCloseResponse close(@RequestParam String orderId,
                                          @RequestParam(required = false) String alipayTradeId) throws AlipayApiException {
        AlipayTradeCloseResponse response = orderService.close(orderId, alipayTradeId);
        return response;
    }

    @PostMapping("/createAlipayTrade")
    public AlipayTradeCreateResponse createAlipayTrade(@RequestParam Double amount) throws AlipayApiException {
        String orderId = appId + "order" + new Date().getTime();
        logger.info("orderId: {}", orderId);
        String title = "订单支付" + orderId;

        AlipayTradeCreateResponse response = orderService.createAlipayTrade(orderId, amount, title);
        return response;
    }

    @GetMapping("/getBillUrl")
    public AlipayDataDataserviceBillDownloadurlQueryResponse getBillUrl(@RequestParam String billType,
                                                                        @RequestParam String billDate) throws AlipayApiException {
        AlipayDataDataserviceBillDownloadurlQueryResponse response = orderService.getBillUrl(billType, billDate);
        return response;
    }

}
