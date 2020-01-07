package io.cjf.jpayalipayback.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;

public interface OrderService {

    String getOrderPayPage(String orderId, Double amount, String title, Boolean useCert) throws AlipayApiException;

    AlipayTradeQueryResponse getPayResult(String orderId, String alipayTradeNo, Boolean useCert) throws AlipayApiException;

    AlipayTradeRefundResponse applyRefund(String orderId, Double amount) throws AlipayApiException;

}
