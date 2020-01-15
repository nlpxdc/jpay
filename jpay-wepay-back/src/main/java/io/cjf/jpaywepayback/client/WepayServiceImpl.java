package io.cjf.jpaywepayback.client;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.cjf.jpaywepayback.dto.PayUnifiedOrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;

@Service
public class WepayServiceImpl implements WepayService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecureRandom secureRandom;

    @Autowired
    private XmlMapper xmlMapper;

    @Autowired
    private WepayApi wepayApi;

    @Value("${wepay.appId}")
    private String appId;

    @Value("${wepay.appKey}")
    private String appKey;

    @Value("${wepay.mchId}")
    private String mchId;

    @Value("${wepay.payKey}")
    private String payKey;

    @Value("${wepay.notifyUrl}")
    private String notifyUrl;

    @Override
    public JSONObject payUnifiedOrder(String orderId,
                                      Integer amount,
                                      String title,
                                      String type,
                                      String openid) throws JsonProcessingException {

        PayUnifiedOrderDTO payUnifiedOrderDTO = new PayUnifiedOrderDTO();
        payUnifiedOrderDTO.setAppid(appId);
        payUnifiedOrderDTO.setMch_id(mchId);
        payUnifiedOrderDTO.setBody(title);
        byte[] bytes = secureRandom.generateSeed(16);
        payUnifiedOrderDTO.setNonce_str(DatatypeConverter.printHexBinary(bytes));
        payUnifiedOrderDTO.setSpbill_create_ip("127.0.0.1");
        payUnifiedOrderDTO.setTrade_type(type);
        payUnifiedOrderDTO.setNotify_url(notifyUrl);
        payUnifiedOrderDTO.setOut_trade_no(orderId);
        payUnifiedOrderDTO.setTotal_fee(amount);
        payUnifiedOrderDTO.setOpenid(openid);
        payUnifiedOrderDTO.setSign_type("MD5");
        String toSign = payUnifiedOrderDTO.getToSign();
        toSign = toSign + "&key=" + payKey;
        String sign = DigestUtils.md5DigestAsHex(toSign.getBytes()).toUpperCase();
        payUnifiedOrderDTO.setSign(sign);
        String xml = wepayApi.payUnifiedOrder(payUnifiedOrderDTO);
        JSONObject jsonObject = xmlMapper.readValue(xml, JSONObject.class);

        return jsonObject;
    }
}
