package com.sharingif.blockchain.withdrawal.service.impl;


import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherReq;
import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherRsp;
import com.sharingif.blockchain.ether.api.withdrawal.service.WithdrawalEtherApiService;
import com.sharingif.blockchain.withdrawal.service.WithdrawalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    private WithdrawalEtherApiService withdrawalEtherApiService;

    @Resource
    public void setWithdrawalEtherApiService(WithdrawalEtherApiService withdrawalEtherApiService) {
        this.withdrawalEtherApiService = withdrawalEtherApiService;
    }

    @Override
    public WithdrawalEtherRsp ether(WithdrawalEtherReq req) {
        req.setAddress(req.getAddress().toLowerCase());
        return withdrawalEtherApiService.ether(req);
    }
}
