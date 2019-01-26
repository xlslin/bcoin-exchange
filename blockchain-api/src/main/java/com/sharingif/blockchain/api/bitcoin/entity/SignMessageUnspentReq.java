package com.sharingif.blockchain.api.bitcoin.entity;

import java.math.BigInteger;

public class SignMessageUnspentReq {

    /**
     * txId
     */
    private String txId;
    /**
     * vout
     */
    private Integer vout;
    /**
     * scriptPubKey
     */
    private String scriptPubKey;
    /**
     * amount
     */
    private BigInteger amount;

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public Integer getVout() {
        return vout;
    }

    public void setVout(Integer vout) {
        this.vout = vout;
    }

    public String getScriptPubKey() {
        return scriptPubKey;
    }

    public void setScriptPubKey(String scriptPubKey) {
        this.scriptPubKey = scriptPubKey;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SignMessageUnspentReq{");
        sb.append("txId='").append(txId).append('\'');
        sb.append(", vout=").append(vout);
        sb.append(", scriptPubKey='").append(scriptPubKey).append('\'');
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }
}
