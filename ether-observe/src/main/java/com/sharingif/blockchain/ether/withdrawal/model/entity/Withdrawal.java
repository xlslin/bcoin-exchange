package com.sharingif.blockchain.ether.withdrawal.model.entity;


import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherReq;
import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;
import com.sharingif.cube.components.sequence.Sequence;

import java.math.BigInteger;
import java.util.Date;

public class Withdrawal implements java.io.Serializable, IObjectDateOperationHistory {

	/**
	 * 处理状态(TXCLZ:提现处理中)
	 */
	public static final String STATUS_PROCESSING = "TXCLZ";

	//columns START
    /**
     * id			db_column: ID 
     */
	@Sequence(ref="uuidSequenceGenerator")
	private String id;
    /**
     * 取现唯一编号			db_column: WITHDRAWAL_ID 
     */	
	private String withdrawalId;
    /**
     * 交易hash			db_column: TX_HASH 
     */	
	private String txHash;
    /**
     * 币种			db_column: COIN_TYPE 
     */	
	private String coinType;
    /**
     * FORM地址			db_column: TX_FROM 
     */	
	private String txFrom;
    /**
     * TO地址			db_column: TX_TO 
     */	
	private String txTo;
    /**
     * 金额			db_column: AMOUNT 
     */	
	private BigInteger amount;
    /**
     * 手续费			db_column: FEE 
     */	
	private BigInteger fee;
    /**
     * 处理状态(TXWCL:提现未处理、TXCLZ:提现处理中、TXCG:提现成功、TXSB:提现失败)			db_column: STATUS 
     */	
	private String status;
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
	public void setWithdrawalId(String withdrawalId) {
		this.withdrawalId = withdrawalId;
	}
	public String getWithdrawalId() {
		return this.withdrawalId;
	}
	public void setTxHash(String txHash) {
		this.txHash = txHash;
	}
	public String getTxHash() {
		return this.txHash;
	}
	public void setCoinType(String coinType) {
		this.coinType = coinType;
	}
	public String getCoinType() {
		return this.coinType;
	}
	public void setTxFrom(String txFrom) {
		this.txFrom = txFrom;
	}
	public String getTxFrom() {
		return this.txFrom;
	}
	public void setTxTo(String txTo) {
		this.txTo = txTo;
	}
	public String getTxTo() {
		return this.txTo;
	}
	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}
	public BigInteger getAmount() {
		return this.amount;
	}
	public void setFee(BigInteger fee) {
		this.fee = fee;
	}
	public BigInteger getFee() {
		return this.fee;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return this.status;
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

	public static Withdrawal convertWithdrawalEtherReqToWithdrawal(WithdrawalEtherReq req) {
		Withdrawal withdrawal = new Withdrawal();
		withdrawal.setWithdrawalId(req.getWithdrawalId());
		withdrawal.setCoinType(req.getCoinType());
		withdrawal.setTxTo(req.getAddress());
		withdrawal.setAmount(req.getAmount());
		return withdrawal;
	}

	public String toString() {
		return new StringBuilder("Withdrawal [")
			.append("Id=").append(getId()).append(", ")
					.append("WithdrawalId=").append(getWithdrawalId()).append(", ")
					.append("TxHash=").append(getTxHash()).append(", ")
					.append("CoinType=").append(getCoinType()).append(", ")
					.append("TxFrom=").append(getTxFrom()).append(", ")
					.append("TxTo=").append(getTxTo()).append(", ")
					.append("Amount=").append(getAmount()).append(", ")
					.append("Fee=").append(getFee()).append(", ")
					.append("Status=").append(getStatus()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

