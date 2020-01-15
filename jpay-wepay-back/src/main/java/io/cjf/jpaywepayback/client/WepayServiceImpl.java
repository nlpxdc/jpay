package io.cjf.jpaywepayback.client;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jpaywepayback.dto.PayUnifiedOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;

@Service
public class WepayServiceImpl implements WepayService {

    @Autowired
    private WepayApi wepayApi;

    @Value("${wepay.appId}")
    private String appId;

    @Value("${wepay.appKey}")
    private String appKey;

    @Value("${wepay.mchId}")
    private String mchId;

    @Value("${wepay.notifyUrl}")
    private String notifyUrl;

    @Override
    public JSONObject payUnifiedOrder(String orderId, Integer amount, String title, String type) {
        PayUnifiedOrderDTO payUnifiedOrderDTO = new PayUnifiedOrderDTO();
        payUnifiedOrderDTO.setAppid(appId);
        payUnifiedOrderDTO.setMch_id(mchId);
        payUnifiedOrderDTO.setNonce_str(UUID.randomUUID().toString());
        payUnifiedOrderDTO.setBody(title);
        payUnifiedOrderDTO.setNotify_url(notifyUrl);
        payUnifiedOrderDTO.setOut_trade_no(orderId);
//        payUnifiedOrderDTO.setSpbill_create_ip(clientIp);
        payUnifiedOrderDTO.setTotal_fee(amount);
        payUnifiedOrderDTO.setTrade_type(type);

        String toSign = payUnifiedOrderDTO.getToSign();
        toSign = toSign + "&key=" + appKey;
        String sign = DigestUtils.md5DigestAsHex(toSign.getBytes());
        payUnifiedOrderDTO.setSign(sign);

        wepayApi.payUnifiedOrder(payUnifiedOrderDTO);

        return null;
    }
}
