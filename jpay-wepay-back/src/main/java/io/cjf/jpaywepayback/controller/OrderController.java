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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/order")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WepayService wepayService;

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wepay.mchId}")
    private String mchId;

    @Value("${wepay.payKey}")
    private String payKey;

    @PostMapping(value = "/getJsapiPrepay", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getJsapiPrepay(@RequestParam(required = false, defaultValue = "1") Integer amount,
                                 @RequestParam(value = "signType", required = false, defaultValue = "MD5") String frontSignType,
                                 @RequestParam Long timestamp,
                                 @RequestParam String nonce,
                                 @RequestParam String openid) throws JsonProcessingException, IllegalAccessException {
        String orderId = appId + "N" + new Date().getTime();
        String title = "订单商品" + orderId;
        JSONObject jsonObject = wepayService.payUnifiedOrder(orderId, amount, title, "JSAPI", openid, null);
        String prepay_id = jsonObject.getString("prepay_id");
        String packageStr = "prepay_id=" + prepay_id;
        String frontToSign = "appId=" + appId +
                "&nonceStr=" + nonce +
                "&package=" + packageStr +
                "&signType=" + frontSignType +
                "&timeStamp=" + timestamp +
                "&key=" + payKey;
        logger.info("frontToSign: {}", frontToSign);
        String frontPaySign = DigestUtils.md5Hex(frontToSign).toUpperCase();
        PrepayDTO prepayDTO = new PrepayDTO();
        prepayDTO.setPrepayId(prepay_id);
        prepayDTO.setPaySign(frontPaySign);
        String jsonString = JSON.toJSONString(prepayDTO);

        return jsonString;
    }

    @PostMapping(value = "/getNativePrepay", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getNativePrepay(@RequestParam(required = false, defaultValue = "1") Integer amount,
                                  @RequestParam(required = false, defaultValue = "multipleProductIds") String productId) throws JsonProcessingException, IllegalAccessException {
        String orderId = appId + "N" + new Date().getTime();
        String title = "订单商品" + orderId;
        final JSONObject jsonObject = wepayService.payUnifiedOrder(orderId, amount, title, "NATIVE", null, productId);
        final String jsonString = jsonObject.toJSONString();
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

    @PostMapping(value = "/refund", produces = MediaType.APPLICATION_JSON_VALUE)
    public String refund(@RequestParam String orderId,
                         @RequestParam Integer totalFee,
                         @RequestParam String refundId,
                         @RequestParam Integer refundFee) throws Exception {
        if (refundFee > totalFee) {
            throw new Exception("refund fee is larger than total fee");
        }
        JSONObject jsonObject = wepayService.payRefund(orderId, totalFee, refundId, refundFee);
        String jsonString = jsonObject.toJSONString();
        return jsonString;
    }

    @GetMapping(value = "/getRefundInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRefundInfo(@RequestParam String orderId) throws JsonProcessingException, IllegalAccessException {
        final JSONObject jsonObject = wepayService.payRefundQuery(orderId);
        final String jsonString = jsonObject.toJSONString();
        return jsonString;
    }

    @GetMapping("/downloadBill")
    public String downloadBill(@RequestParam String billDate) throws IllegalAccessException {
        final String result = wepayService.payDownloadBill(billDate);
        return result;
    }

    @GetMapping("/downloadFundflow")
    public String downloadFundflow(@RequestParam String billDate) throws IllegalAccessException {
        final String result = wepayService.payDownloadFundflow(billDate);
        return result;
    }

    @PostMapping(value = "/payCodePay", produces = MediaType.APPLICATION_JSON_VALUE)
    public String payCodePay(@RequestParam(required = false, defaultValue = "1") Integer amount,
                             @RequestParam String authcode) throws JsonProcessingException, IllegalAccessException {
        String orderId = appId + "N" + new Date().getTime();
        String title = "订单商品" + orderId;
        final JSONObject jsonObject = wepayService.payMicropay(orderId, amount, title, authcode);
        final String jsonString = jsonObject.toJSONString();
        return jsonString;
    }

    @PostMapping(value = "/payReverse", produces = MediaType.APPLICATION_JSON_VALUE)
    public String payReverse(@RequestParam String orderId) throws JsonProcessingException, IllegalAccessException {
        final JSONObject jsonObject = wepayService.payReverse(orderId);
        final String jsonString = jsonObject.toJSONString();
        return jsonString;
    }

    @GetMapping(value = "/authcodeToOpenid", produces = MediaType.APPLICATION_JSON_VALUE)
    public String authcodeToOpenid(@RequestParam String authcode) throws JsonProcessingException, IllegalAccessException {
        final JSONObject jsonObject = wepayService.authcodeToOpenid(authcode);
        final String jsonString = jsonObject.toJSONString();
        return jsonString;
    }

    @GetMapping(value = "/getSandboxPaykey", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSandboxPaykey() throws JsonProcessingException, IllegalAccessException {
        final JSONObject jsonObject = wepayService.getSandboxPaykey(mchId);
        final String jsonString = jsonObject.toJSONString();
        return jsonString;
    }

}
