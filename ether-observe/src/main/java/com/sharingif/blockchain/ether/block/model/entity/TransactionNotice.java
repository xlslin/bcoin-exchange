package com.sharingif.blockchain.ether.block.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;

public class TransactionNotice implements java.io.Serializable, IObjectDateOperationHistory {
	
	//columns START
    /**
     * id			db_column: ID 
     */	
	private java.lang.String id;
    /**
     * 通知地址			db_column: NOTICE_ADDRESS 
     */	
	private java.lang.String noticeAddress;
    /**
     * 通知类型(DEPOSIT:充值、WITHDRAWAL:取现)			db_column: NOTICE_TYPE 
     */	
	private java.lang.String noticeType;
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
	public void setNoticeAddress(java.lang.String noticeAddress) {
		this.noticeAddress = noticeAddress;
	}
	public java.lang.String getNoticeAddress() {
		return this.noticeAddress;
	}
	public void setNoticeType(java.lang.String noticeType) {
		this.noticeType = noticeType;
	}
	public java.lang.String getNoticeType() {
		return this.noticeType;
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
			.append("Id=").append(getId()).append(", ")
					.append("NoticeAddress=").append(getNoticeAddress()).append(", ")
					.append("NoticeType=").append(getNoticeType()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

