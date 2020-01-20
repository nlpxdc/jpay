package io.cjf.jpaywepayback.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cjf.jpaywepayback.client.WepayService;
import io.cjf.jpaywepayback.dto.PrepayDTO;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.util.DigestUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@RestController
@RequestMapping("/order")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WepayService wepayService;

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wepay.payKey}")
    private String payKey;

    @GetMapping(value = "/getPrepay", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPrepay(@RequestParam(required = false, defaultValue = "1") Integer amount,
                               @RequestParam(required = false, defaultValue = "MD5") String signType,
                               @RequestParam Long timestamp,
                               @RequestParam String nonce,
                               @RequestParam String openid) throws JsonProcessingException, UnsupportedEncodingException {
        String orderId = appId + "N" + new Date().getTime();
        String title = "订单商品" + orderId;
        JSONObject jsonObject = wepayService.payUnifiedOrder(orderId, amount, title, "JSAPI", openid);
        String prepay_id = jsonObject.getString("prepay_id");
        String packageStr = "prepay_id=" + prepay_id;
        String toSign = "appId=" + appId +
                "&nonceStr=" + nonce +
                "&package=" + packageStr +
                "&signType=" + signType +
                "&timeStamp=" + timestamp +
                "&key=" + payKey;
        logger.info("toSgin: {}", toSign);
        String paySign = DigestUtils.md5Hex(toSign).toUpperCase();
        PrepayDTO prepayDTO = new PrepayDTO();
        prepayDTO.setPrepayId(prepay_id);
        prepayDTO.setPaySign(paySign);
        String jsonString = JSON.toJSONString(prepayDTO);

        return jsonString;
    }

    @GetMapping(value = "/getPayOrderInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPayOrderInfo(@RequestParam String orderId) throws JsonProcessingException, IllegalAccessException {
        JSONObject jsonObject = wepayService.payOrderQuery(orderId);
        String jsonString = jsonObject.toJSONString();
        return jsonString;
    }

    @PostMapping(value = "/closePayOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public String closePayOrder(@RequestParam String orderId) throws JsonProcessingException, IllegalAccessException {
        JSONObject jsonObject = wepayService.payOrderClose(orderId);
        String jsonString = jsonObject.toJSONString();
        return jsonString;
    }

}
