package com.sharingif.blockchain.api.crypto.entity;

/**
 * 生成addressIndex请求
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/4 下午2:28
 */
public class BIP44AddressIndexReq {

    /**
     * change ExtendedKey Id
     */
    private String changeExtendedKeyId;
    /**
     * 币种
     */
    private int coinType;

    public String getChangeExtendedKeyId() {
        return changeExtendedKeyId;
    }

    public void setChangeExtendedKeyId(String changeExtendedKeyId) {
        this.changeExtendedKeyId = changeExtendedKeyId;
    }

    public int getCoinType() {
        return coinType;
    }

    public void setCoinType(int coinType) {
        this.coinType = coinType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BIP44AddressIndexReq{");
        sb.append("changeExtendedKeyId='").append(changeExtendedKeyId).append('\'');
        sb.append(", coinType=").append(coinType);
        sb.append('}');
        return sb.toString();
    }
}
