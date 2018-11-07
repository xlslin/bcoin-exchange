package com.sharingif.blockchain.account.service;


import com.sharingif.blockchain.account.model.entity.AddressListener;
import com.sharingif.cube.support.service.base.IBaseService;


public interface AddressListenerService extends IBaseService<AddressListener, java.lang.String> {

    /**
     * 添加监听地址
     * @param address
     */
    void add(String address);

}
