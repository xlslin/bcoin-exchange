package com.sharingif.wallet.user.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;
import com.sharingif.cube.components.sequence.Sequence;

import java.util.Date;

public class UserLoginJnl implements java.io.Serializable, IObjectDateOperationHistory {
	
	//columns START
    /**
     * 编号			db_column: ID 
     */
	@Sequence(ref="uuidSequenceGenerator")
	private java.lang.String id;
    /**
     * 用户名			db_column: USERNAME 
     */	
	private java.lang.String username;
    /**
     * 登录IP			db_column: LOGIN_IP 
     */	
	private java.lang.String loginIp;
    /**
     * 登录设备编号			db_column: LOGIN_DEVICE_CODE 
     */	
	private java.lang.String loginDeviceCode;
    /**
     * 登录类型(00:邮箱验证码登录、01:TOKEN登录)			db_column: LOGIN_TYPE 
     */	
	private java.lang.String loginType;
    /**
     * 登录状态(S:成功、F:失败)			db_column: LOGIN_STATUS 
     */	
	private java.lang.String loginStatus;
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
	public void setUsername(java.lang.String username) {
		this.username = username;
	}
	public java.lang.String getUsername() {
		return this.username;
	}
	public void setLoginIp(java.lang.String loginIp) {
		this.loginIp = loginIp;
	}
	public java.lang.String getLoginIp() {
		return this.loginIp;
	}
	public void setLoginDeviceCode(java.lang.String loginDeviceCode) {
		this.loginDeviceCode = loginDeviceCode;
	}
	public java.lang.String getLoginDeviceCode() {
		return this.loginDeviceCode;
	}
	public void setLoginType(java.lang.String loginType) {
		this.loginType = loginType;
	}
	public java.lang.String getLoginType() {
		return this.loginType;
	}
	public void setLoginStatus(java.lang.String loginStatus) {
		this.loginStatus = loginStatus;
	}
	public java.lang.String getLoginStatus() {
		return this.loginStatus;
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
		return new StringBuilder("UserLoginJnl [")
			.append("Id=").append(getId()).append(", ")
					.append("Username=").append(getUsername()).append(", ")
					.append("LoginIp=").append(getLoginIp()).append(", ")
					.append("LoginDeviceCode=").append(getLoginDeviceCode()).append(", ")
					.append("LoginType=").append(getLoginType()).append(", ")
					.append("LoginStatus=").append(getLoginStatus()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

