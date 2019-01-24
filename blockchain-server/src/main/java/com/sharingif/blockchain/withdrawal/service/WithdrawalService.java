package com.sharingif.blockchain.withdrawal.service;


import com.sharingif.blockchain.bitcoin.api.withdrawal.entity.ApplyWithdrawalBitCoinReq;
import com.sharingif.blockchain.bitcoin.api.withdrawal.entity.ApplyWithdrawalBitCoinRsp;
import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherReq;
import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherRsp;

public interface WithdrawalService {

    /**
     * 添加ether取现
     * @param req
     * @return
     */
    WithdrawalEtherRsp ether(WithdrawalEtherReq req);

    /**
     * 申请bitcoin区块提现
     * @param req
     * @return
     */
    ApplyWithdrawalBitCoinRsp applyBitCoin(ApplyWithdrawalBitCoinReq req);

}
