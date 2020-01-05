package io.cjf.jpayalipaynotify.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/alipayrecord")
public class AlipayRecordController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${alipay.alipayPublicKey}")
    private String alipayPublicKey;

    @RequestMapping("/notifyResult")
    public String notifyResult(@RequestParam Map<String, String> result) throws AlipayApiException {
        logger.info("alipay record notify result: {}", result);

        boolean signVerified = AlipaySignature.rsaCheckV1(result, alipayPublicKey, "utf-8", "RSA2");
        if (signVerified) {
            return "success";
        } else {
            return "failure";
        }

    }

}
