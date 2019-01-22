package org.bitcoincore.api.blockchain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Transaction {

    @JsonProperty("txid")
    private String txId;
    private String hash;
    private Integer version;
    private Integer size;
    @JsonProperty("vsize")
    private Integer vSize;
    private Integer weight;
    @JsonProperty("locktime")
    private Integer lockTime;
    @JsonProperty("vin")
    private List<Vin> vIn;
    @JsonProperty("vout")
    private List<Vout> vOut;
    private String hex;

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getvSize() {
        return vSize;
    }

    public void setvSize(Integer vSize) {
        this.vSize = vSize;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getLockTime() {
        return lockTime;
    }

    public void setLockTime(Integer lockTime) {
        this.lockTime = lockTime;
    }

    public List<Vin> getvIn() {
        return vIn;
    }

    public void setvIn(List<Vin> vIn) {
        this.vIn = vIn;
    }

    public List<Vout> getvOut() {
        return vOut;
    }

    public void setvOut(List<Vout> vOut) {
        this.vOut = vOut;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("txId='").append(txId).append('\'');
        sb.append(", hash='").append(hash).append('\'');
        sb.append(", version=").append(version);
        sb.append(", size=").append(size);
        sb.append(", vSize=").append(vSize);
        sb.append(", weight=").append(weight);
        sb.append(", lockTime=").append(lockTime);
        sb.append(", vIn=").append(vIn);
        sb.append(", vOut=").append(vOut);
        sb.append(", hex='").append(hex).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
