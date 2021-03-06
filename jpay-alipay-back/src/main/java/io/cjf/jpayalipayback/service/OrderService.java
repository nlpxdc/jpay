package io.cjf.jpayalipayback.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.*;

public interface OrderService {

    String getOrderPayPage(String orderId, Double amount, String title) throws AlipayApiException;

    String getOrderPayWap(String orderId, Double amount, String title) throws AlipayApiException;

    String getOrderPayApp(String orderId, Double amount, String title) throws AlipayApiException;

    AlipayTradePrecreateResponse getOrderPayQRCode(String orderId, Double amount, String title) throws AlipayApiException;

    AlipayTradePayResponse payCodePay(String orderId,
                                      String title,
                                      Double amount,
                                      Double discount,
                                      String authcode) throws AlipayApiException;

    AlipayTradeCancelResponse cancelOrderPay(String orderId) throws AlipayApiException;

    AlipayTradeRefundResponse applyRefund(String orderId, String orderRefundId, Double amount) throws AlipayApiException;

    AlipayTradeFastpayRefundQueryResponse getRefundResult(String orderId, String orderRefundId) throws AlipayApiException;

    AlipayTradeCloseResponse close(String orderId, String alipayTradeId) throws AlipayApiException;

    AlipayTradeQueryResponse getAlipayTradeInfo(String orderId) throws AlipayApiException;

    AlipayTradeCreateResponse createAlipayTrade(String orderId, Double amount, String title) throws AlipayApiException;

    AlipayDataDataserviceBillDownloadurlQueryResponse getBillUrl(String billType, String billDate) throws AlipayApiException;

}
