package io.cjf.jpayalipaynotify.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/alipayrecord")
public class AlipayRecordController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${alipay.alipayPublicKey}")
    private String alipayPublicKey;

    @Value("${alipay.alipayPublicCertPath}")
    private String alipayPublicCertPath;

    @RequestMapping("/notifyResult")
    public String notifyResult(@RequestParam Map<String, String> result) throws AlipayApiException {
        logger.info("alipay record notify result: {}", JSON.toJSON(result));

        boolean signVerified = AlipaySignature.rsaCheckV1(result, alipayPublicKey, "utf-8", "RSA2");
        logger.info("signVerified: {}", signVerified);

        if (signVerified) {
            //todo check params
            return "success";
        } else {
            return "failure";
        }

    }

    @RequestMapping("/notifyResultCert")
    public String notifyResultCert(@RequestParam Map<String, String> result) throws AlipayApiException {
        logger.info("alipay record notify result cert: {}", JSON.toJSON(result));

        boolean signVerified = AlipaySignature.rsaCertCheckV1(result, alipayPublicCertPath, "utf-8", "RSA2");
        logger.info("signVerified: {}", signVerified);

        if (signVerified) {
            //todo check params
            return "success";
        } else {
            return "failure";
        }

    }

}
