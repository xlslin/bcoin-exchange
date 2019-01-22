package com.sharingif.blockchain.bitcoin.api.account.service;

import com.sharingif.blockchain.bitcoin.api.account.entity.AddressListenerAddReq;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;

@RequestMapping(value="addressListener")
public interface AddressListenerApiService {

    /**
     * 添加地址监听
     * @return
     */
    @RequestMapping(value="add", method= RequestMethod.POST)
    void add(AddressListenerAddReq req);

}
