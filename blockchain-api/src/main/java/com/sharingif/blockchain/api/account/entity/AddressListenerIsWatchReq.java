package com.sharingif.blockchain.api.account.entity;

/**
 * 是否是观察地址请求
 */
public class AddressListenerIsWatchReq {

    /**
     * 区块类型
     */
    private String blockType;
    /**
     * 区块地址
     */
    private String address;

    public String getBlockType() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddressListenerIsWatchReq{");
        sb.append("blockType='").append(blockType).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
