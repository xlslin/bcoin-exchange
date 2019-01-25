package com.sharingif.blockchain.crypto.api.bitcoin.entity;

import java.math.BigInteger;

public class SignMessageVoutReq {

    /**
     * 收款人地址
     */
    private String toAddress;
    /**
     * 金额
     */
    private BigInteger amount;

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SignMessageVoutReq{");
        sb.append("toAddress='").append(toAddress).append('\'');
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }
}
