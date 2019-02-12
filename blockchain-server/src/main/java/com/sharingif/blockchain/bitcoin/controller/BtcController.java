package com.sharingif.blockchain.bitcoin.controller;

import com.sharingif.blockchain.api.bitcoin.entity.SignMessageReq;
import com.sharingif.blockchain.api.bitcoin.entity.SignMessageRsp;
import com.sharingif.blockchain.bitcoin.service.BtcService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@RequestMapping(value="btc")
public class BtcController {

    private BtcService btcService;

    @Resource
    public void setBtcService(BtcService btcService) {
        this.btcService = btcService;
    }

    /**
     * 生成transfer交易
     * @return
     */
    @RequestMapping(value="signMessage", method= RequestMethod.POST)
    public SignMessageRsp signMessage(SignMessageReq req) {

        return btcService.signMessage(req);
    }

}
