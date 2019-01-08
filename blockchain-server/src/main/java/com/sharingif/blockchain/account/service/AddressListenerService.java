package com.sharingif.blockchain.account.service;


public interface AddressListenerService {

    /**
     * 添加地址监听
     * @param req
     */
    void add(String blockType, String address);

}
