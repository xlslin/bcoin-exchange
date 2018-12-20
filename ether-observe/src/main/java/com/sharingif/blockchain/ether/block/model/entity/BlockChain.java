package com.sharingif.blockchain.ether.block.model.entity;


import com.sharingif.cube.batch.core.IDataId;
import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;
import com.sharingif.cube.components.sequence.Sequence;

import java.math.BigInteger;
import java.util.Date;

public class BlockChain implements java.io.Serializable, IObjectDateOperationHistory, IDataId {

	/**
	 * 状态(CSH:初始化)
	 */
	public static final String STATUS_INITIALIZE = "CSH";
	/**
	 * 状态(SJTBZ:数据同步中)
	 */
	public static final String STATUS_DATA_SYNC = "SJTBZ";
	/**
	 * 状态(WYZ:未验证)
	 */
	public static final String STATUS_UNVERIFIED = "WYZ";
	/**
	 * 状态(YZCG:验证成功)
	 */
	public static final String STATUS_VERIFY_SUCCESS = "YZCG";
	/**
	 * 状态(YZSB:验证失败)
	 */
	public static final String STATUS_VERIFY_FAILED = "YZSB";

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
     * 状态(CSH:初始化、SJTBZ:数据同步中、WYZ:未验证、YZCG:验证成功、YZSB:验证失败)			db_column: STATUS 
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
					.append("Status=").append(getStatus()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}

}

