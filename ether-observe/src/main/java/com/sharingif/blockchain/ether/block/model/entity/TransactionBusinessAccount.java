package com.sharingif.blockchain.ether.block.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;
import com.sharingif.cube.components.sequence.Sequence;

import java.util.Date;

public class TransactionBusinessAccount implements java.io.Serializable, IObjectDateOperationHistory {
	
	//columns START
    /**
     * id			db_column: ID 
     */
	@Sequence(ref="uuidSequenceGenerator")
	private java.lang.String id;
    /**
     * 地址			db_column: ADDRESS 
     */	
	private java.lang.String address;
    /**
     * 币种			db_column: COIN_TYPE 
     */	
	private java.lang.String coinType;
    /**
     * 合约地址			db_column: CONTRACT_ADDRESS 
     */	
	private java.lang.String contractAddress;
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
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	public java.lang.String getAddress() {
		return this.address;
	}
	public void setCoinType(java.lang.String coinType) {
		this.coinType = coinType;
	}
	public java.lang.String getCoinType() {
		return this.coinType;
	}
	public void setContractAddress(java.lang.String contractAddress) {
		this.contractAddress = contractAddress;
	}
	public java.lang.String getContractAddress() {
		return this.contractAddress;
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
		return new StringBuilder("TransactionBusinessAccount [")
			.append("Id=").append(getId()).append(", ")
					.append("Address=").append(getAddress()).append(", ")
					.append("CoinType=").append(getCoinType()).append(", ")
					.append("ContractAddress=").append(getContractAddress()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

