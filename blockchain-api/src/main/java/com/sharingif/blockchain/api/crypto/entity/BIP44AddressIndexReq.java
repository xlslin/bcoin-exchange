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
    private String coinType;
    /**
     * 是否接受通知
     */
    private boolean watch;

    public String getChangeExtendedKeyId() {
        return changeExtendedKeyId;
    }

    public void setChangeExtendedKeyId(String changeExtendedKeyId) {
        this.changeExtendedKeyId = changeExtendedKeyId;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public boolean isWatch() {
        return watch;
    }

    public void setWatch(boolean watch) {
        this.watch = watch;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BIP44AddressIndexReq{");
        sb.append("changeExtendedKeyId='").append(changeExtendedKeyId).append('\'');
        sb.append(", coinType='").append(coinType).append('\'');
        sb.append(", watch=").append(watch);
        sb.append('}');
        return sb.toString();
    }
}
