package com.sharingif.blockchain.bitcoin.api.withdrawal.service;

import com.sharingif.blockchain.bitcoin.api.withdrawal.entity.ApplyWithdrawalBitCoinReq;
import com.sharingif.blockchain.bitcoin.api.withdrawal.entity.ApplyWithdrawalBitCoinRsp;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;

@RequestMapping(value="withdrawal")
public interface WithdrawalBitCoinApiService {

    /**
     * BitCoin区块取现申请
     * @param req
     * @return
     */
    @RequestMapping(value="apply", method= RequestMethod.POST)
    ApplyWithdrawalBitCoinRsp apply(ApplyWithdrawalBitCoinReq req);

}
