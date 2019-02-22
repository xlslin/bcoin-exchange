package com.sharingif.blockchain.bitcoin.account.model.entity;

import java.math.BigInteger;

public class SubAccount extends Account {

    /**
     * 子币种
     */
    private String subCoinType;
    /**
     * 子余额
     */
    private BigInteger subBalance;

    public String getSubCoinType() {
        return subCoinType;
    }

    public void setSubCoinType(String subCoinType) {
        this.subCoinType = subCoinType;
    }

    public BigInteger getSubBalance() {
        return subBalance;
    }

    public void setSubBalance(BigInteger subBalance) {
        this.subBalance = subBalance;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SubAccount{");
        sb.append("subCoinType='").append(subCoinType).append('\'');
        sb.append(", subBalance=").append(subBalance);
        sb.append('}');
        return sb.toString();
    }
}
