package com.sharingif.blockchain.notice.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;

import java.util.Date;

public class TransactionNotice implements java.io.Serializable, IObjectDateOperationHistory {
	
	//columns START
    /**
     * 区块类型(Bitcoin、Ether)			db_column: BLOCK_TYPE 
     */	
	private String blockType;
    /**
     * 通知类型(DEPOSIT:充值、WITHDRAWAL:取现)			db_column: NOTICE_TYPE 
     */	
	private String noticeType;
    /**
     * 通知地址			db_column: NOTICE_ADDRESS 
     */	
	private String noticeAddress;
    /**
     * 创建时间			db_column: CREATE_TIME 
     */	
	private Date createTime;
    /**
     * 修改时间			db_column: MODIFY_TIME 
     */	
	private Date modifyTime;
	//columns END

	public void setBlockType(String blockType) {
		this.blockType = blockType;
	}
	public String getBlockType() {
		return this.blockType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	public String getNoticeType() {
		return this.noticeType;
	}
	public void setNoticeAddress(String noticeAddress) {
		this.noticeAddress = noticeAddress;
	}
	public String getNoticeAddress() {
		return this.noticeAddress;
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
		return new StringBuilder("TransactionNotice [")
			.append("BlockType=").append(getBlockType()).append(", ")
					.append("NoticeType=").append(getNoticeType()).append(", ")
					.append("NoticeAddress=").append(getNoticeAddress()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

