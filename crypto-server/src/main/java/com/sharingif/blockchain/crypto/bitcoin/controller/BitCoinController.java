package com.sharingif.blockchain.crypto.bitcoin.controller;

import com.sharingif.blockchain.crypto.api.bitcoin.entity.SignMessageReq;
import com.sharingif.blockchain.crypto.api.bitcoin.entity.SignMessageRsp;
import com.sharingif.blockchain.crypto.bitcoin.service.BitCoinService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@RequestMapping(value="bitcoin")
public class BitCoinController {

    private BitCoinService bitCoinService;

    @Resource
    public void setBitCoinService(BitCoinService bitCoinService) {
        this.bitCoinService = bitCoinService;
    }

    /**
     * 生成transfer交易
     * @return
     */
    @RequestMapping(value="signMessage", method= RequestMethod.POST)
    public SignMessageRsp signMessage(SignMessageReq req) {

        return bitCoinService.signMessage(req);
    }

}
