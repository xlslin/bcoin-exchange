package com.sharingif.blockchain.bitcoin.api.account.entity;

/**
 * 添加地址监听请求
 */
public class AddressListenerAddReq {

    /**
     * 区块地址
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
        final StringBuilder sb = new StringBuilder("AddressListenerAddReq{");
        sb.append("address='").append(address).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
