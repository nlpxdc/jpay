package io.cjf.jpaywepayback.util;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WepayUtil {

    @Value("${wepay.payKey}")
    private String payKey;

    public String sign(Object wepayDTO) throws IllegalAccessException {

        Field[] fieldArray = wepayDTO.getClass().getDeclaredFields();
        List<Field> fieldList = Arrays.stream(fieldArray).filter(f -> !f.getName().equals("sign")).collect(Collectors.toList());
        LinkedList<Field> fieldListNoSign = new LinkedList<>();
        for (Field field : fieldList) {
            field.setAccessible(true);
            Object o = field.get(wepayDTO);
            if (o != null) {
                fieldListNoSign.add(field);
            }
        }
        List<Field> sortedFields = fieldListNoSign.stream().sorted(Comparator.comparing(f -> f.getName())).collect(Collectors.toList());
        List<String> paras = new LinkedList<>();
        for (Field field : sortedFields) {
            Object o = field.get(wepayDTO);
            String para = String.format("%s=%s", field.getName(), o);
            paras.add(para);
        }
        String toSign = String.join("&", paras);
        toSign = toSign + "&key=" + payKey;

        Field sign_type = null;
        try {
            sign_type = wepayDTO.getClass().getDeclaredField("sign_type");
            sign_type.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        String sign = "";
        if(sign_type != null && sign_type.get(wepayDTO).equals("HMAC-SHA256")){
            final HmacUtils hmacsha256 = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, payKey);
            sign = hmacsha256.hmacHex(toSign);
        }else {
            sign = DigestUtils.md5DigestAsHex(toSign.getBytes());
        }
        sign = sign.toUpperCase();
        return sign;
    }

}
