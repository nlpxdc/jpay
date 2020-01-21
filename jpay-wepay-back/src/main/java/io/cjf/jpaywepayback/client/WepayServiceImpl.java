package io.cjf.jpaywepayback.client;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.cjf.jpaywepayback.dto.*;
import io.cjf.jpaywepayback.util.WepayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;

@Service
public class WepayServiceImpl implements WepayService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecureRandom secureRandom;

    @Autowired
    private XmlMapper xmlMapper;

    @Autowired
    private WepayApi wepayApi;

    @Autowired
    private WepayCertApi wepayCertApi;

    @Autowired
    private WepayUtil wepayUtil;

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.appKey}")
    private String appKey;

    @Value("${wepay.mchId}")
    private String mchId;

    @Value("${wepay.payKey}")
    private String payKey;

    @Value("${wepay.notifyUrl}")
    private String notifyUrl;

    @Value("${wepay.refund.notifyUrl}")
    private String refundNotifyUrl;

    @Override
    public JSONObject payUnifiedOrder(String orderId,
                                      Integer amount,
                                      String title,
                                      String payType,
                                      String openid,
                                      String productId) throws JsonProcessingException, IllegalAccessException {
        logger.info("orderId is: {}", orderId);

        PayUnifiedOrderDTO payUnifiedOrderDTO = new PayUnifiedOrderDTO();
        payUnifiedOrderDTO.setAppid(appId);
        payUnifiedOrderDTO.setMch_id(mchId);
        byte[] bytes = secureRandom.generateSeed(16);
        String nonce = DatatypeConverter.printHexBinary(bytes);
        payUnifiedOrderDTO.setNonce_str(nonce);

        payUnifiedOrderDTO.setOut_trade_no(orderId);
        payUnifiedOrderDTO.setTotal_fee(amount);
        payUnifiedOrderDTO.setBody(title);
        payUnifiedOrderDTO.setSpbill_create_ip("127.0.0.1");
        payUnifiedOrderDTO.setNotify_url(notifyUrl);
        payUnifiedOrderDTO.setSign_type("MD5");

        payUnifiedOrderDTO.setTrade_type(payType);
        if (payType.equals("JSAPI")){
            payUnifiedOrderDTO.setOpenid(openid);
        }else if (payType.equals("NATIVE")){
            payUnifiedOrderDTO.setProduct_id(productId);
        }

        final String sign = wepayUtil.sign(payUnifiedOrderDTO);
        payUnifiedOrderDTO.setSign(sign);
        String xml = wepayApi.payUnifiedOrder(payUnifiedOrderDTO);
        JSONObject jsonObject = xmlMapper.readValue(xml, JSONObject.class);

        return jsonObject;
    }

    @Override
    public JSONObject payOrderQuery(String orderId) throws IllegalAccessException, JsonProcessingException {
        PayOrderQueryDTO payOrderQueryDTO = new PayOrderQueryDTO();
        payOrderQueryDTO.setAppid(appId);
        payOrderQueryDTO.setMch_id(mchId);
        byte[] bytes = secureRandom.generateSeed(16);
        String nonce = DatatypeConverter.printHexBinary(bytes);
        payOrderQueryDTO.setNonce_str(nonce);
        payOrderQueryDTO.setOut_trade_no(orderId);
        payOrderQueryDTO.setSign_type("MD5");
        String sign = wepayUtil.sign(payOrderQueryDTO);
        payOrderQueryDTO.setSign(sign);
        String xml = wepayApi.payOrderQuery(payOrderQueryDTO);
        JSONObject jsonObject = xmlMapper.readValue(xml, JSONObject.class);
        return jsonObject;
    }

    @Override
    public JSONObject payOrderClose(String orderId) throws IllegalAccessException, JsonProcessingException {
        PayOrderCloseDTO payOrderCloseDTO = new PayOrderCloseDTO();
        payOrderCloseDTO.setAppid(appId);
        payOrderCloseDTO.setMch_id(mchId);
        byte[] bytes = secureRandom.generateSeed(16);
        String nonce = DatatypeConverter.printHexBinary(bytes);
        payOrderCloseDTO.setNonce_str(nonce);
        payOrderCloseDTO.setOut_trade_no(orderId);
        payOrderCloseDTO.setSign_type("MD5");
        String sign = wepayUtil.sign(payOrderCloseDTO);
        payOrderCloseDTO.setSign(sign);
        String xml = wepayApi.payOrderClose(payOrderCloseDTO);
        JSONObject jsonObject = xmlMapper.readValue(xml, JSONObject.class);
        return jsonObject;
    }

    @Override
    public JSONObject payRefund(String orderId, Integer totalFee, String refundId, Integer refundFee) throws IllegalAccessException, JsonProcessingException {
        PayRefundDTO payRefundDTO = new PayRefundDTO();
        payRefundDTO.setAppid(appId);
        payRefundDTO.setMch_id(mchId);
        byte[] bytes = secureRandom.generateSeed(16);
        String nonce = DatatypeConverter.printHexBinary(bytes);
        payRefundDTO.setNonce_str(nonce);
        payRefundDTO.setNotify_url(refundNotifyUrl);
        payRefundDTO.setOut_trade_no(orderId);
        payRefundDTO.setTotal_fee(totalFee);
        payRefundDTO.setOut_refund_no(refundId);
        payRefundDTO.setRefund_fee(refundFee);
        payRefundDTO.setSign_type("MD5");
        String sign = wepayUtil.sign(payRefundDTO);
        payRefundDTO.setSign(sign);
        String xml = wepayCertApi.payRefund(payRefundDTO);
        JSONObject jsonObject = xmlMapper.readValue(xml, JSONObject.class);
        return jsonObject;
    }

    @Override
    public JSONObject payRefundQuery(String orderId) throws IllegalAccessException, JsonProcessingException {
        final PayRefundQueryDTO payRefundQueryDTO = new PayRefundQueryDTO();
        payRefundQueryDTO.setAppid(appId);
        payRefundQueryDTO.setMch_id(mchId);
        byte[] bytes = secureRandom.generateSeed(16);
        String nonce = DatatypeConverter.printHexBinary(bytes);
        payRefundQueryDTO.setNonce_str(nonce);
        payRefundQueryDTO.setOut_trade_no(orderId);
        payRefundQueryDTO.setSign_type("MD5");
        final String sign = wepayUtil.sign(payRefundQueryDTO);
        payRefundQueryDTO.setSign(sign);
        final String xml = wepayApi.payRefundQuery(payRefundQueryDTO);
        JSONObject jsonObject = xmlMapper.readValue(xml, JSONObject.class);
        return jsonObject;
    }

    @Override
    public String payDownloadBill(String billDate) throws IllegalAccessException {
        final PayDownloadBillDTO payDownloadBillDTO = new PayDownloadBillDTO();
        payDownloadBillDTO.setAppid(appId);
        payDownloadBillDTO.setMch_id(mchId);
        byte[] bytes = secureRandom.generateSeed(16);
        String nonce = DatatypeConverter.printHexBinary(bytes);
        payDownloadBillDTO.setNonce_str(nonce);
        payDownloadBillDTO.setBill_date(billDate);
        payDownloadBillDTO.setBill_type("ALL");
        final String sign = wepayUtil.sign(payDownloadBillDTO);
        payDownloadBillDTO.setSign(sign);
        final String result = wepayApi.payDownloadBill(payDownloadBillDTO);
        return result;
    }

    @Override
    public String payDownloadFundflow(String billDate) throws IllegalAccessException {
        final PayDownloadFundflowDTO payDownloadFundflowDTO = new PayDownloadFundflowDTO();
        payDownloadFundflowDTO.setAppid(appId);
        payDownloadFundflowDTO.setMch_id(mchId);
        byte[] bytes = secureRandom.generateSeed(16);
        String nonce = DatatypeConverter.printHexBinary(bytes);
        payDownloadFundflowDTO.setNonce_str(nonce);
        payDownloadFundflowDTO.setAccount_type("Basic");
        payDownloadFundflowDTO.setBill_date(billDate);
        payDownloadFundflowDTO.setSign_type("HMAC-SHA256");
        final String sign = wepayUtil.sign(payDownloadFundflowDTO);
        payDownloadFundflowDTO.setSign(sign);
        final String result = wepayCertApi.payDownloadFundflow(payDownloadFundflowDTO);
        return result;
    }

    @Override
    public JSONObject payMicropay(String orderId, Integer amount, String title, String authcode) throws IllegalAccessException, JsonProcessingException {
        logger.info("micro pay orderId is: {}", orderId);

        final PayMicropayDTO payMicropayDTO = new PayMicropayDTO();
        payMicropayDTO.setAppid(appId);
        payMicropayDTO.setMch_id(mchId);
        byte[] bytes = secureRandom.generateSeed(16);
        String nonce = DatatypeConverter.printHexBinary(bytes);
        payMicropayDTO.setNonce_str(nonce);
        payMicropayDTO.setOut_trade_no(orderId);
        payMicropayDTO.setTotal_fee(amount);
        payMicropayDTO.setBody(title);
        payMicropayDTO.setSpbill_create_ip("127.0.0.1");
        payMicropayDTO.setAuth_code(authcode);
        payMicropayDTO.setSign_type("MD5");
        final String sign = wepayUtil.sign(payMicropayDTO);
        payMicropayDTO.setSign(sign);
        final String xml = wepayApi.payMicropay(payMicropayDTO);
        JSONObject jsonObject = xmlMapper.readValue(xml, JSONObject.class);
        return jsonObject;
    }

    @Override
    public JSONObject payReverse(String orderId) throws IllegalAccessException, JsonProcessingException {
        final PayReverseDTO payReverseDTO = new PayReverseDTO();
        payReverseDTO.setAppid(appId);
        payReverseDTO.setMch_id(mchId);
        byte[] bytes = secureRandom.generateSeed(16);
        String nonce = DatatypeConverter.printHexBinary(bytes);
        payReverseDTO.setNonce_str(nonce);
        payReverseDTO.setOut_trade_no(orderId);
        final String sign = wepayUtil.sign(payReverseDTO);
        payReverseDTO.setSign(sign);
        final String xml = wepayCertApi.payReverse(payReverseDTO);
        JSONObject jsonObject = xmlMapper.readValue(xml, JSONObject.class);
        return jsonObject;
    }

    @Override
    public JSONObject authcodeToOpenid(String authcode) throws IllegalAccessException, JsonProcessingException {
        final AuthcodeToOpenidDTO authcodeToOpenidDTO = new AuthcodeToOpenidDTO();
        authcodeToOpenidDTO.setAppid(appId);
        authcodeToOpenidDTO.setMch_id(mchId);
        byte[] bytes = secureRandom.generateSeed(16);
        String nonce = DatatypeConverter.printHexBinary(bytes);
        authcodeToOpenidDTO.setNonce_str(nonce);
        authcodeToOpenidDTO.setAuth_code(authcode);
        final String sign = wepayUtil.sign(authcodeToOpenidDTO);
        authcodeToOpenidDTO.setSign(sign);
        final String xml = wepayApi.authcodeToOpenid(authcodeToOpenidDTO);
        JSONObject jsonObject = xmlMapper.readValue(xml, JSONObject.class);
        return jsonObject;
    }

    @Override
    public JSONObject getSandboxPaykey(String mchId) throws IllegalAccessException, JsonProcessingException {
        final GetSandboxPaykeyDTO getSandboxPaykeyDTO = new GetSandboxPaykeyDTO();
        getSandboxPaykeyDTO.setMch_id(mchId);
        byte[] bytes = secureRandom.generateSeed(16);
        String nonce = DatatypeConverter.printHexBinary(bytes);
        getSandboxPaykeyDTO.setNonce_str(nonce);
        final String sign = wepayUtil.sign(getSandboxPaykeyDTO);
        getSandboxPaykeyDTO.setSign(sign);
        final String xml = wepayApi.getSandboxPaykey(getSandboxPaykeyDTO);
        JSONObject jsonObject = xmlMapper.readValue(xml, JSONObject.class);
        return jsonObject;
    }
}
