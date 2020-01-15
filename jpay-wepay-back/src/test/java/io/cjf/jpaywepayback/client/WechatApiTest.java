package io.cjf.jpaywepayback.client;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WechatApiTest {

    @Autowired
    private WechatApi wechatApi;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAppAccessToken() {
        String grant_type = "client_credential";
        String appid = "wxba004d8c6d611e32";
        String secret = "";
        JSONObject appAccessTokenJson = wechatApi.getAppAccessToken(grant_type, appid, secret);
        assertNotNull(appAccessTokenJson);
        String access_token = appAccessTokenJson.getString("access_token");
        assertNotNull(access_token);
    }

    @Test
    void getJsTicket() {
        String access_token = "29_RcSo9BK6MAkrtg8trg5IHz55qeCvo_SoGAfaLgXsuTLHnWZGG8TrWAJ0voAfbM_Ax1nTKLVjWqLCSTWpkC78f9ihOCO1Nn9cuekbacAzK2UlA5qzkpShR-M18lXJYmpiNmD0LMzsy6cFpw8tZLJbABAIUS";
        String type = "jsapi";
        JSONObject jsTicketJson = wechatApi.getJsTicket(access_token, type);
        String ticket = jsTicketJson.getString("ticket");
        assertNotNull(ticket);
    }
}