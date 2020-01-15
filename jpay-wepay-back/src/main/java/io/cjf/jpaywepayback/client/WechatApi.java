package io.cjf.jpaywepayback.client;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "WechatApi", url = "${wechat.url}")
public interface WechatApi {

    @GetMapping("/cgi-bin/token")
    JSONObject getAppAccessToken(@RequestParam String grant_type,
                                 @RequestParam String appid,
                                 @RequestParam String secret);

    @GetMapping("/cgi-bin/ticket/getticket")
    JSONObject getJsTicket(@RequestParam String access_token,
                           @RequestParam String type);

}
