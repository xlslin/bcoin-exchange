package com.sharingif.blockchain.bitcoin.withdrawal.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;

import java.math.BigInteger;
import java.util.Date;

public class WithdrawalVout implements java.io.Serializable, IObjectDateOperationHistory {
	
	//columns START
    /**
     * id			db_column: ID 
     */	
	private String id;
    /**
     * 取现唯一编号			db_column: WITHDRAWAL_ID 
     */	
	private String withdrawalId;
    /**
     * 交易hash			db_column: TX_HASH 
     */	
	private String txHash;
    /**
     * 地址			db_column: ADDRESS 
     */	
	private String address;
    /**
     * 交易输出位置			db_column: VOUT 
     */	
	private Integer vout;
    /**
     * 金额			db_column: AMOUNT 
     */	
	private BigInteger amount;
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
	public void setWithdrawalId(String withdrawalId) {
		this.withdrawalId = withdrawalId;
	}
	public String getWithdrawalId() {
		return this.withdrawalId;
	}
	public void setTxHash(String txHash) {
		this.txHash = txHash;
	}
	public String getTxHash() {
		return this.txHash;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress() {
		return this.address;
	}
	public void setVout(Integer vout) {
		this.vout = vout;
	}
	public Integer getVout() {
		return this.vout;
	}
	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}
	public BigInteger getAmount() {
		return this.amount;
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
		return new StringBuilder("WithdrawalVout [")
			.append("Id=").append(getId()).append(", ")
					.append("WithdrawalId=").append(getWithdrawalId()).append(", ")
					.append("TxHash=").append(getTxHash()).append(", ")
					.append("Address=").append(getAddress()).append(", ")
					.append("Vout=").append(getVout()).append(", ")
					.append("Amount=").append(getAmount()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

