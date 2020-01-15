package io.cjf.jpaywepayback.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cjf.jpaywepayback.client.WepayService;
import io.cjf.jpaywepayback.dto.PrepayDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.Date;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private WepayService wepayService;

    @Value("${wepay.appId}")
    private String appId;

    @Value("${wepay.payKey}")
    private String payKey;

    @GetMapping(value = "/getPrepay", produces = MediaType.APPLICATION_XML_VALUE)
    public PrepayDTO getPrepay(@RequestParam(required = false, defaultValue = "1") Integer amount,
                               @RequestParam(required = false, defaultValue = "MD5") String signType,
                               @RequestParam Long timestamp,
                               @RequestParam String nonce,
                               @RequestParam String openid) throws JsonProcessingException {
        String orderId = appId + "N" + new Date().getTime();
        String title = "订单商品" + orderId;
        JSONObject jsonObject = wepayService.payUnifiedOrder(orderId, amount, title, "JSAPI", openid);
        String prepay_id = jsonObject.getString("prepay_id");
        String packageStr = "prepay_id=" + prepay_id;
        String toSign = "appid=" + appId +
                "&nonceStr=" + nonce +
                "&package=" + packageStr +
                "&signType=" + signType +
                "&timeStamp=" + timestamp +
                "&key=" + payKey;
        String paySign = DigestUtils.md5DigestAsHex(toSign.getBytes()).toUpperCase();
        PrepayDTO prepayDTO = new PrepayDTO();
        prepayDTO.setPrepayId(prepay_id);
        prepayDTO.setPaySign(paySign);

        return prepayDTO;
    }

}
