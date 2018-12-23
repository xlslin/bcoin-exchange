package com.sharingif.blockchain.ether.block.model.entity;


import com.sharingif.cube.batch.core.IDataId;
import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;
import com.sharingif.cube.components.sequence.Sequence;

import java.math.BigInteger;
import java.util.Date;

public class BlockChain implements java.io.Serializable, IObjectDateOperationHistory, IDataId {

	/**
	 * 状态(WCL:未处理)
	 */
	public static final String STATUS_UNTREATED = "WCL";
	/**
	 * 状态(QKTBZ:区块同步中)
	 */
	public static final String STATUS_BLOCK_SYNCHING = "QKTBZ";
	/**
	 * 状态(WYZ:未验证)
	 */
	public static final String STATUS_UNVERIFIED = "WYZ";
	/**
	 * 状态(QKYZYX:区块验证有效)
	 */
	public static final String STATUS_VERIFY_VALID = "QKYZYX";
	/**
	 * 状态(QKYZWX:区块验证无效)
	 */
	public static final String STATUS_VERIFY_INVALID = "QKYZWX";
	
	//columns START
    /**
     * id			db_column: ID 
     */
	@Sequence(ref="uuidSequenceGenerator")
	private java.lang.String id;
    /**
     * 区块号			db_column: BLOCK_NUMBER 
     */	
	private BigInteger blockNumber;
    /**
     * 验证区块号			db_column: VERIFY_BLOCK_NUMBER 
     */	
	private BigInteger verifyBlockNumber;
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

	@Override
	public String getDateId() {
		return getId();
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getId() {
		return this.id;
	}
	public void setBlockNumber(BigInteger blockNumber) {
		this.blockNumber = blockNumber;
	}
	public BigInteger getBlockNumber() {
		return this.blockNumber;
	}
	public void setVerifyBlockNumber(BigInteger verifyBlockNumber) {
		this.verifyBlockNumber = verifyBlockNumber;
	}
	public BigInteger getVerifyBlockNumber() {
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

