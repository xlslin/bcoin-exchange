package com.sharingif.blockchain.ether.block.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;
import com.sharingif.cube.components.sequence.Sequence;

import java.math.BigInteger;
import java.util.Date;

public class TransactionBusiness implements java.io.Serializable, IObjectDateOperationHistory {

	/**
	 * 交易类型(DEPOSIT:充值)
	 */
	public static final String TYPE_DEPOSIT = "DEPOSIT";
	/**
	 * 交易类型(WITHDRAWAL:提现)
	 */
	public static final String TYPE_WITHDRAWAL = "WITHDRAWAL";

	/**
	 * 处理状态(WCL:未处理)
	 */
	public static final String STATUS_UNTREATED = "WCL";
	/**
	 * 处理状态(CSHTZZ:初始化通知中)
	 */
	public static final String STATUS_INIT_NOTICE = "CSHTZZ";
	/**
	 * 处理状态(CSHTZCG:初始化通知成功)
	 */
	public static final String STATUS_INIT_NOTICED = "CSHTZCG";

	//columns START
    /**
     * id			db_column: ID 
     */
	@Sequence(ref="uuidSequenceGenerator")
	private java.lang.String id;
    /**
     * 区块数			db_column: BLOCK_NUMBER 
     */	
	private BigInteger blockNumber;
    /**
     * 区块hash			db_column: BLOCK_HASH 
     */	
	private java.lang.String blockHash;
    /**
     * ETH交易id			db_column: TRANSACTION_ID 
     */	
	private java.lang.String transactionId;
    /**
     * 交易hash			db_column: TX_HASH 
     */	
	private java.lang.String txHash;
    /**
     * 币种			db_column: COIN_TYPE 
     */	
	private java.lang.String coinType;
    /**
     * FORM地址			db_column: TX_FROM 
     */	
	private java.lang.String txFrom;
    /**
     * TO地址			db_column: TX_TO 
     */	
	private java.lang.String txTo;
    /**
     * 金额			db_column: AMOUNT 
     */	
	private BigInteger amount;
    /**
     * 手续费			db_column: FEE 
     */	
	private BigInteger fee;
    /**
     * 交易类型(DEPOSIT:充值、WITHDRAWAL:提现)			db_column: TYPE 
     */	
	private java.lang.String type;
    /**
     * 处理状态(WCL:未处理、CSHTZZ:初始化通知中、CSHTZCG:初始化通知成功、YQS:已清算)			db_column: STATUS
     */	
	private java.lang.String status;
    /**
     * 合约交易状态(S:成功、F:失败)			db_column: TX_RECEIPT_STATUS 
     */	
	private java.lang.String txReceiptStatus;
    /**
     * 交易状态(WYZ:未验证、QKQRYX:区块确认有效、QKQRWX:区块确认无效)			db_column: TX_STATUS
     */	
	private java.lang.String txStatus;
    /**
     * 交易时间			db_column: TX_TIME 
     */	
	private Date txTime;
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
	public void setBlockNumber(BigInteger blockNumber) {
		this.blockNumber = blockNumber;
	}
	public BigInteger getBlockNumber() {
		return this.blockNumber;
	}
	public void setBlockHash(java.lang.String blockHash) {
		this.blockHash = blockHash;
	}
	public java.lang.String getBlockHash() {
		return this.blockHash;
	}
	public void setTransactionId(java.lang.String transactionId) {
		this.transactionId = transactionId;
	}
	public java.lang.String getTransactionId() {
		return this.transactionId;
	}
	public void setTxHash(java.lang.String txHash) {
		this.txHash = txHash;
	}
	public java.lang.String getTxHash() {
		return this.txHash;
	}
	public void setCoinType(java.lang.String coinType) {
		this.coinType = coinType;
	}
	public java.lang.String getCoinType() {
		return this.coinType;
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
	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}
	public BigInteger getAmount() {
		return this.amount;
	}
	public void setFee(BigInteger fee) {
		this.fee = fee;
	}
	public BigInteger getFee() {
		return this.fee;
	}
	public void setType(java.lang.String type) {
		this.type = type;
	}
	public java.lang.String getType() {
		return this.type;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	public java.lang.String getStatus() {
		return this.status;
	}
	public void setTxReceiptStatus(java.lang.String txReceiptStatus) {
		this.txReceiptStatus = txReceiptStatus;
	}
	public java.lang.String getTxReceiptStatus() {
		return this.txReceiptStatus;
	}
	public void setTxStatus(java.lang.String txStatus) {
		this.txStatus = txStatus;
	}
	public java.lang.String getTxStatus() {
		return this.txStatus;
	}
	public void setTxTime(Date txTime) {
		this.txTime = txTime;
	}
	public Date getTxTime() {
		return this.txTime;
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

	public String toString() {
		return new StringBuilder("TransactionBusiness [")
			.append("Id=").append(getId()).append(", ")
					.append("BlockNumber=").append(getBlockNumber()).append(", ")
					.append("BlockHash=").append(getBlockHash()).append(", ")
					.append("TransactionId=").append(getTransactionId()).append(", ")
					.append("TxHash=").append(getTxHash()).append(", ")
					.append("CoinType=").append(getCoinType()).append(", ")
					.append("TxFrom=").append(getTxFrom()).append(", ")
					.append("TxTo=").append(getTxTo()).append(", ")
					.append("Amount=").append(getAmount()).append(", ")
					.append("Fee=").append(getFee()).append(", ")
					.append("Type=").append(getType()).append(", ")
					.append("Status=").append(getStatus()).append(", ")
					.append("TxReceiptStatus=").append(getTxReceiptStatus()).append(", ")
					.append("TxStatus=").append(getTxStatus()).append(", ")
					.append("TxTime=").append(getTxTime()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

