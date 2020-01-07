package io.cjf.jpayalipayback.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import io.cjf.jpayalipayback.dto.PayCodePayDTO;
import io.cjf.jpayalipayback.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/f2f")
public class F2FController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    @Value("${alipay.appId}")
    private String appId;

    @PostMapping("/payCodePay")
    public AlipayTradePayResponse payCodePay(@RequestBody PayCodePayDTO payCodePayDTO) throws AlipayApiException {
        String orderId = appId + "order" + new Date().getTime();
        logger.info("orderId: {}", orderId);
        String title = "订单支付" + orderId;
        Double amount = payCodePayDTO.getAmount();
        Double discount = payCodePayDTO.getDiscount();
        String authcode = payCodePayDTO.getAuthcode();
        AlipayTradePayResponse response = orderService.payCodePay(orderId, title, amount, discount, authcode);
        return response;
    }

    @PostMapping("/cancelOrderPay")
    public AlipayTradeCancelResponse cancelOrderPay(@RequestParam String orderId) throws AlipayApiException {
        AlipayTradeCancelResponse response = orderService.cancelOrderPay(orderId);
        return response;
    }

}
