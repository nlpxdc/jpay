package io.cjf.jpayalipayback.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeQueryResponse;

public interface OrderService {

    String getOrderPayPage(String orderId, Double amount, String title, Boolean useCert) throws AlipayApiException;

    AlipayTradeQueryResponse getPayResult(String orderId, String alipayTradeNo, Boolean useCert) throws AlipayApiException;

}
