package com.sharingif.blockchain.api.bitcoin.service;

import com.sharingif.blockchain.api.bitcoin.entity.SignMessageReq;
import com.sharingif.blockchain.api.bitcoin.entity.SignMessageRsp;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;

@RequestMapping(value="bitcoin")
public interface BitCoinApiService {

    /**
     * 转账签名
     * @param req
     * @return
     */
    @RequestMapping(value="signMessage", method= RequestMethod.POST)
    SignMessageRsp signMessage(SignMessageReq req);

}
