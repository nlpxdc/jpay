package io.cjf.jpaywepayback.client;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    void payUnifiedOrder() throws JsonProcessingException {
        String orderId =  appId+"N"+new Date().getTime();
        Integer amount = 1;
        String title = "订单商品" + orderId;
        String openid = "oG_Lp1MOCClUT-2Q5LqOrfq-qcFg";
        JSONObject jsonObject = wepayService.payUnifiedOrder(orderId, amount, title, "JSAPI", openid);
        String prepay_id = jsonObject.getString("prepay_id");
        assertNotNull(prepay_id);
    }
}