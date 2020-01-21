package io.cjf.jpaywepayback.client;

import com.alibaba.fastjson.JSONObject;
import io.cjf.jpaywepayback.dto.*;
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

    @PostMapping(value = "/pay/closeorder", consumes = MediaType.APPLICATION_XML_VALUE)
    String payOrderClose(@RequestBody PayOrderCloseDTO payOrderCloseDTO);

    @PostMapping(value = "/pay/refundquery", consumes = MediaType.APPLICATION_XML_VALUE)
    String payRefundQuery(@RequestBody PayRefundQueryDTO payRefundQueryDTO);

    @PostMapping(value = "/pay/downloadbill", consumes = MediaType.APPLICATION_XML_VALUE)
    String payDownloadBill(@RequestBody PayDownloadBillDTO payDownloadBillDTO);

    @PostMapping(value = "/pay/micropay", consumes = MediaType.APPLICATION_XML_VALUE)
    String payMicropay(@RequestBody PayMicropayDTO payMicropayDTO);

}
