package com.sharingif.blockchain.bitcoin.block.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;
import com.sharingif.cube.components.sequence.Sequence;

import java.math.BigInteger;
import java.util.Date;

public class Transaction implements java.io.Serializable, IObjectDateOperationHistory {
	
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
     * tx index			db_column: TX_INDEX 
     */	
	private BigInteger txIndex;
    /**
     * 交易手续费			db_column: TX_FEE 
     */	
	private BigInteger txFee;
    /**
     * 交易时间			db_column: TX_TIME 
     */	
	private Date txTime;
    /**
     * 确认区块数			db_column: CONFIRM_BLOCK_NUMBER 
     */	
	private Integer confirmBlockNumber;
    /**
     * 交易状态(WYZ:未验证、QKYZYX:区块验证有效、QKYZWX:区块验证无效)			db_column: TX_STATUS 
     */	
	private String txStatus;
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
	public void setTxIndex(BigInteger txIndex) {
		this.txIndex = txIndex;
	}
	public BigInteger getTxIndex() {
		return this.txIndex;
	}
	public void setTxFee(BigInteger txFee) {
		this.txFee = txFee;
	}
	public BigInteger getTxFee() {
		return this.txFee;
	}
	public void setTxTime(Date txTime) {
		this.txTime = txTime;
	}
	public Date getTxTime() {
		return this.txTime;
	}
	public void setConfirmBlockNumber(Integer confirmBlockNumber) {
		this.confirmBlockNumber = confirmBlockNumber;
	}
	public Integer getConfirmBlockNumber() {
		return this.confirmBlockNumber;
	}
	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}
	public String getTxStatus() {
		return this.txStatus;
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
		return new StringBuilder("Transaction [")
			.append("Id=").append(getId()).append(", ")
					.append("BlockNumber=").append(getBlockNumber()).append(", ")
					.append("BlockHash=").append(getBlockHash()).append(", ")
					.append("TxHash=").append(getTxHash()).append(", ")
					.append("TxIndex=").append(getTxIndex()).append(", ")
					.append("TxFee=").append(getTxFee()).append(", ")
					.append("TxTime=").append(getTxTime()).append(", ")
					.append("ConfirmBlockNumber=").append(getConfirmBlockNumber()).append(", ")
					.append("TxStatus=").append(getTxStatus()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

