package com.sharingif.blockchain.api.bitcoin.entity;

import java.util.List;

public class SignMessageVinReq {

    /**
     * 发款人地址
     */
    private String fromAddress;
    /**
     * unspent列表
     */
    private List<SignMessageUnspentReq> unspentList;

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public List<SignMessageUnspentReq> getUnspentList() {
        return unspentList;
    }

    public void setUnspentList(List<SignMessageUnspentReq> unspentList) {
        this.unspentList = unspentList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SignMessageVinReq{");
        sb.append("fromAddress='").append(fromAddress).append('\'');
        sb.append(", unspentList=").append(unspentList);
        sb.append('}');
        return sb.toString();
    }
}
