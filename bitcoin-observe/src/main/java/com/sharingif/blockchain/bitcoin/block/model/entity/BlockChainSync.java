package com.sharingif.blockchain.bitcoin.block.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;

public class BlockChainSync implements java.io.Serializable, IObjectDateOperationHistory {
	
	//columns START
    /**
     * id			db_column: ID 
     */	
	private java.lang.String id;
    /**
     * 区块数			db_column: BLOCK_NUMBER 
     */	
	private java.lang.Long blockNumber;
    /**
     * 类型(00:区块同步)			db_column: TYPE 
     */	
	private java.lang.String type;
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
	public void setType(java.lang.String type) {
		this.type = type;
	}
	public java.lang.String getType() {
		return this.type;
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
					.append("BlockNumber=").append(getBlockNumber()).append(", ")
					.append("Type=").append(getType()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

