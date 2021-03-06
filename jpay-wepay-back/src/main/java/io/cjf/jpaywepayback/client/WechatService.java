package io.cjf.jpaywepayback.client;

import com.alibaba.fastjson.JSONObject;

public interface WechatService {
    JSONObject getAppAccessToken();

    JSONObject getJsTicket() throws Exception;

    JSONObject getUserAccessToken(String authcode);
}
