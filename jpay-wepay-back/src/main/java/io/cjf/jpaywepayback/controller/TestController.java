package io.cjf.jpaywepayback.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jpaywepayback.dto.PayUnifiedOrderDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_XML_VALUE)
    public PayUnifiedOrderDTO hello(){
//        JSONObject xml = new JSONObject();
//        xml.put("aa", "aavv");
//        xml.put("bb", "bbvv");
        PayUnifiedOrderDTO payUnifiedOrderDTO = new PayUnifiedOrderDTO();
        return payUnifiedOrderDTO;
    }

}
