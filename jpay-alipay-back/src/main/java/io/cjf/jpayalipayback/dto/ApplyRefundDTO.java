package io.cjf.jpayalipayback.dto;

public class ApplyRefundDTO {
    private String orderId;
    private String orderRefundId;
    private Double amount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOrderRefundId() {
        return orderRefundId;
    }

    public void setOrderRefundId(String orderRefundId) {
        this.orderRefundId = orderRefundId;
    }
}
