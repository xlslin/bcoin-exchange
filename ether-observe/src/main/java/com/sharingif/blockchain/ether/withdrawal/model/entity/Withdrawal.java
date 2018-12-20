package com.sharingif.blockchain.ether.withdrawal.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;
import com.sharingif.cube.components.sequence.Sequence;

import java.math.BigInteger;
import java.util.Date;

public class Withdrawal implements java.io.Serializable, IObjectDateOperationHistory {

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
     * 取现唯一编号			db_column: WITHDRAWAL_ID 
     */	
	private java.lang.String withdrawalId;
    /**
     * ETH交易id			db_column: TRANSACTION_ID 
     */	
	private java.lang.String transactionId;
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
     * gas limit			db_column: GAS_LIMIT 
     */	
	private java.lang.Long gasLimit;
    /**
     * gas used			db_column: GAS_USED 
     */	
	private java.lang.Long gasUsed;
    /**
     * gas price			db_column: GAS_PRICE 
     */	
	private java.lang.Long gasPrice;
    /**
     * 交易hash			db_column: TX_HASH 
     */	
	private java.lang.String txHash;
    /**
     * 处理状态(CZWCL:提现未处理、CZCLZ:提现处理中、SUCCESS:提现成功、FAIL:提现失败)			db_column: STATUS 
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
	public void setWithdrawalId(java.lang.String withdrawalId) {
		this.withdrawalId = withdrawalId;
	}
	public java.lang.String getWithdrawalId() {
		return this.withdrawalId;
	}
	public void setTransactionId(java.lang.String transactionId) {
		this.transactionId = transactionId;
	}
	public java.lang.String getTransactionId() {
		return this.transactionId;
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
	public void setGasLimit(java.lang.Long gasLimit) {
		this.gasLimit = gasLimit;
	}
	public java.lang.Long getGasLimit() {
		return this.gasLimit;
	}
	public void setGasUsed(java.lang.Long gasUsed) {
		this.gasUsed = gasUsed;
	}
	public java.lang.Long getGasUsed() {
		return this.gasUsed;
	}
	public void setGasPrice(java.lang.Long gasPrice) {
		this.gasPrice = gasPrice;
	}
	public java.lang.Long getGasPrice() {
		return this.gasPrice;
	}
	public void setTxHash(java.lang.String txHash) {
		this.txHash = txHash;
	}
	public java.lang.String getTxHash() {
		return this.txHash;
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
		return new StringBuilder("Withdrawal [")
			.append("Id=").append(getId()).append(", ")
					.append("WithdrawalId=").append(getWithdrawalId()).append(", ")
					.append("TransactionId=").append(getTransactionId()).append(", ")
					.append("CoinType=").append(getCoinType()).append(", ")
					.append("TxFrom=").append(getTxFrom()).append(", ")
					.append("TxTo=").append(getTxTo()).append(", ")
					.append("Amount=").append(getAmount()).append(", ")
					.append("Fee=").append(getFee()).append(", ")
					.append("GasLimit=").append(getGasLimit()).append(", ")
					.append("GasUsed=").append(getGasUsed()).append(", ")
					.append("GasPrice=").append(getGasPrice()).append(", ")
					.append("TxHash=").append(getTxHash()).append(", ")
					.append("Status=").append(getStatus()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

