package io.cjf.jpayalipayback.service;

import com.alipay.api.AlipayApiException;

public interface OrderService {

    String getOrderPayPage(String orderId, Double amount, String title) throws AlipayApiException;

}
