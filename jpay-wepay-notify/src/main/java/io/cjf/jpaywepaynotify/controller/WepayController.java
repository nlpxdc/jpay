package io.cjf.jpaywepaynotify.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.cjf.jpaywepaynotify.dto.HandleResultDTO;
import io.cjf.jpaywepaynotify.dto.MyJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@RestController
@RequestMapping("/wepay")
public class WepayController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${wepay.payKey}")
    private String payKey;

    private static final String ALGORITHM = "AES";

    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS7Padding";

    @PostMapping(value = "/handlePayResult", produces = MediaType.APPLICATION_XML_VALUE)
    public HandleResultDTO handlePayResult(@RequestBody JSONObject payResult){
        logger.info("pay result: {}", payResult);

        HandleResultDTO handleResultDTO = new HandleResultDTO();
        handleResultDTO.setReturn_code("SUCCESS");
        handleResultDTO.setReturn_msg("OK");
        return handleResultDTO;
    }

    @PostMapping(value = "/handleRefundResult", produces = MediaType.APPLICATION_XML_VALUE)
    public MyJson handleRefundResult(@RequestBody JSONObject refundResult) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, JsonProcessingException {
        logger.info("refund result: {}", refundResult);
        final String req_info = refundResult.getString("req_info");
        final byte[] reqinfoBytes = Base64.getDecoder().decode(req_info);
        String md5Key = DigestUtils.md5DigestAsHex(payKey.getBytes());
        md5Key = md5Key.toLowerCase();

        SecretKeySpec key = new SecretKeySpec(md5Key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, key);
        final byte[] bytes = cipher.doFinal(reqinfoBytes);
        final String reqinfoText = new String(bytes, StandardCharsets.UTF_8);
        final XmlMapper xmlMapper = new XmlMapper();
        JSONObject jsonObject = xmlMapper.readValue(reqinfoText, JSONObject.class);
        logger.info("reqinfo text is: {}", jsonObject);

        final MyJson myJson = new MyJson();
        myJson.put("return_code", "SUCCESS");
        myJson.put("return_msg", "OK");
        return myJson;
    }
}
