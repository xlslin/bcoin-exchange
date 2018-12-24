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
     * 处理状态(WCL:未处理、TZZ:通知中、TZCG:通知成功、QKYZZ:区块验证中、SUCCESS:成功、FAIL:失败、CGTZZ:成功通知中、SBTZZ:失败通知中、CGTZ:成功通知中、SBTZ:失败通知)			db_column: STATUS 
     */	
	private java.lang.String status;
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
					.append("TransactionId=").append(getTransactionId()).append(", ")
					.append("TxHash=").append(getTxHash()).append(", ")
					.append("CoinType=").append(getCoinType()).append(", ")
					.append("TxFrom=").append(getTxFrom()).append(", ")
					.append("TxTo=").append(getTxTo()).append(", ")
					.append("Amount=").append(getAmount()).append(", ")
					.append("Fee=").append(getFee()).append(", ")
					.append("Type=").append(getType()).append(", ")
					.append("Status=").append(getStatus()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

