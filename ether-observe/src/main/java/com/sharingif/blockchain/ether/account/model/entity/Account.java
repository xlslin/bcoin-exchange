package com.sharingif.blockchain.ether.account.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;

public class Account implements java.io.Serializable, IObjectDateOperationHistory {
	
	//columns START
    /**
     * id			db_column: ID 
     */	
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
	private BigDecimal totalIn;
    /**
     * 出账总额			db_column: TOTAL_OUT 
     */	
	private BigDecimal totalOut;
    /**
     * 余额			db_column: BALANCE 
     */	
	private BigDecimal balance;
    /**
     * 冻结金额			db_column: FROZEN_AMOUNT 
     */	
	private BigDecimal frozenAmount;
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
	public void setTotalIn(BigDecimal totalIn) {
		this.totalIn = totalIn;
	}
	public BigDecimal getTotalIn() {
		return this.totalIn;
	}
	public void setTotalOut(BigDecimal totalOut) {
		this.totalOut = totalOut;
	}
	public BigDecimal getTotalOut() {
		return this.totalOut;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getBalance() {
		return this.balance;
	}
	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
	}
	public BigDecimal getFrozenAmount() {
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

