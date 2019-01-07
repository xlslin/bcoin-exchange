package com.sharingif.blockchain.ether.api.withdrawal.entity;

import java.math.BigInteger;

/**
 * 取ether区块币种请求
 */
public class WithdrawalEtherReq {

    /**
     * 取现唯一编号
     */
    private String withdrawalId;
    /**
     * 币种
     */
    private String coinType;
    /**
     * 取现地址
     */
    private String address;
    /**
     * 金额
     */
    private BigInteger amount;
    /**
     * gas limit
     */
    private BigInteger gasLimit;
    /**
     * gas price
     */
    private BigInteger gasPrice;

    public String getWithdrawalId() {
        return withdrawalId;
    }

    public void setWithdrawalId(String withdrawalId) {
        this.withdrawalId = withdrawalId;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public BigInteger getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(BigInteger gasLimit) {
        this.gasLimit = gasLimit;
    }

    public BigInteger getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(BigInteger gasPrice) {
        this.gasPrice = gasPrice;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WithdrawalEtherReq{");
        sb.append("withdrawalId='").append(withdrawalId).append('\'');
        sb.append(", coinType='").append(coinType).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", gasLimit=").append(gasLimit);
        sb.append(", gasPrice=").append(gasPrice);
        sb.append('}');
        return sb.toString();
    }
}
