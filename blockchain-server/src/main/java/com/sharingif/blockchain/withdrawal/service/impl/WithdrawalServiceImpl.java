package com.sharingif.blockchain.withdrawal.service.impl;


import com.sharingif.blockchain.bitcoin.api.withdrawal.entity.ApplyWithdrawalBitCoinReq;
import com.sharingif.blockchain.bitcoin.api.withdrawal.entity.ApplyWithdrawalBitCoinRsp;
import com.sharingif.blockchain.bitcoin.api.withdrawal.service.WithdrawalBitCoinApiService;
import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherReq;
import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherRsp;
import com.sharingif.blockchain.ether.api.withdrawal.service.WithdrawalEtherApiService;
import com.sharingif.blockchain.withdrawal.service.WithdrawalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    private WithdrawalEtherApiService withdrawalEtherApiService;
    private WithdrawalBitCoinApiService withdrawalBitCoinApiService;

    @Resource
    public void setWithdrawalEtherApiService(WithdrawalEtherApiService withdrawalEtherApiService) {
        this.withdrawalEtherApiService = withdrawalEtherApiService;
    }
    @Resource
    public void setWithdrawalBitCoinApiService(WithdrawalBitCoinApiService withdrawalBitCoinApiService) {
        this.withdrawalBitCoinApiService = withdrawalBitCoinApiService;
    }

    @Override
    public WithdrawalEtherRsp ether(WithdrawalEtherReq req) {
        return withdrawalEtherApiService.ether(req);
    }

    @Override
    public ApplyWithdrawalBitCoinRsp applyBitCoin(ApplyWithdrawalBitCoinReq req) {
        return withdrawalBitCoinApiService.apply(req);
    }
}
