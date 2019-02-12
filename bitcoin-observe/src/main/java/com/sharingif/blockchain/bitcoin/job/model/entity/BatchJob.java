package com.sharingif.blockchain.bitcoin.job.model.entity;


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
	private String id;
    /**
     * 父级JOB ID			db_column: PARENT_JOB_ID 
     */	
	private String parentJobId;
    /**
     * 交易名			db_column: LOOKUP_PATH 
     */	
	private String lookupPath;
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
	private Integer executeCount;
    /**
     * 数据ID			db_column: DATA_ID 
     */	
	private String dataId;
    /**
     * 状态(0:待处理、1:队列中、2:处理中、3:处理完成、4:处理失败)			db_column: STATUS 
     */	
	private String status;
    /**
     * 错误码			db_column: ERROR_MESSAGE_CODE 
     */	
	private String errorMessageCode;
    /**
     * 本地错误消息			db_column: ERROR_LOCALIZED_MESSAGE 
     */	
	private String errorLocalizedMessage;
    /**
     * 错误原因			db_column: ERROR_CAUSE 
     */	
	private String errorCause;
    /**
     * 创建时间			db_column: CREATE_TIME 
     */	
	private Date createTime;
    /**
     * 修改时间			db_column: MODIFY_TIME 
     */	
	private Date modifyTime;
	//columns END

	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	public void setParentJobId(String parentJobId) {
		this.parentJobId = parentJobId;
	}
	public String getParentJobId() {
		return this.parentJobId;
	}
	public void setLookupPath(String lookupPath) {
		this.lookupPath = lookupPath;
	}
	public String getLookupPath() {
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
	public void setExecuteCount(Integer executeCount) {
		this.executeCount = executeCount;
	}
	public Integer getExecuteCount() {
		return this.executeCount;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public String getDataId() {
		return this.dataId;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return this.status;
	}
	public void setErrorMessageCode(String errorMessageCode) {
		this.errorMessageCode = errorMessageCode;
	}
	public String getErrorMessageCode() {
		return this.errorMessageCode;
	}
	public void setErrorLocalizedMessage(String errorLocalizedMessage) {
		this.errorLocalizedMessage = errorLocalizedMessage;
	}
	public String getErrorLocalizedMessage() {
		return this.errorLocalizedMessage;
	}
	public void setErrorCause(String errorCause) {
		this.errorCause = errorCause;
	}
	public String getErrorCause() {
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

	public JobRequest convertJobRequest() {
		JobRequest<String> jobRequest = new JobRequest<String>();
		jobRequest.setParentJobId(getParentJobId());
		jobRequest.setId(getId());
		jobRequest.setLookupPath(getLookupPath());
		jobRequest.setPlanExecuteTime(getPlanExecuteTime());
		jobRequest.setExecuteCount(getExecuteCount());
		jobRequest.setData(getDataId());
		jobRequest.setCreatetime(getCreateTime());

		return jobRequest;
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

