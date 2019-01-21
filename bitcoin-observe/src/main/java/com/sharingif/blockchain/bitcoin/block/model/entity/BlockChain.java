package com.sharingif.blockchain.bitcoin.block.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;

public class BlockChain implements java.io.Serializable, IObjectDateOperationHistory {
	
	//columns START
    /**
     * id			db_column: ID 
     */	
	private java.lang.String id;
    /**
     * 区块号			db_column: BLOCK_NUMBER 
     */	
	private java.lang.Long blockNumber;
    /**
     * 验证区块号			db_column: VERIFY_BLOCK_NUMBER 
     */	
	private java.lang.Long verifyBlockNumber;
    /**
     * 区块hash			db_column: HASH 
     */	
	private java.lang.String hash;
    /**
     * 块创建时间			db_column: BLOCK_CREATE_TIME 
     */	
	private Date blockCreateTime;
    /**
     * 状态(WCL:未处理、QKTBZ:区块同步中、WYZ:未验证、QKYZYX:区块验证有效、QKYZWX:区块验证无效)			db_column: STATUS 
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
	public void setBlockNumber(java.lang.Long blockNumber) {
		this.blockNumber = blockNumber;
	}
	public java.lang.Long getBlockNumber() {
		return this.blockNumber;
	}
	public void setVerifyBlockNumber(java.lang.Long verifyBlockNumber) {
		this.verifyBlockNumber = verifyBlockNumber;
	}
	public java.lang.Long getVerifyBlockNumber() {
		return this.verifyBlockNumber;
	}
	public void setHash(java.lang.String hash) {
		this.hash = hash;
	}
	public java.lang.String getHash() {
		return this.hash;
	}
	public void setBlockCreateTime(Date blockCreateTime) {
		this.blockCreateTime = blockCreateTime;
	}
	public Date getBlockCreateTime() {
		return this.blockCreateTime;
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
		return new StringBuilder("BlockChain [")
			.append("Id=").append(getId()).append(", ")
					.append("BlockNumber=").append(getBlockNumber()).append(", ")
					.append("VerifyBlockNumber=").append(getVerifyBlockNumber()).append(", ")
					.append("Hash=").append(getHash()).append(", ")
					.append("BlockCreateTime=").append(getBlockCreateTime()).append(", ")
					.append("Status=").append(getStatus()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

