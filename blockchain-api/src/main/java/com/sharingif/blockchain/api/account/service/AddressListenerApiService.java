package com.sharingif.blockchain.api.account.service;

import com.sharingif.blockchain.api.account.entity.AddressListenerIsWatchReq;
import com.sharingif.blockchain.api.account.entity.AddressListenerIsWatchRsp;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;

@RequestMapping(value="addressListener")
public interface AddressListenerApiService {

    /**
     * 判断地址是否是监听地址
     * @param req
     * @return
     */
    @RequestMapping(value="isWatch", method= RequestMethod.POST)
    AddressListenerIsWatchRsp isWatch(AddressListenerIsWatchReq req);

}
