package com.sharingif.blockchain.account.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;
import com.sharingif.cube.components.sequence.Sequence;

import java.math.BigDecimal;
import java.util.Date;

public class AccountJnl implements java.io.Serializable, IObjectDateOperationHistory {
	
	//columns START
    /**
     * id			db_column: ID 
     */
	@Sequence(ref="uuidSequenceGenerator")
	private java.lang.String id;
    /**
     * from地址			db_column: ACCOUNT_FROM 
     */	
	private java.lang.String accountFrom;
    /**
     * to地址			db_column: ACCOUNT_TO 
     */	
	private java.lang.String accountTo;
    /**
     * 币种			db_column: COIN_TYPE 
     */	
	private java.lang.String coinType;
    /**
     * 金额			db_column: BALANCE 
     */	
	private BigDecimal balance;
    /**
     * 类型(00:转入、01:转出、10:冻结、11:解冻)			db_column: TYPE 
     */	
	private java.lang.String type;
    /**
     * 交易id			db_column: TX_ID 
     */	
	private java.lang.String txId;
    /**
     * 交易时间			db_column: TRANS_TIME 
     */	
	private Date transTime;
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
	public void setAccountFrom(java.lang.String accountFrom) {
		this.accountFrom = accountFrom;
	}
	public java.lang.String getAccountFrom() {
		return this.accountFrom;
	}
	public void setAccountTo(java.lang.String accountTo) {
		this.accountTo = accountTo;
	}
	public java.lang.String getAccountTo() {
		return this.accountTo;
	}
	public void setCoinType(java.lang.String coinType) {
		this.coinType = coinType;
	}
	public java.lang.String getCoinType() {
		return this.coinType;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getBalance() {
		return this.balance;
	}
	public void setType(java.lang.String type) {
		this.type = type;
	}
	public java.lang.String getType() {
		return this.type;
	}
	public void setTxId(java.lang.String txId) {
		this.txId = txId;
	}
	public java.lang.String getTxId() {
		return this.txId;
	}
	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}
	public Date getTransTime() {
		return this.transTime;
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
		return new StringBuilder("AccountJnl [")
			.append("Id=").append(getId()).append(", ")
					.append("AccountFrom=").append(getAccountFrom()).append(", ")
					.append("AccountTo=").append(getAccountTo()).append(", ")
					.append("CoinType=").append(getCoinType()).append(", ")
					.append("Balance=").append(getBalance()).append(", ")
					.append("Type=").append(getType()).append(", ")
					.append("TxId=").append(getTxId()).append(", ")
					.append("TransTime=").append(getTransTime()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

