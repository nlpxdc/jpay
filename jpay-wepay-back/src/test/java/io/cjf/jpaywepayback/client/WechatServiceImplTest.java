package io.cjf.jpaywepayback.client;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WechatServiceImplTest {

    @Autowired
    private WechatService wechatService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAppAccessToken() {
        JSONObject appAccessTokenJson = wechatService.getAppAccessToken();
        assertNotNull(appAccessTokenJson);
        String appAccessToken = appAccessTokenJson.getString("access_token");
        assertNotNull(appAccessToken);
    }

    @Test
    void getJsTicket() throws Exception {
        JSONObject jsTicketJson = wechatService.getJsTicket();
        assertNotNull(jsTicketJson);
        String ticket = jsTicketJson.getString("ticket");
        assertNotNull(ticket);
    }
}