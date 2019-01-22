package com.sharingif.blockchain.bitcoin.account.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;
import com.sharingif.cube.components.sequence.Sequence;

import java.math.BigInteger;
import java.util.Date;

public class AccountFrozenJnl implements java.io.Serializable, IObjectDateOperationHistory {

	/**
	 * 类型(00:冻结)
	 */
	public static final String TYPE_FROZEN = "00";
	/**
	 * 类型(01:解冻)
	 */
	public static final String TYPE_UNFROZEN = "01";
	
	//columns START
    /**
     * id			db_column: ID 
     */
	@Sequence(ref="uuidSequenceGenerator")
	private String id;
    /**
     * from地址			db_column: ACCOUNT_FROM 
     */	
	private String accountFrom;
    /**
     * to地址			db_column: ACCOUNT_TO 
     */	
	private String accountTo;
    /**
     * 币种			db_column: COIN_TYPE 
     */	
	private String coinType;
    /**
     * 金额			db_column: BALANCE 
     */	
	private BigInteger balance;
    /**
     * 类型(00:冻结、01:解冻)			db_column: TYPE 
     */	
	private String type;
    /**
     * 交易id			db_column: TX_ID 
     */	
	private String txId;
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

	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	public void setAccountFrom(String accountFrom) {
		this.accountFrom = accountFrom;
	}
	public String getAccountFrom() {
		return this.accountFrom;
	}
	public void setAccountTo(String accountTo) {
		this.accountTo = accountTo;
	}
	public String getAccountTo() {
		return this.accountTo;
	}
	public void setCoinType(String coinType) {
		this.coinType = coinType;
	}
	public String getCoinType() {
		return this.coinType;
	}
	public void setBalance(BigInteger balance) {
		this.balance = balance;
	}
	public BigInteger getBalance() {
		return this.balance;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return this.type;
	}
	public void setTxId(String txId) {
		this.txId = txId;
	}
	public String getTxId() {
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
		return new StringBuilder("AccountFrozenJnl [")
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

