package com.sharingif.blockchain.ether.api.account.entity;

public class AddressListenerIsWatchRsp {

    /**
     * 是否是观察地址
     */
    private boolean isWatch;

    public boolean isWatch() {
        return isWatch;
    }

    public void setWatch(boolean watch) {
        isWatch = watch;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddressListenerIsWatchRsp{");
        sb.append("isWatch=").append(isWatch);
        sb.append('}');
        return sb.toString();
    }
}
