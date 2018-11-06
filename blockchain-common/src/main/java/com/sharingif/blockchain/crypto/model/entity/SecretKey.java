package com.sharingif.blockchain.crypto.model.entity;


import java.util.Date;

public class SecretKey implements java.io.Serializable {
	
	//columns START
    /**
     * id			db_column: ID 
     */	
	private java.lang.String id;
    /**
     * 扩展密钥id			db_column: EXTENDED_KEY_ID 
     */	
	private java.lang.String extendedKeyId;
    /**
     * 地址			db_column: ADDRESS 
     */	
	private java.lang.String address;
    /**
     * 密码			db_column: PASSWORD 
     */	
	private java.lang.String password;
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
	public void setExtendedKeyId(java.lang.String extendedKeyId) {
		this.extendedKeyId = extendedKeyId;
	}
	public java.lang.String getExtendedKeyId() {
		return this.extendedKeyId;
	}
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	public java.lang.String getAddress() {
		return this.address;
	}
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	public java.lang.String getPassword() {
		return this.password;
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
		return new StringBuilder("SecretKey [")
			.append("Id=").append(getId()).append(", ")
					.append("ExtendedKeyId=").append(getExtendedKeyId()).append(", ")
					.append("Address=").append(getAddress()).append(", ")
					.append("Password=").append(getPassword()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

