package com.sharingif.blockchain.account.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;
import com.sharingif.cube.components.sequence.Sequence;

import java.math.BigInteger;
import java.util.Date;

public class Account implements java.io.Serializable, IObjectDateOperationHistory {

	/**
	 * 账户状态(NORMAL:正常)
	 */
	public static final String STATUS_NORMAL = "NORMAL";
	/**
	 * 账户状态(NORMAL:LOCK:锁定)
	 */
	public static final String STATUS_LOCK = "LOCK";
	
	//columns START
    /**
     * id			db_column: ID 
     */
	@Sequence(ref="uuidSequenceGenerator")
	private java.lang.String id;
    /**
     * 地址			db_column: ADDRESS 
     */	
	private java.lang.String address;
    /**
     * 币种			db_column: COIN_TYPE 
     */	
	private java.lang.String coinType;
    /**
     * 入账总额			db_column: TOTAL_IN 
     */	
	private BigInteger totalIn;
    /**
     * 出账总额			db_column: TOTAL_OUT 
     */	
	private BigInteger totalOut;
    /**
     * 余额			db_column: BALANCE 
     */	
	private BigInteger balance;
    /**
     * 冻结金额			db_column: FROZEN_AMOUNT 
     */	
	private BigInteger frozenAmount;
    /**
     * 账户状态(NORMAL:正常、LOCK:锁定)			db_column: STATUS 
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
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	public java.lang.String getAddress() {
		return this.address;
	}
	public void setCoinType(java.lang.String coinType) {
		this.coinType = coinType;
	}
	public java.lang.String getCoinType() {
		return this.coinType;
	}
	public void setTotalIn(BigInteger totalIn) {
		this.totalIn = totalIn;
	}
	public BigInteger getTotalIn() {
		return this.totalIn;
	}
	public void setTotalOut(BigInteger totalOut) {
		this.totalOut = totalOut;
	}
	public BigInteger getTotalOut() {
		return this.totalOut;
	}
	public void setBalance(BigInteger balance) {
		this.balance = balance;
	}
	public BigInteger getBalance() {
		return this.balance;
	}
	public void setFrozenAmount(BigInteger frozenAmount) {
		this.frozenAmount = frozenAmount;
	}
	public BigInteger getFrozenAmount() {
		return this.frozenAmount;
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
		return new StringBuilder("Account [")
			.append("Id=").append(getId()).append(", ")
					.append("Address=").append(getAddress()).append(", ")
					.append("CoinType=").append(getCoinType()).append(", ")
					.append("TotalIn=").append(getTotalIn()).append(", ")
					.append("TotalOut=").append(getTotalOut()).append(", ")
					.append("Balance=").append(getBalance()).append(", ")
					.append("FrozenAmount=").append(getFrozenAmount()).append(", ")
					.append("Status=").append(getStatus()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

