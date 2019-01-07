package com.sharingif.blockchain.crypto.ether.service;

import com.sharingif.blockchain.crypto.api.ether.entity.Erc20TransferReq;
import com.sharingif.blockchain.crypto.api.ether.entity.Erc20TransferRsp;
import com.sharingif.blockchain.crypto.api.ether.entity.EtherTransferReq;
import com.sharingif.blockchain.crypto.api.ether.entity.EtherTransferRsp;

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
     * 转账
     * @param req
     * @return
     */
    EtherTransferRsp transfer(EtherTransferReq req);

    /**
     * 转账
     * @param req
     * @return
     */
    Erc20TransferRsp erc20Transfer(Erc20TransferReq req);

}
