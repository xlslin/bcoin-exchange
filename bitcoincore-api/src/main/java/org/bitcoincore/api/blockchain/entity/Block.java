package org.bitcoincore.api.blockchain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Block<T> {

    private String hash;
    private Integer confirmations;
    private Integer strippedsize;
    private Integer size;
    private Integer weight;
    private Integer height;
    private Integer version;
    private String versionHex;
    @JsonProperty("merkleroot")
    private String merkleRoot;
    private T tx;
    private Long time;
    private Long mediantime;
    private Long nonce;
    private String bits;
    private BigDecimal difficulty;
    @JsonProperty("chainwork")
    private String chainWork;
    private Integer nTx;
    @JsonProperty("previousblockhash")
    private String previousBlockHash;
    @JsonProperty("nextblockhash")
    private String nextBlockHash;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public Integer getStrippedsize() {
        return strippedsize;
    }

    public void setStrippedsize(Integer strippedsize) {
        this.strippedsize = strippedsize;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getVersionHex() {
        return versionHex;
    }

    public void setVersionHex(String versionHex) {
        this.versionHex = versionHex;
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public void setMerkleRoot(String merkleRoot) {
        this.merkleRoot = merkleRoot;
    }

    public T getTx() {
        return tx;
    }

    public void setTx(T tx) {
        this.tx = tx;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getMediantime() {
        return mediantime;
    }

    public void setMediantime(Long mediantime) {
        this.mediantime = mediantime;
    }

    public Long getNonce() {
        return nonce;
    }

    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }

    public String getBits() {
        return bits;
    }

    public void setBits(String bits) {
        this.bits = bits;
    }

    public BigDecimal getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(BigDecimal difficulty) {
        this.difficulty = difficulty;
    }

    public String getChainWork() {
        return chainWork;
    }

    public void setChainWork(String chainWork) {
        this.chainWork = chainWork;
    }

    public Integer getnTx() {
        return nTx;
    }

    public void setnTx(Integer nTx) {
        this.nTx = nTx;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public void setPreviousBlockHash(String previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
    }

    public String getNextBlockHash() {
        return nextBlockHash;
    }

    public void setNextBlockHash(String nextBlockHash) {
        this.nextBlockHash = nextBlockHash;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Block{");
        sb.append("hash='").append(hash).append('\'');
        sb.append(", confirmations=").append(confirmations);
        sb.append(", strippedsize=").append(strippedsize);
        sb.append(", size=").append(size);
        sb.append(", weight=").append(weight);
        sb.append(", height=").append(height);
        sb.append(", version=").append(version);
        sb.append(", versionHex='").append(versionHex).append('\'');
        sb.append(", merkleRoot='").append(merkleRoot).append('\'');
        sb.append(", tx=").append(tx);
        sb.append(", time=").append(time);
        sb.append(", mediantime=").append(mediantime);
        sb.append(", nonce=").append(nonce);
        sb.append(", bits='").append(bits).append('\'');
        sb.append(", difficulty=").append(difficulty);
        sb.append(", chainWork='").append(chainWork).append('\'');
        sb.append(", nTx=").append(nTx);
        sb.append(", previousBlockHash='").append(previousBlockHash).append('\'');
        sb.append(", nextBlockHash='").append(nextBlockHash).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
