package com.sharingif.blockchain.api.crypto.entity;

/**
 * 生成addressIndex响应
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/4 下午2:29
 */
public class BIP44AddressIndexRsp {

    /**
     * 地址
     */
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BIP44AddressIndexRsp{");
        sb.append("address='").append(address).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
