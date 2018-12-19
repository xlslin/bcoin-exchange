package com.sharingif.blockchain.ether.block.model.entity;


import com.sharingif.cube.components.sequence.Sequence;

import java.math.BigInteger;
import java.util.Date;

public class BlockChainSync implements java.io.Serializable {
	
	//columns START
    /**
     * id			db_column: ID 
     */
	@Sequence(ref="uuidSequenceGenerator")
	private java.lang.String id;
    /**
     * 当前同步区块数			db_column: CURRENT_SYNC_BLOCK_NUMBER 
     */	
	private BigInteger currentSyncBlockNumber;
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
	public void setCurrentSyncBlockNumber(BigInteger currentSyncBlockNumber) {
		this.currentSyncBlockNumber = currentSyncBlockNumber;
	}
	public BigInteger getCurrentSyncBlockNumber() {
		return this.currentSyncBlockNumber;
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
		return new StringBuilder("BlockChainSync [")
			.append("Id=").append(getId()).append(", ")
					.append("CurrentSyncBlockNumber=").append(getCurrentSyncBlockNumber()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

