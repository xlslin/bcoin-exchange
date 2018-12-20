package com.sharingif.blockchain.ether.block.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;
import com.sharingif.cube.core.util.StringUtils;

import java.math.BigInteger;
import java.util.Date;

public class Contract implements java.io.Serializable, IObjectDateOperationHistory {
	
	//columns START
    /**
     * 合约地址			db_column: CONTRACT_ADDRESS 
     */	
	private java.lang.String contractAddress;
    /**
     * 名称			db_column: NAME 
     */	
	private java.lang.String name;
    /**
     * 符号			db_column: SYMBOL 
     */	
	private java.lang.String symbol;
    /**
     * 小数位			db_column: DECIMALS 
     */	
	private BigInteger decimals;
    /**
     * 总发行量			db_column: TOTALSUPPLY 
     */	
	private BigInteger totalsupply;
    /**
     * 创建时间			db_column: CREATE_TIME 
     */	
	private Date createTime;
    /**
     * 修改时间			db_column: MODIFY_TIME 
     */	
	private Date modifyTime;
	//columns END

	public void setContractAddress(java.lang.String contractAddress) {
		this.contractAddress = contractAddress;
	}
	public java.lang.String getContractAddress() {
		return this.contractAddress;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	public java.lang.String getName() {
		return this.name;
	}
	public void setSymbol(java.lang.String symbol) {
		this.symbol = symbol;
	}
	public java.lang.String getSymbol() {
		return this.symbol;
	}
	public void setDecimals(BigInteger decimals) {
		this.decimals = decimals;
	}
	public BigInteger getDecimals() {
		return this.decimals;
	}
	public void setTotalsupply(BigInteger totalsupply) {
		this.totalsupply = totalsupply;
	}
	public BigInteger getTotalsupply() {
		return this.totalsupply;
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

	public boolean isErc20Contract() {
		if(StringUtils.isTrimEmpty(name)) {
			return false;
		}

		if(StringUtils.isTrimEmpty(symbol)) {
			return false;
		}

		if(decimals == null) {
			return false;
		}

		return true;
	}

	public String toString() {
		return new StringBuilder("Contract [")
			.append("ContractAddress=").append(getContractAddress()).append(", ")
					.append("Name=").append(getName()).append(", ")
					.append("Symbol=").append(getSymbol()).append(", ")
					.append("Decimals=").append(getDecimals()).append(", ")
					.append("Totalsupply=").append(getTotalsupply()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

