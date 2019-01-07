package com.sharingif.blockchain.ether.service;

import com.sharingif.blockchain.api.ether.entity.Erc20SignMessageReq;
import com.sharingif.blockchain.api.ether.entity.Erc20SignMessageRsp;
import com.sharingif.blockchain.api.ether.entity.SignMessageReq;
import com.sharingif.blockchain.api.ether.entity.SignMessageRsp;

/**
 * EthService
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/26 下午12:57
 */
public interface EtherService {

    /**
     * 转账签名
     * @param req
     * @return
     */
    SignMessageRsp signMessage(SignMessageReq req);

    /**
     * Erc20转账签名
     * @param req
     * @return
     */
    Erc20SignMessageRsp erc20SignMessage(Erc20SignMessageReq req);

}
