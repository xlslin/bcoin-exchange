package com.sharingif.blockchain.crypto.bitcoin.controller;

import com.sharingif.blockchain.crypto.api.bitcoin.entity.OmniSimpleSendSignMessageReq;
import com.sharingif.blockchain.crypto.api.bitcoin.entity.SignMessageReq;
import com.sharingif.blockchain.crypto.api.bitcoin.entity.SignMessageRsp;
import com.sharingif.blockchain.crypto.bitcoin.service.BitCoinService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.validation.Valid;

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
    public SignMessageRsp signMessage(@Valid SignMessageReq req) {

        return bitCoinService.signMessage(req);
    }

    /**
     * 生成omni转账签名
     * @return
     */
    @RequestMapping(value="omniSimpleSendSignMessage", method= RequestMethod.POST)
    public SignMessageRsp omniSimpleSendSignMessage(OmniSimpleSendSignMessageReq req) {

        return bitCoinService.omniSimpleSendSignMessage(req);
    }

}
