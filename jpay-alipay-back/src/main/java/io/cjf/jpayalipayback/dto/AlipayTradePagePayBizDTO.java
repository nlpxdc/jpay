package io.cjf.jpayalipayback.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AlipayTradePagePayBizDTO{

	@JSONField(name="out_trade_no")
	private String outTradeNo;

	@JSONField(name="total_amount")
	private Double totalAmount;

	@JSONField(name="subject")
	private String subject;

	public void setOutTradeNo(String outTradeNo){
		this.outTradeNo = outTradeNo;
	}

	public String getOutTradeNo(){
		return outTradeNo;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setSubject(String subject){
		this.subject = subject;
	}

	public String getSubject(){
		return subject;
	}

	@JSONField(name="product_code")
	public String getProductCode(){
		return "FAST_INSTANT_TRADE_PAY";
	}

}