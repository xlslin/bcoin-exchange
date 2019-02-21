package com.sharingif.blockchain.crypto.api.bitcoin.service;

import com.sharingif.blockchain.crypto.api.bitcoin.entity.OmniSimpleSendSignMessageReq;
import com.sharingif.blockchain.crypto.api.bitcoin.entity.SignMessageReq;
import com.sharingif.blockchain.crypto.api.bitcoin.entity.SignMessageRsp;
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

    /**
     * omni转账签名
     * @param req
     * @return
     */
    @RequestMapping(value="omniSimpleSendSignMessage", method= RequestMethod.POST)
    SignMessageRsp omniSimpleSendSignMessage(OmniSimpleSendSignMessageReq req);

}
