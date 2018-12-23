package com.sharingif.blockchain.ether.block.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;
import com.sharingif.cube.components.sequence.Sequence;

import java.math.BigInteger;
import java.util.Date;

public class BlockChainSync implements java.io.Serializable, IObjectDateOperationHistory {

	/**
	 * 类型(00:区块同步)
	 */
	public static final String TYPE_SYNC = "00";
	/**
	 * 类型(01:区块验证)
	 */
	public static final String TYPE_CONFIRMATION = "01";

	//columns START
    /**
     * id			db_column: ID 
     */
	@Sequence(ref="uuidSequenceGenerator")
	private String id;
    /**
     * 当前同步区块数			db_column: BLOCK_NUMBER 
     */	
	private BigInteger blockNumber;
    /**
     * 类型(00:区块同步、01:区块验证)			db_column: TYPE 
     */	
	private String type;
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
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
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

