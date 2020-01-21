package io.cjf.jpaywepayback.client;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.cjf.jpaywepayback.dto.PayUnifiedOrderDTO;
import io.cjf.jpaywepayback.util.WepayUtil;
import javafx.util.converter.DateStringConverter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WepayApiTest {

    @Autowired
    private WepayApi wepayApi;

    @Autowired
    private WepayUtil wepayUtil;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void payUnifiedOrder() throws JsonProcessingException, IllegalAccessException {
        PayUnifiedOrderDTO payUnifiedOrderDTO = new PayUnifiedOrderDTO();
        String appId = "wxba004d8c6d611e32";
        payUnifiedOrderDTO.setAppid(appId);
        payUnifiedOrderDTO.setMch_id("1563136541");
        payUnifiedOrderDTO.setBody("杰岱投资-理财产品（测试）");
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = secureRandom.generateSeed(16);
        payUnifiedOrderDTO.setNonce_str(DatatypeConverter.printHexBinary(bytes));
        payUnifiedOrderDTO.setSpbill_create_ip("127.0.0.1");
        payUnifiedOrderDTO.setTrade_type("JSAPI");
        payUnifiedOrderDTO.setNotify_url("http://cjf.qianmu.fun:8080/wepay/handlePayResult");
        String orderId = appId+"N"+new Date().getTime();
        payUnifiedOrderDTO.setOut_trade_no(orderId);
        payUnifiedOrderDTO.setTotal_fee(1);
        payUnifiedOrderDTO.setOpenid("oG_Lp1MOCClUT-2Q5LqOrfq-qcFg");
        payUnifiedOrderDTO.setSign_type("MD5");
        final String sign = wepayUtil.sign(payUnifiedOrderDTO);
        payUnifiedOrderDTO.setSign(sign);
        String xml = wepayApi.payUnifiedOrder(payUnifiedOrderDTO);
        XmlMapper xmlMapper = new XmlMapper();
        JSONObject jsonObject = xmlMapper.readValue(xml, JSONObject.class);
        assertNotNull(jsonObject);
        String prepay_id = jsonObject.getString("prepay_id");
        assertNotNull(prepay_id);
    }
}