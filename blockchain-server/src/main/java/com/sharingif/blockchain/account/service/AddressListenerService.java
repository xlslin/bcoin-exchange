package com.sharingif.blockchain.account.service;


import com.sharingif.blockchain.account.model.entity.AddressListener;
import com.sharingif.blockchain.api.account.entity.AddressListenerAddReq;
import com.sharingif.blockchain.api.account.entity.AddressListenerIsWatchReq;
import com.sharingif.blockchain.api.account.entity.AddressListenerIsWatchRsp;
import com.sharingif.cube.support.service.base.IBaseService;


public interface AddressListenerService extends IBaseService<AddressListener, java.lang.String> {

    /**
     * 添加监听地址
     * @param address
     */
    void add(String address);

    /**
     * 添加地址监听
     * @param req
     */
    void add(AddressListenerAddReq req);

    /**
     * 判断地址是否是监听地址
     * @param req
     * @return
     */
    AddressListenerIsWatchRsp isWatch(AddressListenerIsWatchReq req);

}
