package com.sharingif.wallet.user.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;
import com.sharingif.cube.components.sequence.Sequence;

import java.util.Date;

public class Cif implements java.io.Serializable, IObjectDateOperationHistory {
	
	//columns START
    /**
     * ID			db_column: ID 
     */
	@Sequence(ref="uuidSequenceGenerator")
	private java.lang.String id;
    /**
     * 姓名			db_column: CIF_NAME 
     */	
	private java.lang.String cifName;
    /**
     * 性别(M:男、F:女)			db_column: SEX 
     */	
	private java.lang.String sex;
    /**
     * 出生日期			db_column: BIRTHDAY 
     */	
	private Date birthday;
    /**
     * 证件类型(00:身份证)			db_column: ID_TYPE 
     */	
	private java.lang.String idType;
    /**
     * 证件号码			db_column: ID_NUM 
     */	
	private java.lang.String idNum;
    /**
     * 半身照片			db_column: HALF_PHOTO 
     */	
	private java.lang.String halfPhoto;
    /**
     * 身份正面照			db_column: IDENTITY_PHOTOS_FORNT 
     */	
	private java.lang.String identityPhotosFornt;
    /**
     * 身份正反面			db_column: IDENTITY_PHOTOS_REAR 
     */	
	private java.lang.String identityPhotosRear;
    /**
     * 手持身份证			db_column: IDENTITY_PHOTOS_HOLD 
     */	
	private java.lang.String identityPhotosHold;
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
	public void setCifName(java.lang.String cifName) {
		this.cifName = cifName;
	}
	public java.lang.String getCifName() {
		return this.cifName;
	}
	public void setSex(java.lang.String sex) {
		this.sex = sex;
	}
	public java.lang.String getSex() {
		return this.sex;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Date getBirthday() {
		return this.birthday;
	}
	public void setIdType(java.lang.String idType) {
		this.idType = idType;
	}
	public java.lang.String getIdType() {
		return this.idType;
	}
	public void setIdNum(java.lang.String idNum) {
		this.idNum = idNum;
	}
	public java.lang.String getIdNum() {
		return this.idNum;
	}
	public void setHalfPhoto(java.lang.String halfPhoto) {
		this.halfPhoto = halfPhoto;
	}
	public java.lang.String getHalfPhoto() {
		return this.halfPhoto;
	}
	public void setIdentityPhotosFornt(java.lang.String identityPhotosFornt) {
		this.identityPhotosFornt = identityPhotosFornt;
	}
	public java.lang.String getIdentityPhotosFornt() {
		return this.identityPhotosFornt;
	}
	public void setIdentityPhotosRear(java.lang.String identityPhotosRear) {
		this.identityPhotosRear = identityPhotosRear;
	}
	public java.lang.String getIdentityPhotosRear() {
		return this.identityPhotosRear;
	}
	public void setIdentityPhotosHold(java.lang.String identityPhotosHold) {
		this.identityPhotosHold = identityPhotosHold;
	}
	public java.lang.String getIdentityPhotosHold() {
		return this.identityPhotosHold;
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
		return new StringBuilder("Cif [")
			.append("Id=").append(getId()).append(", ")
					.append("CifName=").append(getCifName()).append(", ")
					.append("Sex=").append(getSex()).append(", ")
					.append("Birthday=").append(getBirthday()).append(", ")
					.append("IdType=").append(getIdType()).append(", ")
					.append("IdNum=").append(getIdNum()).append(", ")
					.append("HalfPhoto=").append(getHalfPhoto()).append(", ")
					.append("IdentityPhotosFornt=").append(getIdentityPhotosFornt()).append(", ")
					.append("IdentityPhotosRear=").append(getIdentityPhotosRear()).append(", ")
					.append("IdentityPhotosHold=").append(getIdentityPhotosHold()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

