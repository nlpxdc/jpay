package io.cjf.jpaywepayback.client;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WepayServiceImplTest {

    @Autowired
    private WepayServiceImpl wepayService;

    @Value("${wepay.appId}")
    private String appId;

    @Test
    void payUnifiedOrder() {
        String orderId =  appId + "order" + new Date().getTime();
        Integer amount = 1;
        String title = "订单商品" + orderId;
        String clientIp = "121.76.196.44";
        JSONObject jsonObject = wepayService.payUnifiedOrder(orderId, amount, title, clientIp, "MWEB");
        assertNotNull(jsonObject);
    }
}