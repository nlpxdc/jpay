package io.cjf.jpayalipayback.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeQueryResponse;

public interface OrderService {

    String getOrderPayPage(String orderId, Double amount, String title) throws AlipayApiException;

    AlipayTradeQueryResponse getPayResult(String orderId, String alipayTradeNo) throws AlipayApiException;

}
