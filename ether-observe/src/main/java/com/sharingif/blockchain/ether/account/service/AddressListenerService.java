package com.sharingif.blockchain.ether.account.service;


import com.sharingif.blockchain.ether.account.model.entity.AddressListener;
import com.sharingif.blockchain.ether.api.account.entity.AddressListenerAddReq;
import com.sharingif.cube.support.service.base.IBaseService;


public interface AddressListenerService extends IBaseService<AddressListener, java.lang.String> {

    /**
     * 添加地址监听
     * @param req
     */
    void add(AddressListenerAddReq req);

    /**
     * 判断地址是否是监听地址
     * @param address
     * @return
     */
    boolean isWatch(String address);

}
