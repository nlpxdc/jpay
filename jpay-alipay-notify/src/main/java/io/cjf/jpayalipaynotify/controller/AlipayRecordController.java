package io.cjf.jpayalipaynotify.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/alipayrecord")
public class AlipayRecordController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/notifyResult")
    public String notifyResult(@RequestBody Map<String, String> result){
        logger.info("alipay record notify result: {}", result);
        return "success";
    }

}
