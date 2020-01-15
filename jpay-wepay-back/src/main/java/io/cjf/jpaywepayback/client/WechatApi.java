package io.cjf.jpaywepayback.client;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "WechatApi", url = "${wechat.url}")
public interface WechatApi {

    @GetMapping(value = "/cgi-bin/token")
    String getAppAccessToken(@RequestParam String grant_type,
                             @RequestParam String appid,
                             @RequestParam String secret);

    @GetMapping("/cgi-bin/ticket/getticket")
    String getJsTicket(@RequestParam String access_token,
                       @RequestParam String type);

}
