package io.cjf.jpaywepayback.client;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface WepayService {

    JSONObject payUnifiedOrder(String orderId, Integer amount, String title, String payType, String openid, String productId) throws JsonProcessingException, IllegalAccessException;

    JSONObject payOrderQuery(String orderId) throws IllegalAccessException, JsonProcessingException;

    JSONObject payOrderClose(String orderId) throws IllegalAccessException, JsonProcessingException;

    JSONObject payRefund(String orderId, Integer totalFee, String refundId, Integer refundFee) throws IllegalAccessException, JsonProcessingException;

    JSONObject payRefundQuery(String orderId) throws IllegalAccessException, JsonProcessingException;

    String payDownloadBill(String billDate) throws IllegalAccessException;

    String payDownloadFundflow(String billDate) throws IllegalAccessException;

    JSONObject payMicropay(String orderId, Integer amount, String title, String authcode) throws IllegalAccessException, JsonProcessingException;

    JSONObject payReverse(String orderId) throws IllegalAccessException, JsonProcessingException;

    JSONObject authcodeToOpenid(String authcode) throws IllegalAccessException, JsonProcessingException;

    JSONObject getSandboxPaykey(String mchId) throws IllegalAccessException, JsonProcessingException;
}
