package com.sharingif.blockchain.ether.controller;

import com.sharingif.blockchain.api.ether.entity.Erc20SignMessageReq;
import com.sharingif.blockchain.api.ether.entity.Erc20SignMessageRsp;
import com.sharingif.blockchain.api.ether.entity.SignMessageReq;
import com.sharingif.blockchain.api.ether.entity.SignMessageRsp;
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
    @RequestMapping(value="signMessage", method= RequestMethod.POST)
    public SignMessageRsp signMessage(SignMessageReq req) {

        return etherService.signMessage(req);
    }

    /**
     * 生成erc20 transfer交易
     * @return
     */
    @RequestMapping(value="erc20SignMessage", method= RequestMethod.POST)
    public Erc20SignMessageRsp erc20SignMessage(Erc20SignMessageReq req) {

        return etherService.erc20SignMessage(req);
    }

}
