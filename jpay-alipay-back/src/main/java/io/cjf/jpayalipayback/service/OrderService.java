package io.cjf.jpayalipayback.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.*;

public interface OrderService {

    String getOrderPayPage(String orderId, Double amount, String title, Boolean useCert) throws AlipayApiException;

    AlipayTradeQueryResponse getPayResult(String orderId, String alipayTradeNo, Boolean useCert) throws AlipayApiException;

    AlipayTradeRefundResponse applyRefund(String orderId, String orderRefundId, Double amount) throws AlipayApiException;

    AlipayTradeFastpayRefundQueryResponse getRefundResult(String orderId, String orderRefundId) throws AlipayApiException;

    AlipayTradeCloseResponse close(String orderId, String alipayTradeId) throws AlipayApiException;

    AlipayTradeQueryResponse getAlipayTradeInfo(String orderId) throws AlipayApiException;

    AlipayTradeCreateResponse createAlipayTrade(String orderId, Double amount, String title) throws AlipayApiException;

}
