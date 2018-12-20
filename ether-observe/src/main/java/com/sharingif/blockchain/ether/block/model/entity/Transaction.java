package com.sharingif.blockchain.ether.block.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;

import java.math.BigInteger;
import java.util.Date;

public class Transaction implements java.io.Serializable, IObjectDateOperationHistory {

	/**
	 * 交易状态(0x0:失败)
	 */
	public static final String RECEIPT_STATUS_FAIL = "0x0";
	/**
	 * 交易状态(0x1:成功)
	 */
	public static final String RECEIPT_STATUS_SUCCESS = "0x1";

	/**
	 * 交易状态(S:成功)
	 */
	public static final String TX_RECEIPT_STATUS_SUCCESS = "S";
	/**
	 * 交易状态(F:失败)
	 */
	public static final String TX_RECEIPT_STATUS_FAIL = "F";

	/**
	 * 交易状态(WCL:未处理)
	 */
	public static final String TX_STATUS_UNTREATED = "WCL";

	//columns START
    /**
     * id			db_column: ID 
     */	
	private java.lang.String id;
    /**
     * 区块hash			db_column: BLOCK_HASH 
     */	
	private java.lang.String blockHash;
	/**
	 * 交易hash			db_column: TX_HASH
	 */
	private String txHash;
	/**
	 * 区块号			db_column: BLOCK_NUMBER
	 */
	private BigInteger blockNumber;
	/**
	 * FORM地址			db_column: TX_FROM
	 */
	private String txFrom;
	/**
	 * TO地址			db_column: TX_TO
	 */
	private String txTo;
	/**
	 * 合约地址			db_column: CONTRACT_ADDRESS
	 */
	private String contractAddress;
	/**
	 * 币种			db_column: COIN_TYPE
	 */
	private String coinType;
	/**
	 * input			db_column: TX_INPUT
	 */
	private String txInput;
	/**
	 * 交易值			db_column: TX_VALUE
	 */
	private BigInteger txValue;
	/**
	 * tx index			db_column: TX_INDEX
	 */
	private BigInteger txIndex;
	/**
	 * gas limit			db_column: GAS_LIMIT
	 */
	private BigInteger gasLimit;
	/**
	 * gas used			db_column: GAS_USED
	 */
	private BigInteger gasUsed;
	/**
	 * gas price			db_column: GAS_PRICE
	 */
	private BigInteger gasPrice;
	/**
	 * actual fee			db_column: ACTUAL_FEE
	 */
	private BigInteger actualFee;
	/**
	 * nonce			db_column: NONCE
	 */
	private BigInteger nonce;
	/**
	 * 合约交易状态(S:成功、F:失败、P:处理中)			db_column: TX_RECEIPT_STATUS
	 */
	private String txReceiptStatus;
	/**
	 * 交易时间			db_column: TX_TIME
	 */
	private Date txTime;
	/**
	 * 确认区块数			db_column: CONFIRM_BLOCK_NUMBER
	 */
	private Integer confirmBlockNumber;
	/**
	 * 交易状态(WCL:未处理、QKQRSCLZ:区块确认数处理中、YEWQR:余额未确认、YEQRYC:余额确认异常、YX:有效、WX:无效)			db_column: TX_STATUS
	 */
	private String txStatus;
	/**
	 * 创建时间			db_column: CREATE_TIME
	 */
	private Date createTime;
	/**
	 * 修改时间			db_column: MODIFY_TIME
	 */
	private Date modifyTime;
	//columns END

	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getId() {
		return this.id;
	}
	public void setBlockHash(java.lang.String blockHash) {
		this.blockHash = blockHash;
	}
	public java.lang.String getBlockHash() {
		return this.blockHash;
	}
	public void setTxHash(java.lang.String txHash) {
		this.txHash = txHash;
	}
	public java.lang.String getTxHash() {
		return this.txHash;
	}
	public void setBlockNumber(BigInteger blockNumber) {
		this.blockNumber = blockNumber;
	}
	public BigInteger getBlockNumber() {
		return this.blockNumber;
	}
	public void setTxFrom(java.lang.String txFrom) {
		this.txFrom = txFrom;
	}
	public java.lang.String getTxFrom() {
		return this.txFrom;
	}
	public void setTxTo(java.lang.String txTo) {
		this.txTo = txTo;
	}
	public java.lang.String getTxTo() {
		return this.txTo;
	}
	public void setContractAddress(java.lang.String contractAddress) {
		this.contractAddress = contractAddress;
	}
	public java.lang.String getContractAddress() {
		return this.contractAddress;
	}
	public void setCoinType(java.lang.String coinType) {
		this.coinType = coinType;
	}
	public java.lang.String getCoinType() {
		return this.coinType;
	}
	public void setTxInput(java.lang.String txInput) {
		this.txInput = txInput;
	}
	public java.lang.String getTxInput() {
		return this.txInput;
	}
	public void setTxValue(BigInteger txValue) {
		this.txValue = txValue;
	}
	public BigInteger getTxValue() {
		return this.txValue;
	}
	public void setTxIndex(BigInteger txIndex) {
		this.txIndex = txIndex;
	}
	public BigInteger getTxIndex() {
		return this.txIndex;
	}
	public void setGasLimit(BigInteger gasLimit) {
		this.gasLimit = gasLimit;
	}
	public BigInteger getGasLimit() {
		return this.gasLimit;
	}
	public void setGasUsed(BigInteger gasUsed) {
		this.gasUsed = gasUsed;
	}
	public BigInteger getGasUsed() {
		return this.gasUsed;
	}
	public void setGasPrice(BigInteger gasPrice) {
		this.gasPrice = gasPrice;
	}
	public BigInteger getGasPrice() {
		return this.gasPrice;
	}
	public void setActualFee(BigInteger actualFee) {
		this.actualFee = actualFee;
	}
	public BigInteger getActualFee() {
		return this.actualFee;
	}
	public void setNonce(BigInteger nonce) {
		this.nonce = nonce;
	}
	public BigInteger getNonce() {
		return this.nonce;
	}
	public void setTxReceiptStatus(java.lang.String txReceiptStatus) {
		this.txReceiptStatus = txReceiptStatus;
	}
	public java.lang.String getTxReceiptStatus() {
		return this.txReceiptStatus;
	}
	public void setTxTime(Date txTime) {
		this.txTime = txTime;
	}
	public Date getTxTime() {
		return this.txTime;
	}
	public void setConfirmBlockNumber(java.lang.Integer confirmBlockNumber) {
		this.confirmBlockNumber = confirmBlockNumber;
	}
	public java.lang.Integer getConfirmBlockNumber() {
		return this.confirmBlockNumber;
	}
	public void setTxStatus(java.lang.String txStatus) {
		this.txStatus = txStatus;
	}
	public java.lang.String getTxStatus() {
		return this.txStatus;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Date getModifyTime() {
		return this.modifyTime;
	}

	public static String convertTxReceiptStatus(String status) {
		return  (RECEIPT_STATUS_SUCCESS.equals(status) ? TX_RECEIPT_STATUS_SUCCESS : TX_RECEIPT_STATUS_FAIL);
	}

	public String toString() {
		return new StringBuilder("Transaction [")
			.append("Id=").append(getId()).append(", ")
					.append("BlockHash=").append(getBlockHash()).append(", ")
					.append("TxHash=").append(getTxHash()).append(", ")
					.append("BlockNumber=").append(getBlockNumber()).append(", ")
					.append("TxFrom=").append(getTxFrom()).append(", ")
					.append("TxTo=").append(getTxTo()).append(", ")
					.append("ContractAddress=").append(getContractAddress()).append(", ")
					.append("CoinType=").append(getCoinType()).append(", ")
					.append("TxInput=").append(getTxInput()).append(", ")
					.append("TxValue=").append(getTxValue()).append(", ")
					.append("TxIndex=").append(getTxIndex()).append(", ")
					.append("GasLimit=").append(getGasLimit()).append(", ")
					.append("GasUsed=").append(getGasUsed()).append(", ")
					.append("GasPrice=").append(getGasPrice()).append(", ")
					.append("ActualFee=").append(getActualFee()).append(", ")
					.append("Nonce=").append(getNonce()).append(", ")
					.append("TxReceiptStatus=").append(getTxReceiptStatus()).append(", ")
					.append("TxTime=").append(getTxTime()).append(", ")
					.append("ConfirmBlockNumber=").append(getConfirmBlockNumber()).append(", ")
					.append("TxStatus=").append(getTxStatus()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

