package io.cjf.jpaywepayback.client;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jpaywepayback.dto.PayOrderQueryDTO;
import io.cjf.jpaywepayback.dto.PayUnifiedOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "WepayApi", url = "${wepay.url}")
public interface WepayApi {

    @PostMapping(value = "/pay/unifiedorder", consumes = MediaType.APPLICATION_XML_VALUE)
    String payUnifiedOrder(@RequestBody PayUnifiedOrderDTO payUnifiedOrderDTO);

    @PostMapping(value = "/pay/orderquery", consumes = MediaType.APPLICATION_XML_VALUE)
    String payOrderQuery(@RequestBody PayOrderQueryDTO payOrderQueryDTO);

}
