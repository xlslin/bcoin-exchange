package com.sharingif.blockchain.api.ether.service;

import com.sharingif.blockchain.api.ether.entity.Erc20TransferReq;
import com.sharingif.blockchain.api.ether.entity.Erc20TransferRsp;
import com.sharingif.blockchain.api.ether.entity.EtherTransferReq;
import com.sharingif.blockchain.api.ether.entity.EtherTransferRsp;
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
@RequestMapping(value="eth")
public interface EtherApiService {

    /**
     * 转账
     * @param req
     * @return
     */
    @RequestMapping(value="transfer", method= RequestMethod.POST)
    EtherTransferRsp transfer(EtherTransferReq req);

    /**
     * 转账
     * @param req
     * @return
     */
    @RequestMapping(value="erc20Transfer", method= RequestMethod.POST)
    Erc20TransferRsp erc20Transfer(Erc20TransferReq req);

}
