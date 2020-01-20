package io.cjf.jpaywepayback.client;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface WepayService {

    JSONObject payUnifiedOrder(String orderId, Integer amount, String title, String type, String openid) throws JsonProcessingException;

    JSONObject payOrderQuery(String orderId) throws IllegalAccessException, JsonProcessingException;

    JSONObject payOrderClose(String orderId) throws IllegalAccessException, JsonProcessingException;
}
