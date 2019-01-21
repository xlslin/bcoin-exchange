package com.sharingif.wallet.user.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;
import com.sharingif.cube.components.sequence.Sequence;

import java.util.Date;

public class User implements java.io.Serializable, IObjectDateOperationHistory {
	
	//columns START
    /**
     * 用户编号			db_column: USER_ID 
     */
	@Sequence(ref="uuidSequenceGenerator")
	private java.lang.String userId;
    /**
     * 用户名			db_column: USERNAME 
     */	
	private java.lang.String username;
    /**
     * 密码			db_column: PASSWORD 
     */	
	private java.lang.String password;
    /**
     * 支付密码			db_column: PAYMENT_PASSWORD 
     */	
	private java.lang.String paymentPassword;
    /**
     * 支付密码验证失败次数			db_column: PAYMENT_PASSWORD_FAIL_COUNTER 
     */	
	private java.lang.Integer paymentPasswordFailCounter;
    /**
     * 昵称			db_column: NICK 
     */	
	private java.lang.String nick;
    /**
     * 手机前缀			db_column: MOBILE_PREFIX 
     */	
	private java.lang.String mobilePrefix;
    /**
     * 手机			db_column: MOBILE 
     */	
	private java.lang.String mobile;
    /**
     * 邮箱			db_column: EMAIL 
     */	
	private java.lang.String email;
    /**
     * 客户信息			db_column: CIF_ID 
     */	
	private java.lang.String cifId;
    /**
     * 用户图片			db_column: USER_PICTURE 
     */	
	private java.lang.String userPicture;
    /**
     * 用户状态(初始化:CSH、YRZ:已认证、WRZ:未认证、SD:锁定、HMD:黑名单)			db_column: STATUS 
     */	
	private java.lang.String status;
    /**
     * 账户锁定失效时间			db_column: LOCK_EXPIRE_TIME 
     */	
	private Date lockExpireTime;
    /**
     * 创建渠道(00:系统注册)			db_column: CREATE_CHANNEL 
     */	
	private java.lang.String createChannel;
    /**
     * 登录TOKEN			db_column: LOGIN_TOKEN 
     */	
	private java.lang.String loginToken;
    /**
     * 登录TOKEN失效时间			db_column: LOGIN_TOKEN_EXPIRAT_TIME 
     */	
	private Date loginTokenExpiratTime;
    /**
     * 最后登录时间			db_column: LAST_LOGIN_TIME 
     */	
	private Date lastLoginTime;
    /**
     * 最后登录IP			db_column: LAST_LOGIN_IP 
     */	
	private java.lang.String lastLoginIp;
    /**
     * 登录设备编号			db_column: LOGIN_DEVICE_CODE 
     */	
	private java.lang.String loginDeviceCode;
    /**
     * 最后登录设备类型			db_column: LAST_LOGIN_DEVICE 
     */	
	private java.lang.String lastLoginDevice;
    /**
     * 最后登录时语言环境			db_column: LAST_LOGIN_LOCALE 
     */	
	private java.lang.String lastLoginLocale;
    /**
     * 登录失败次数			db_column: FAIL_LOGIN_COUNTER 
     */	
	private java.lang.Integer failLoginCounter;
    /**
     * 创建时间			db_column: CREATE_TIME 
     */	
	private Date createTime;
    /**
     * 修改时间			db_column: MODIFY_TIME 
     */	
	private Date modifyTime;
	//columns END

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setUsername(java.lang.String username) {
		this.username = username;
	}
	public java.lang.String getUsername() {
		return this.username;
	}
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	public java.lang.String getPassword() {
		return this.password;
	}
	public void setPaymentPassword(java.lang.String paymentPassword) {
		this.paymentPassword = paymentPassword;
	}
	public java.lang.String getPaymentPassword() {
		return this.paymentPassword;
	}
	public void setPaymentPasswordFailCounter(java.lang.Integer paymentPasswordFailCounter) {
		this.paymentPasswordFailCounter = paymentPasswordFailCounter;
	}
	public java.lang.Integer getPaymentPasswordFailCounter() {
		return this.paymentPasswordFailCounter;
	}
	public void setNick(java.lang.String nick) {
		this.nick = nick;
	}
	public java.lang.String getNick() {
		return this.nick;
	}
	public void setMobilePrefix(java.lang.String mobilePrefix) {
		this.mobilePrefix = mobilePrefix;
	}
	public java.lang.String getMobilePrefix() {
		return this.mobilePrefix;
	}
	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}
	public java.lang.String getMobile() {
		return this.mobile;
	}
	public void setEmail(java.lang.String email) {
		this.email = email;
	}
	public java.lang.String getEmail() {
		return this.email;
	}
	public void setCifId(java.lang.String cifId) {
		this.cifId = cifId;
	}
	public java.lang.String getCifId() {
		return this.cifId;
	}
	public void setUserPicture(java.lang.String userPicture) {
		this.userPicture = userPicture;
	}
	public java.lang.String getUserPicture() {
		return this.userPicture;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	public java.lang.String getStatus() {
		return this.status;
	}
	public void setLockExpireTime(Date lockExpireTime) {
		this.lockExpireTime = lockExpireTime;
	}
	public Date getLockExpireTime() {
		return this.lockExpireTime;
	}
	public void setCreateChannel(java.lang.String createChannel) {
		this.createChannel = createChannel;
	}
	public java.lang.String getCreateChannel() {
		return this.createChannel;
	}
	public void setLoginToken(java.lang.String loginToken) {
		this.loginToken = loginToken;
	}
	public java.lang.String getLoginToken() {
		return this.loginToken;
	}
	public void setLoginTokenExpiratTime(Date loginTokenExpiratTime) {
		this.loginTokenExpiratTime = loginTokenExpiratTime;
	}
	public Date getLoginTokenExpiratTime() {
		return this.loginTokenExpiratTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}
	public void setLastLoginIp(java.lang.String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public java.lang.String getLastLoginIp() {
		return this.lastLoginIp;
	}
	public void setLoginDeviceCode(java.lang.String loginDeviceCode) {
		this.loginDeviceCode = loginDeviceCode;
	}
	public java.lang.String getLoginDeviceCode() {
		return this.loginDeviceCode;
	}
	public void setLastLoginDevice(java.lang.String lastLoginDevice) {
		this.lastLoginDevice = lastLoginDevice;
	}
	public java.lang.String getLastLoginDevice() {
		return this.lastLoginDevice;
	}
	public void setLastLoginLocale(java.lang.String lastLoginLocale) {
		this.lastLoginLocale = lastLoginLocale;
	}
	public java.lang.String getLastLoginLocale() {
		return this.lastLoginLocale;
	}
	public void setFailLoginCounter(java.lang.Integer failLoginCounter) {
		this.failLoginCounter = failLoginCounter;
	}
	public java.lang.Integer getFailLoginCounter() {
		return this.failLoginCounter;
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
		return new StringBuilder("User [")
			.append("UserId=").append(getUserId()).append(", ")
					.append("Username=").append(getUsername()).append(", ")
					.append("Password=").append(getPassword()).append(", ")
					.append("PaymentPassword=").append(getPaymentPassword()).append(", ")
					.append("PaymentPasswordFailCounter=").append(getPaymentPasswordFailCounter()).append(", ")
					.append("Nick=").append(getNick()).append(", ")
					.append("MobilePrefix=").append(getMobilePrefix()).append(", ")
					.append("Mobile=").append(getMobile()).append(", ")
					.append("Email=").append(getEmail()).append(", ")
					.append("CifId=").append(getCifId()).append(", ")
					.append("UserPicture=").append(getUserPicture()).append(", ")
					.append("Status=").append(getStatus()).append(", ")
					.append("LockExpireTime=").append(getLockExpireTime()).append(", ")
					.append("CreateChannel=").append(getCreateChannel()).append(", ")
					.append("LoginToken=").append(getLoginToken()).append(", ")
					.append("LoginTokenExpiratTime=").append(getLoginTokenExpiratTime()).append(", ")
					.append("LastLoginTime=").append(getLastLoginTime()).append(", ")
					.append("LastLoginIp=").append(getLastLoginIp()).append(", ")
					.append("LoginDeviceCode=").append(getLoginDeviceCode()).append(", ")
					.append("LastLoginDevice=").append(getLastLoginDevice()).append(", ")
					.append("LastLoginLocale=").append(getLastLoginLocale()).append(", ")
					.append("FailLoginCounter=").append(getFailLoginCounter()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

