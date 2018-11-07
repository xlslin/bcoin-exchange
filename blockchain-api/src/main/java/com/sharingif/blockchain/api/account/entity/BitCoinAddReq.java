package com.sharingif.blockchain.api.account.entity;


/**
 * 币种添加请求
 */
public class BitCoinAddReq {

    /**
     * 区块类型
     */
    private String blockType;
    /**
     * 币种名称
     */
    private String coinType;
    /**
     * bip44币种
     */
    private String bip44CoinType;
    /**
     * 小数位
     */
    private Integer decimals;

    public String getBlockType() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public String getBip44CoinType() {
        return bip44CoinType;
    }

    public void setBip44CoinType(String bip44CoinType) {
        this.bip44CoinType = bip44CoinType;
    }

    public Integer getDecimals() {
        return decimals;
    }

    public void setDecimals(Integer decimals) {
        this.decimals = decimals;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BitCoinAddReq{");
        sb.append("blockType='").append(blockType).append('\'');
        sb.append(", coinType='").append(coinType).append('\'');
        sb.append(", bip44CoinType='").append(bip44CoinType).append('\'');
        sb.append(", decimals=").append(decimals);
        sb.append('}');
        return sb.toString();
    }
}
