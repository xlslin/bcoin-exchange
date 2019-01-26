package com.sharingif.blockchain.crypto.key.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;

import java.util.Date;

public class SecretKey implements java.io.Serializable, IObjectDateOperationHistory {
	
	//columns START
    /**
     * 地址			db_column: ADDRESS 
     */	
	private String address;
    /**
     * 扩展密钥id			db_column: EXTENDED_KEY_ID 
     */	
	private String extendedKeyId;
    /**
     * key路径			db_column: KEY_PATH 
     */	
	private String keyPath;
    /**
     * 文件路径			db_column: FILE_PATH 
     */	
	private String filePath;
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
	public void setExtendedKeyId(String extendedKeyId) {
		this.extendedKeyId = extendedKeyId;
	}
	public String getExtendedKeyId() {
		return this.extendedKeyId;
	}
	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}
	public String getKeyPath() {
		return this.keyPath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFilePath() {
		return this.filePath;
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
			.append("Address=").append(getAddress()).append(", ")
					.append("ExtendedKeyId=").append(getExtendedKeyId()).append(", ")
					.append("KeyPath=").append(getKeyPath()).append(", ")
					.append("FilePath=").append(getFilePath()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

