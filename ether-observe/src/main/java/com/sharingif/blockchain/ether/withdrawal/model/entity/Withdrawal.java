package com.sharingif.blockchain.ether.withdrawal.model.entity;


import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherReq;
import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;
import com.sharingif.cube.components.sequence.Sequence;

import java.math.BigInteger;
import java.util.Date;

public class Withdrawal implements java.io.Serializable, IObjectDateOperationHistory {

	/**
	 * 处理状态(TXWCL:提现未处理)
	 */
	public static final String STATUS_UNTREATED = "TXWCL";
	/**
	 * 处理状态(TXCLZ:提现处理中)
	 */
	public static final String STATUS_PROCESSING = "TXCLZ";
	/**
	 * 处理状态(CSHTZZ:初始化通知中)
	 */
	public static final String STATUS_INIT_NOTICE = "CSHTZZ";
	/**
	 * 处理状态(CSHTZCG:初始化通知成功)
	 */
	public static final String STATUS_INIT_NOTICED = "CSHTZCG";
	/**
	 * 处理状态(TXCG:提现成功)
	 */
	public static final String STATUS_SUCCESS = "TXCG";
	/**
	 * 处理状态(TXSB:提现失败)
	 */
	public static final String STATUS_FAILURE = "TXSB";
	/**
	 * 处理状态(TXTZZ:提现通知中)
	 */
	public static final String STATUS_NOTICING = "TXTZZ";
	/**
	 * 处理状态(TXCGTZ:提现成功通知)
	 */
	public static final String STATUS_SUCCESS_NOTICED = "TXCGTZ";
	/**
	 * 处理状态(TXSBTZ:提现失败通知)
	 */
	public static final String STATUS_FAILURE_NOTICED = "TXSBTZ";

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
     * 合约地址			db_column: CONTRACT_ADDRESS 
     */	
	private String contractAddress;
    /**
     * 金额			db_column: AMOUNT 
     */	
	private BigInteger amount;
    /**
     * gas limit			db_column: GAS_LIMIT 
     */	
	private BigInteger gasLimit;
    /**
     * gas used			db_column: GAS_USED 
     */	
	private BigInteger gasUsed;
    /**
     * gas price			db_column: GAS_PRICE 
     */	
	private BigInteger gasPrice;
    /**
     * 手续费			db_column: FEE 
     */	
	private BigInteger fee;
    /**
     * 处理状态(TXWCL:提现未处理、TXCLZ:提现处理中、CSHTZZ:初始化通知中、CSHTZCG:初始化通知成功、TXCG:提现成功、TXSB:提现失败、TXTZZ:提现通知中、TXCGTZ:提现成功通知、TXSBTZ:提现失败通知)			db_column: STATUS
     */	
	private String status;
    /**
     * 交易时间			db_column: TX_TIME 
     */	
	private Date txTime;
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
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	public String getContractAddress() {
		return this.contractAddress;
	}
	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}
	public BigInteger getAmount() {
		return this.amount;
	}
	public void setGasLimit(BigInteger gasLimit) {
		this.gasLimit = gasLimit;
	}
	public BigInteger getGasLimit() {
		return this.gasLimit;
	}
	public void setGasUsed(BigInteger gasUsed) {
		this.gasUsed = gasUsed;
	}
	public BigInteger getGasUsed() {
		return this.gasUsed;
	}
	public void setGasPrice(BigInteger gasPrice) {
		this.gasPrice = gasPrice;
	}
	public BigInteger getGasPrice() {
		return this.gasPrice;
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
	public void setTxTime(Date txTime) {
		this.txTime = txTime;
	}
	public Date getTxTime() {
		return this.txTime;
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
		withdrawal.setContractAddress(req.getContractAddress());
		withdrawal.setAmount(req.getAmount());
		withdrawal.setGasLimit(req.getGasLimit());
		withdrawal.setGasPrice(req.getGasPrice());
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
					.append("ContractAddress=").append(getContractAddress()).append(", ")
					.append("Amount=").append(getAmount()).append(", ")
					.append("GasLimit=").append(getGasLimit()).append(", ")
					.append("GasUsed=").append(getGasUsed()).append(", ")
					.append("GasPrice=").append(getGasPrice()).append(", ")
					.append("Fee=").append(getFee()).append(", ")
					.append("Status=").append(getStatus()).append(", ")
					.append("TxTime=").append(getTxTime()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

