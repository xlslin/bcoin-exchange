package com.sharingif.blockchain.ether.job.entity;


import com.sharingif.cube.batch.core.request.JobRequest;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;


public class BatchJob implements java.io.Serializable {

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

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * 主键id			db_column: ID 
     */	
	@Length(max=40)
	private String id;
    /**
     * 父级job id			db_column: PARENT_JOB_ID
     */
	@Length(max=40)
	private String parentJobId;
    /**
     * 交易名			db_column: LOOKUP_PATH
     */
	@NotBlank @Length(max=200)
	private String lookupPath;
    /**
     * 计划执行时间			db_column: PLAN_EXECUTE_TIME
     */
	@NotNull
	private java.util.Date planExecuteTime;
    /**
     * 实际执行时间			db_column: ACTUAL_EXECUTE_TIME
     */
	@NotNull
	private java.util.Date actualExecuteTime;
    /**
     * 执行次数			db_column: EXECUTE_COUNT
     */
	@NotNull
	private Integer executeCount;
    /**
     * 数据id			db_column: DATA_ID
     */
	@Length(max=100)
	private String dataId;
    /**
     * 创建时间			db_column: CREATE_TIME
     */
	@NotNull
	private java.util.Date createTime;
    /**
     * 失败次数			db_column: STATUS
     */
	@NotBlank @Length(max=1)
	private String status;
    /**
     * 错误码			db_column: ERROR_MESSAGE_CODE
     */
	@Length(max=200)
	private String errorMessageCode;
    /**
     * 本地错误消息			db_column: ERROR_LOCALIZED_MESSAGE
     */
	@Length(max=500)
	private String errorLocalizedMessage;
    /**
     * 错误原因			db_column: ERROR_CAUSE
     */
	@Length(max=6000)
	private String errorCause;
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
	public void setPlanExecuteTime(java.util.Date planExecuteTime) {
		this.planExecuteTime = planExecuteTime;
	}
	public java.util.Date getPlanExecuteTime() {
		return this.planExecuteTime;
	}
	public void setActualExecuteTime(java.util.Date actualExecuteTime) {
		this.actualExecuteTime = actualExecuteTime;
	}
	public java.util.Date getActualExecuteTime() {
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
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
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

	public String toString() {
		return new StringBuilder("BatchJob [")
			.append("Id=").append(getId()).append(", ")
					.append("ParentJobId=").append(getParentJobId()).append(", ")
					.append("LookupPath=").append(getLookupPath()).append(", ")
					.append("PlanExecuteTime=").append(getPlanExecuteTime()).append(", ")
					.append("ActualExecuteTime=").append(getActualExecuteTime()).append(", ")
					.append("ExecuteCount=").append(getExecuteCount()).append(", ")
					.append("DataId=").append(getDataId()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("Status=").append(getStatus()).append(", ")
					.append("ErrorMessageCode=").append(getErrorMessageCode()).append(", ")
					.append("ErrorLocalizedMessage=").append(getErrorLocalizedMessage()).append(", ")
					.append("ErrorCause=").append(getErrorCause())
		.append("]").toString();
	}
	
}

