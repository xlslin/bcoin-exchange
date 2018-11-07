package com.sharingif.blockchain.crypto.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;

import java.util.Date;

public class ExtendedKey implements java.io.Serializable, IObjectDateOperationHistory {
	
	//columns START
    /**
     * id			db_column: ID 
     */	
	private java.lang.String id;
    /**
     * 助记词id			db_column: MNEMONIC_ID 
     */	
	private java.lang.String mnemonicId;
    /**
     * 扩展密钥路径			db_column: EXTENDED_KEY_PATH 
     */	
	private java.lang.String extendedKeyPath;
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
	public void setMnemonicId(java.lang.String mnemonicId) {
		this.mnemonicId = mnemonicId;
	}
	public java.lang.String getMnemonicId() {
		return this.mnemonicId;
	}
	public void setExtendedKeyPath(java.lang.String extendedKeyPath) {
		this.extendedKeyPath = extendedKeyPath;
	}
	public java.lang.String getExtendedKeyPath() {
		return this.extendedKeyPath;
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
		return new StringBuilder("ExtendedKey [")
			.append("Id=").append(getId()).append(", ")
					.append("MnemonicId=").append(getMnemonicId()).append(", ")
					.append("ExtendedKeyPath=").append(getExtendedKeyPath()).append(", ")
					.append("Password=").append(getPassword()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

