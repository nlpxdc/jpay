package io.cjf.jpaywepayback.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WechatServiceImpl implements WechatService {

    @Autowired
    private WechatApi wechatApi;

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.appKey}")
    private String appKey;

    private String accessToken;

    @Override
    public JSONObject getAppAccessToken() {
        String jsonStr = wechatApi.getAppAccessToken("client_credential", appId, appKey);
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        return jsonObject;
    }

    @Override
    public JSONObject getJsTicket() {
        if (accessToken == null){
            JSONObject appAccessTokenJson = getAppAccessToken();
            accessToken = appAccessTokenJson.getString("access_token");
        }
        String jsonStr = wechatApi.getJsTicket(accessToken, "jsapi");
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        return jsonObject;
    }
}
