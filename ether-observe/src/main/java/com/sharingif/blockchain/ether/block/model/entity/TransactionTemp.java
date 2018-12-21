package com.sharingif.blockchain.ether.block.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;
import com.sharingif.cube.components.sequence.Sequence;

import java.math.BigInteger;
import java.util.Date;

public class TransactionTemp implements java.io.Serializable, IObjectDateOperationHistory {

	/**
	 * 交易状态(WCL:未处理)
	 */
	public static final String TX_STATUS_UNPROCESSED = "WCL";
	/**
	 * 交易状态(CLZ:处理中)
	 */
	public static final String TX_STATUS_PROCESSING = "CLZ";
	/**
	 * 交易状态(YCL:已处理)
	 */
	public static final String TX_STATUS_PROCESSED = "YCL";

	//columns START
    /**
     * id			db_column: ID 
     */
	@Sequence(ref="uuidSequenceGenerator")
	private java.lang.String id;
    /**
     * 区块id			db_column: BLOCK_CHAIN_ID 
     */	
	private java.lang.String blockChainId;
    /**
     * 区块号			db_column: BLOCK_NUMBER 
     */	
	private BigInteger blockNumber;
    /**
     * 区块hash			db_column: BLOCK_HASH 
     */	
	private java.lang.String blockHash;
    /**
     * 交易hash			db_column: TX_HASH 
     */	
	private java.lang.String txHash;
    /**
     * 交易状态(WCL:未处理、CLZ:处理中、YCL:已处理)			db_column: TX_STATUS 
     */	
	private java.lang.String txStatus;
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
	public void setBlockChainId(java.lang.String blockChainId) {
		this.blockChainId = blockChainId;
	}
	public java.lang.String getBlockChainId() {
		return this.blockChainId;
	}
	public void setBlockNumber(BigInteger blockNumber) {
		this.blockNumber = blockNumber;
	}
	public BigInteger getBlockNumber() {
		return this.blockNumber;
	}
	public void setBlockHash(java.lang.String blockHash) {
		this.blockHash = blockHash;
	}
	public java.lang.String getBlockHash() {
		return this.blockHash;
	}
	public void setTxHash(java.lang.String txHash) {
		this.txHash = txHash;
	}
	public java.lang.String getTxHash() {
		return this.txHash;
	}
	public void setTxStatus(java.lang.String txStatus) {
		this.txStatus = txStatus;
	}
	public java.lang.String getTxStatus() {
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
		return new StringBuilder("TransactionTemp [")
			.append("Id=").append(getId()).append(", ")
					.append("BlockChainId=").append(getBlockChainId()).append(", ")
					.append("BlockNumber=").append(getBlockNumber()).append(", ")
					.append("BlockHash=").append(getBlockHash()).append(", ")
					.append("TxHash=").append(getTxHash()).append(", ")
					.append("TxStatus=").append(getTxStatus()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

