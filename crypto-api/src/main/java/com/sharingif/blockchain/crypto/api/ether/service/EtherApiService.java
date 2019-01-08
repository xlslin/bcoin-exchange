package com.sharingif.blockchain.crypto.api.ether.service;

import com.sharingif.blockchain.crypto.api.ether.entity.Erc20SignMessageReq;
import com.sharingif.blockchain.crypto.api.ether.entity.Erc20SignMessageRsp;
import com.sharingif.blockchain.crypto.api.ether.entity.SignMessageReq;
import com.sharingif.blockchain.crypto.api.ether.entity.SignMessageRsp;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;

/**
 * EthService
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/26 下午12:57
 */
@RequestMapping(value="ether")
public interface EtherApiService {

    /**
     * 转账签名
     * @param req
     * @return
     */
    @RequestMapping(value="signMessage", method= RequestMethod.POST)
    SignMessageRsp signMessage(SignMessageReq req);

    /**
     * Erc20转账签名
     * @param req
     * @return
     */
    @RequestMapping(value="erc20SignMessage", method= RequestMethod.POST)
    Erc20SignMessageRsp erc20SignMessage(Erc20SignMessageReq req);

}
