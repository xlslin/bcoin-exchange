package com.sharingif.blockchain.ether.controller;

import com.sharingif.blockchain.crypto.api.ether.entity.Erc20TransferReq;
import com.sharingif.blockchain.crypto.api.ether.entity.Erc20TransferRsp;
import com.sharingif.blockchain.crypto.api.ether.entity.EtherTransferReq;
import com.sharingif.blockchain.crypto.api.ether.entity.EtherTransferRsp;
import com.sharingif.blockchain.ether.service.EtherService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * EthController
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/26 下午12:58
 */
@Controller
@RequestMapping(value="eth")
public class EtherController {

    private EtherService etherService;

    @Resource
    public void setEtherService(EtherService etherService) {
        this.etherService = etherService;
    }

    /**
     * 生成transfer交易
     * @return
     */
    @RequestMapping(value="transfer", method= RequestMethod.POST)
    public EtherTransferRsp transfer(EtherTransferReq req) {

        return etherService.transfer(req);
    }

    /**
     * 生成erc20 transfer交易
     * @return
     */
    @RequestMapping(value="erc20Transfer", method= RequestMethod.POST)
    public Erc20TransferRsp erc20Transfer(Erc20TransferReq req) {

        return etherService.erc20Transfer(req);
    }

}
