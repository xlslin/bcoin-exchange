package com.sharingif.blockchain.ether.api.account.entity;

/**
 * 是否是观察地址请求
 */
public class AddressListenerIsWatchReq {

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
        final StringBuilder sb = new StringBuilder("AddressListenerIsWatchReq{");
        sb.append("address='").append(address).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
