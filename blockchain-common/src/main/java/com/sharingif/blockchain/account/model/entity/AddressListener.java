package com.sharingif.blockchain.account.model.entity;


public class AddressListener implements java.io.Serializable {
	
	//columns START
    /**
     * 区块类型(Ether、Bitcoin)			db_column: BLOCK_TYPE 
     */	
	private java.lang.String blockType;
    /**
     * 地址			db_column: ADDRESS 
     */	
	private java.lang.String address;
    /**
     * 创建时间			db_column: CREATE_TIME 
     */	
	private Date createTime;
    /**
     * 修改时间			db_column: MODIFY_TIME 
     */	
	private Date modifyTime;
	//columns END

	public void setBlockType(java.lang.String blockType) {
		this.blockType = blockType;
	}
	public java.lang.String getBlockType() {
		return this.blockType;
	}
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	public java.lang.String getAddress() {
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
			.append("BlockType=").append(getBlockType()).append(", ")
					.append("Address=").append(getAddress()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

