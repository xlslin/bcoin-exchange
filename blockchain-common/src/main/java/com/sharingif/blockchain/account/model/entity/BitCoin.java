package com.sharingif.blockchain.account.model.entity;


import com.sharingif.cube.components.monitor.IObjectDateOperationHistory;
import com.sharingif.cube.components.sequence.Sequence;

import java.util.Date;

public class BitCoin implements java.io.Serializable, IObjectDateOperationHistory {
	
	//columns START
    /**
     * id			db_column: ID 
     */
	@Sequence(ref="uuidSequenceGenerator")
	private java.lang.String id;
    /**
     * 区块类型(Ether、Bitcoin)			db_column: BLOCK_TYPE
     */	
	private java.lang.String blockType;
    /**
     * 币种名称			db_column: COIN_TYPE 
     */	
	private java.lang.String coinType;
    /**
     * bip44币种			db_column: BIP44_COIN_TYPE 
     */	
	private java.lang.String bip44CoinType;
    /**
     * 小数位			db_column: DECIMALS 
     */	
	private java.lang.Integer decimals;
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
	public void setBlockType(java.lang.String blockType) {
		this.blockType = blockType;
	}
	public java.lang.String getBlockType() {
		return this.blockType;
	}
	public void setCoinType(java.lang.String coinType) {
		this.coinType = coinType;
	}
	public java.lang.String getCoinType() {
		return this.coinType;
	}
	public void setBip44CoinType(java.lang.String bip44CoinType) {
		this.bip44CoinType = bip44CoinType;
	}
	public java.lang.String getBip44CoinType() {
		return this.bip44CoinType;
	}
	public void setDecimals(java.lang.Integer decimals) {
		this.decimals = decimals;
	}
	public java.lang.Integer getDecimals() {
		return this.decimals;
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
		return new StringBuilder("BitCoin [")
			.append("Id=").append(getId()).append(", ")
					.append("BlockType=").append(getBlockType()).append(", ")
					.append("CoinType=").append(getCoinType()).append(", ")
					.append("Bip44CoinType=").append(getBip44CoinType()).append(", ")
					.append("Decimals=").append(getDecimals()).append(", ")
					.append("CreateTime=").append(getCreateTime()).append(", ")
					.append("ModifyTime=").append(getModifyTime())
		.append("]").toString();
	}
	
}

