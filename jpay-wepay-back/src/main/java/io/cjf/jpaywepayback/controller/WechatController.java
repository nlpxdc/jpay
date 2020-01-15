package io.cjf.jpaywepayback.controller;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jpaywepayback.client.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wechat")
public class WechatController {

    @Autowired
    private WechatService wechatService;

    @GetMapping("/getJsTicket")
    public String getJsTicket(){
        JSONObject jsTicketJson = wechatService.getJsTicket();
        String ticket = jsTicketJson.getString("ticket");
        return ticket;
    }

}
