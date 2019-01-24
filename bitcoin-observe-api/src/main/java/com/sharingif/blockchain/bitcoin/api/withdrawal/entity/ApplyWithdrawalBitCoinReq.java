package com.sharingif.blockchain.bitcoin.api.withdrawal.entity;

import java.math.BigInteger;

/**
 * BitCoin区块取现申请请求
 */
public class ApplyWithdrawalBitCoinReq {

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ApplyWithdrawalBitCoinReq{");
        sb.append("withdrawalId='").append(withdrawalId).append('\'');
        sb.append(", coinType='").append(coinType).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }
}
