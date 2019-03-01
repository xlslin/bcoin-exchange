package com.sharingif.blockchain.api.ether.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sharingif.cube.core.util.Charset;
import com.sharingif.cube.core.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;

public class DepositWithdrawalNoticeReq {

    /**
     * 处理状态(P:处理中)
     */
    public static final String STATUS_PROCESSING = "P";
    /**
     * 处理状态(S:成功)
     */
    public static final String STATUS_SUCCESS = "S";
    /**
     * 处理状态(F:失败)
     */
    public static final String STATUS_FAIL = "F";

    /**
     * id
     */
    private String id;
    /**
     * 区块数
     */
    private BigInteger blockNumber;
    /**
     * 区块hash
     */
    private String blockHash;
    /**
     * 交易hash
     */
    private String txHash;
    /**
     * tx index
     */
    private BigInteger txIndex;
    /**
     * 币种
     */
    private String coinType;
    /**
     * FORM地址
     */
    private String txFrom;
    /**
     * TO地址
     */
    private String txTo;
    /**
     * 金额
     */
    private BigInteger amount;
    /**
     * nonce
     */
    private BigInteger nonce;
    /**
     * 交易时间
     */
    private Long txTime;
    /**
     * gas limit
     */
    private BigInteger gasLimit;
    /**
     * gas used
     */
    private BigInteger gasUsed;
    /**
     * gas price
     */
    private BigInteger gasPrice;
    /**
     * actual fee
     */
    private BigInteger actualFee;
    /**
     * 合约地址
     */
    private String contractAddress;
    /**
     * 签名
     */
    private String sign;
    /**
     * 处理状态(P:处理中、S:成功、F:失败)
     */
    private String status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigInteger getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(BigInteger blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public BigInteger getTxIndex() {
        return txIndex;
    }

    public void setTxIndex(BigInteger txIndex) {
        this.txIndex = txIndex;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public String getTxFrom() {
        return txFrom;
    }

    public void setTxFrom(String txFrom) {
        this.txFrom = txFrom;
    }

    public String getTxTo() {
        return txTo;
    }

    public void setTxTo(String txTo) {
        this.txTo = txTo;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public BigInteger getNonce() {
        return nonce;
    }

    public void setNonce(BigInteger nonce) {
        this.nonce = nonce;
    }

    public Long getTxTime() {
        return txTime;
    }

    public void setTxTime(Long txTime) {
        this.txTime = txTime;
    }

    public BigInteger getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(BigInteger gasLimit) {
        this.gasLimit = gasLimit;
    }

    public BigInteger getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(BigInteger gasUsed) {
        this.gasUsed = gasUsed;
    }

    public BigInteger getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(BigInteger gasPrice) {
        this.gasPrice = gasPrice;
    }

    public BigInteger getActualFee() {
        return actualFee;
    }

    public void setActualFee(BigInteger actualFee) {
        this.actualFee = actualFee;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonIgnore
    public byte[] getSignData() {
        Map<String, String> parameters = new TreeMap();
        parameters.put("id", id);
        parameters.put("blockNumber", blockNumber.toString());
        parameters.put("blockHash", blockHash);
        parameters.put("txHash", txHash);
        parameters.put("txIndex", txIndex.toString());
        parameters.put("coinType", coinType);
        parameters.put("txFrom", txFrom);
        parameters.put("txTo", txTo);
        parameters.put("amount", amount.toString());
        parameters.put("nonce", nonce.toString());
        parameters.put("txTime", txTime.toString());
        parameters.put("gasLimit", gasLimit.toString());
        parameters.put("gasUsed", gasUsed.toString());
        parameters.put("gasPrice", gasPrice.toString());
        parameters.put("actualFee", actualFee.toString());
        parameters.put("contractAddress", contractAddress);
        parameters.put("status", status);

        StringBuffer stringBuffer = new StringBuffer();
        parameters.forEach((key, value)->{
            if (!StringUtils.isTrimEmpty(value)) {
                stringBuffer.append(key).append("=").append(value).append("&");
            }
        });

        try {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            return stringBuffer.toString().getBytes(Charset.UTF8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NoticeReq{");
        sb.append("id='").append(id).append('\'');
        sb.append(", blockNumber=").append(blockNumber);
        sb.append(", blockHash='").append(blockHash).append('\'');
        sb.append(", txHash='").append(txHash).append('\'');
        sb.append(", txIndex=").append(txIndex);
        sb.append(", coinType='").append(coinType).append('\'');
        sb.append(", txFrom='").append(txFrom).append('\'');
        sb.append(", txTo='").append(txTo).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", nonce=").append(nonce);
        sb.append(", txTime=").append(txTime);
        sb.append(", gasLimit=").append(gasLimit);
        sb.append(", gasUsed=").append(gasUsed);
        sb.append(", gasPrice=").append(gasPrice);
        sb.append(", actualFee=").append(actualFee);
        sb.append(", contractAddress='").append(contractAddress).append('\'');
        sb.append(", sign='").append(sign).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
