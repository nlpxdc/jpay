package io.cjf.jpaywepayback.util;

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
        String md5Str = DigestUtils.md5DigestAsHex(toSign.getBytes());
        String md5Upper = md5Str.toUpperCase();
        return md5Upper;
    }

}
