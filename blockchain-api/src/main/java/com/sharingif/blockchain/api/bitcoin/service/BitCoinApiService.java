package com.sharingif.blockchain.api.bitcoin.service;

import com.sharingif.blockchain.api.bitcoin.entity.DepositWithdrawalNoticeReq;
import com.sharingif.blockchain.api.bitcoin.entity.OmniSimpleSendSignMessageReq;
import com.sharingif.blockchain.api.bitcoin.entity.SignMessageReq;
import com.sharingif.blockchain.api.bitcoin.entity.SignMessageRsp;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;

@RequestMapping(value="btc")
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

    /**
     * 充值提现通知
     * @param req
     */
    @RequestMapping(value="depositWithdrawalNotice", method= RequestMethod.POST)
    void depositWithdrawalNotice(DepositWithdrawalNoticeReq req);

}
