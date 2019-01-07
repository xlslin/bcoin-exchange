package com.sharingif.blockchain.api.ether.entity;

import java.math.BigInteger;

/**
 * 生成transfer交易请求
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/26 上午11:54
 */
public class Erc20SignMessageReq {

    /**
     * 交易唯一编号
     */
    private BigInteger nonce;
    /**
     * 发款人地址
     */
    private String fromAddress;
    /**
     * 收款人地址
     */
    private String toAddress;
    /**
     * 合约地址
     */
    private String contractAddress;
    /**
     * 金额
     */
    private BigInteger amount;
    /**
     * gas价格
     */
    private BigInteger gasPrice;
    /**
     * gas最大使用量
     */
    private BigInteger gasLimit;

    public BigInteger getNonce() {
        return nonce;
    }

    public void setNonce(BigInteger nonce) {
        this.nonce = nonce;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public BigInteger getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(BigInteger gasPrice) {
        this.gasPrice = gasPrice;
    }

    public BigInteger getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(BigInteger gasLimit) {
        this.gasLimit = gasLimit;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Erc20TransferReq{");
        sb.append("nonce=").append(nonce);
        sb.append(", fromAddress='").append(fromAddress).append('\'');
        sb.append(", toAddress='").append(toAddress).append('\'');
        sb.append(", contractAddress='").append(contractAddress).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", gasPrice=").append(gasPrice);
        sb.append(", gasLimit=").append(gasLimit);
        sb.append('}');
        return sb.toString();
    }
}
