package com.sharingif.blockchain.crypto.mnemonic.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;

import java.util.Date;


public class Mnemonic implements java.io.Serializable, IObjectDateOperationHistory {
	
	//columns START
    /**
     * id			db_column: ID 
     */	
	private String id;
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
	/**
	 * 助记词
	 */
	private String mnemonic;

	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
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
	public String getMnemonic() {
		return mnemonic;
	}
	public void setMnemonic(String mnemonic) {
		this.mnemonic = mnemonic;
	}

	public String getMnemonicDirectory() {
		return getFilePath().split("/")[0];
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Mnemonic{");
		sb.append("id='").append(id).append('\'');
		sb.append(", filePath='").append(filePath).append('\'');
		sb.append(", createTime=").append(createTime);
		sb.append(", modifyTime=").append(modifyTime);
		sb.append(", mnemonic='").append(mnemonic).append('\'');
		sb.append('}');
		return sb.toString();
	}
}

