package io.cjf.jpaywepayback.client;

import com.alibaba.fastjson.JSONObject;

public interface WepayService {

    JSONObject payUnifiedOrder(String orderId, Integer amount, String title, String clientIp, String type);

}
