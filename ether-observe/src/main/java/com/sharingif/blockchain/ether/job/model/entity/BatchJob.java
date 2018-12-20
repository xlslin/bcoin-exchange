package com.sharingif.blockchain.ether.job.model.entity;


import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;
import com.sharingif.cube.components.sequence.Sequence;

import java.util.Date;

public class BatchJob implements java.io.Serializable, IObjectDateOperationHistory {

	/**
	 * 待处理
	 */
	public static final String STATUS_PENDING="0";
	/**
	 * 队列中
	 */
	public static final String STATUS_IN_QUEUE="1";
	/**
	 * 处理中
	 */
	public static final String STATUS_HANDLING="2";
	/**
	 * 处理完成
	 */
	public static final String STATUS_SOLVED="3";
	/**
	 * 处理失败
	 */
	public static final String STATUS_FAILED="4";

	//columns START
    /**
     * 主键ID			db_column: ID 
     */
	@Sequence(ref="uuidSequenceGenerator")
	private java.lang.String id;
    /**
     * 父级JOB ID			db_column: PARENT_JOB_ID 
     */	
	private java.lang.String parentJobId;
    /**
     * 交易名			db_column: LOOKUP_PATH 
     */	
	private java.lang.String lookupPath;
    /**
     * 计划执行时间			db_column: PLAN_EXECUTE_TIME 
     */	
	private Date planExecuteTime;
    /**
     * 实际执行时间			db_column: ACTUAL_EXECUTE_TIME 
     */	
	private Date actualExecuteTime;
    /**
     * 执行次数			db_column: EXECUTE_COUNT 
     */	
	private java.lang.Integer executeCount;
    /**
     * 数据ID			db_column: DATA_ID 
     */	
	private java.lang.String dataId;
    /**
     * 状态(0:待处理、1:队列中、2:处理中、3:处理完成、4:处理失败)			db_column: STATUS 
     */	
	private java.lang.String status;
    /**
     * 错误码			db_column: ERROR_MESSAGE_CODE 
     */	
	private java.lang.String errorMessageCode;
    /**
     * 本地错误消息			db_column: ERROR_LOCALIZED_MESSAGE 
     */	
	private java.lang.String errorLocalizedMessage;
    /**
     * 错误原因			db_column: ERROR_CAUSE 
     */	
	private java.lang.String errorCause;
    /**
     * 创建时间			db_column: CREATE_TIME 
     */	
	private Date createTime;
    /**
     * 修改时间			db_column: MODIFY_TIME 
     */	
	private Date modifyTime;
	//columns END

	public JobRequest convertJobRequest() {
		JobRequest jobRequest = new JobRequest();
		jobRequest.setParentJobId(getParentJobId());
		jobRequest.setId(getId());
		jobRequest.setLookupPath(getLookupPath());
		jobRequest.setPlanExecuteTime(getPlanExecuteTime());
		jobRequest.setExecuteCount(getExecuteCount());
		jobRequest.setCreatetime(getCreateTime());

		return jobRequest;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getId() {
		return this.id;
	}
	public void setParentJobId(java.lang.String parentJobId) {
		this.parentJobId = parentJobId;
	}
	public java.lang.String getParentJobId() {
		return this.parentJobId;
	}
	public void setLookupPath(java.lang.String lookupPath) {
		this.lookupPath = lookupPath;
	}
	public java.lang.String getLookupPath() {
		return this.lookupPath;
	}
	public void setPlanExecuteTime(Date planExecuteTime) {
		this.planExecuteTime = planExecuteTime;
	}
	public Date getPlanExecuteTime() {
		return this.planExecuteTime;
	}
	public void setActualExecuteTime(Date actualExecuteTime) {
		this.actualExecuteTime = actualExecuteTime;
	}
	public Date getActualExecuteTime() {
		return this.actualExecuteTime;
	}
	public void setExecuteCount(java.lang.Integer executeCount) {
		this.executeCount = executeCount;
	}
	public java.lang.Integer getExecuteCount() {
		return this.executeCount;
	}
	public void setDataId(java.lang.String dataId) {
		this.dataId = dataId;
	}
	public java.lang.String getDataId() {
		return this.dataId;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	public java.lang.String getStatus() {
		return this.status;
	}
	public void setErrorMessageCode(java.lang.String errorMessageCode) {
		this.errorMessageCode = errorMessageCode;
	}
	public java.lang.String getErrorMessageCode() {
		return this.errorMessageCode;
	}
	public void setErrorLocalizedMessage(java.lang.String errorLocalizedMessage) {
		this.errorLocalizedMessage = errorLocalizedMessage;
	}
	public java.lang.String getErrorLocalizedMessage() {
		return this.errorLocalizedMessage;
	}
	public void setErrorCause(java.lang.String errorCause) {
		this.errorCause = errorCause;
	}
	public java.lang.String getErrorCause() {
		return this.errorCause;
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
		return new StringBuilder("BatchJob [")
			.append("Id=").append(getId()).append(", ")
					.append("ParentJobId=").append(getParentJobId()).append(", ")
					.append("LookupPath=").append(getLookupPath()).append(", ")
					.append("PlanExecuteTime=").append(getPlanExecuteTime()).append(", ")
					.append("ActualExecuteTime=").append(getActualExecuteTime()).append(", ")
					.append("ExecuteCount=").append(getExecuteCount()).append(", ")
					.append("DataId=").append(getDataId()).append(", ")
					.append("Status=").append(getStatus()).append(", ")
					.append("ErrorMessageCode=").append(getErrorMessageCode()).append(", ")
					.append("ErrorLocalizedMessage=").append(getErrorLocalizedMessage()).append(", ")
					.append("ErrorCause=").append(getErrorCause()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

