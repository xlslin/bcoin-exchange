package com.sharingif.blockchain.bitcoin.withdrawal.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;

import java.math.BigInteger;
import java.util.Date;

public class WithdrawalTransaction implements java.io.Serializable, IObjectDateOperationHistory {

	/**
	 * 处理状态(WCL:未处理)
	 */
	public static final String STATUS_UNTREATED = "WCL";
	/**
	 * 处理状态(CLZ:处理中)
	 */
	public static final String STATUS_PROCESSING = "CLZ";
	/**
	 * 处理状态(YCL:已处理)
	 */
	public static final String STATUS_PROCESSED = "YCL";
	
	//columns START
    /**
     * 交易hash			db_column: TX_HASH 
     */	
	private String txHash;
    /**
     * 手续费			db_column: FEE 
     */	
	private BigInteger fee;
    /**
     * 处理状态(WCL:未处理、CLZ:处理中、YCL:已处理)			db_column: STATUS 
     */	
	private String status;
    /**
     * 创建时间			db_column: CREATE_TIME 
     */	
	private Date createTime;
    /**
     * 修改时间			db_column: MODIFY_TIME 
     */	
	private Date modifyTime;
	//columns END

	public void setTxHash(String txHash) {
		this.txHash = txHash;
	}
	public String getTxHash() {
		return this.txHash;
	}
	public void setFee(BigInteger fee) {
		this.fee = fee;
	}
	public BigInteger getFee() {
		return this.fee;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
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
		return new StringBuilder("WithdrawalTransaction [")
			.append("TxHash=").append(getTxHash()).append(", ")
					.append("Fee=").append(getFee()).append(", ")
					.append("Status=").append(getStatus()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

