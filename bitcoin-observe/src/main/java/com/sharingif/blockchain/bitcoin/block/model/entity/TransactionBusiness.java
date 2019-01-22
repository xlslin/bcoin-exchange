package com.sharingif.blockchain.bitcoin.block.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;
import com.sharingif.cube.components.sequence.Sequence;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class TransactionBusiness implements java.io.Serializable, IObjectDateOperationHistory {
	
	//columns START
    /**
     * id			db_column: ID 
     */
	@Sequence(ref="uuidSequenceGenerator")
	private String id;
    /**
     * 区块号			db_column: BLOCK_NUMBER 
     */	
	private BigInteger blockNumber;
    /**
     * 区块hash			db_column: BLOCK_HASH 
     */	
	private String blockHash;
    /**
     * 交易hash			db_column: TX_HASH 
     */	
	private String txHash;
    /**
     * 交易id			db_column: TRANSACTION_ID 
     */	
	private String transactionId;
    /**
     * 币种			db_column: COIN_TYPE 
     */	
	private String coinType;
    /**
     * FORM地址			db_column: TX_FROM 
     */	
	private String txFrom;
    /**
     * TO地址			db_column: TX_TO 
     */	
	private String txTo;
    /**
     * 金额			db_column: AMOUNT 
     */	
	private BigDecimal amount;
    /**
     * 手续费			db_column: FEE 
     */	
	private BigDecimal fee;
    /**
     * 交易类型(DEPOSIT:充值、WITHDRAWAL:提现)			db_column: TYPE 
     */	
	private String type;
    /**
     * 处理状态(WCL:未处理、CSHTZZ:初始化通知中、CSHTZCG:初始化通知成功、JYWCTZZ:交易完成通知中、JYWCTZCG:交易完成通知成功)			db_column: STATUS 
     */	
	private String status;
    /**
     * 处理状态(WQS:未清算、ZBQS:准备清算、QSZ:清算中、QSWC:清算完成)			db_column: SETTLE_STATUS 
     */	
	private String settleStatus;
    /**
     * 交易状态(WYZ:未验证、QKYZYX:区块验证有效、QKYZWX:区块验证无效)			db_column: TX_STATUS 
     */	
	private String txStatus;
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

	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	public void setBlockNumber(BigInteger blockNumber) {
		this.blockNumber = blockNumber;
	}
	public BigInteger getBlockNumber() {
		return this.blockNumber;
	}
	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}
	public String getBlockHash() {
		return this.blockHash;
	}
	public void setTxHash(String txHash) {
		this.txHash = txHash;
	}
	public String getTxHash() {
		return this.txHash;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransactionId() {
		return this.transactionId;
	}
	public void setCoinType(String coinType) {
		this.coinType = coinType;
	}
	public String getCoinType() {
		return this.coinType;
	}
	public void setTxFrom(String txFrom) {
		this.txFrom = txFrom;
	}
	public String getTxFrom() {
		return this.txFrom;
	}
	public void setTxTo(String txTo) {
		this.txTo = txTo;
	}
	public String getTxTo() {
		return this.txTo;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getAmount() {
		return this.amount;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public BigDecimal getFee() {
		return this.fee;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return this.type;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return this.status;
	}
	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}
	public String getSettleStatus() {
		return this.settleStatus;
	}
	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}
	public String getTxStatus() {
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
					.append("TxHash=").append(getTxHash()).append(", ")
					.append("TransactionId=").append(getTransactionId()).append(", ")
					.append("CoinType=").append(getCoinType()).append(", ")
					.append("TxFrom=").append(getTxFrom()).append(", ")
					.append("TxTo=").append(getTxTo()).append(", ")
					.append("Amount=").append(getAmount()).append(", ")
					.append("Fee=").append(getFee()).append(", ")
					.append("Type=").append(getType()).append(", ")
					.append("Status=").append(getStatus()).append(", ")
					.append("SettleStatus=").append(getSettleStatus()).append(", ")
					.append("TxStatus=").append(getTxStatus()).append(", ")
					.append("TxTime=").append(getTxTime()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

