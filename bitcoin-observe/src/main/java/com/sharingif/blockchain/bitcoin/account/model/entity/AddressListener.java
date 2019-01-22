package com.sharingif.blockchain.bitcoin.account.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;

import java.util.Date;

public class AddressListener implements java.io.Serializable, IObjectDateOperationHistory {
	
	//columns START
    /**
     * 地址			db_column: ADDRESS 
     */	
	private String address;
    /**
     * 创建时间			db_column: CREATE_TIME 
     */	
	private Date createTime;
    /**
     * 修改时间			db_column: MODIFY_TIME 
     */	
	private Date modifyTime;
	//columns END

	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress() {
		return this.address;
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
		return new StringBuilder("AddressListener [")
			.append("Address=").append(getAddress()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

