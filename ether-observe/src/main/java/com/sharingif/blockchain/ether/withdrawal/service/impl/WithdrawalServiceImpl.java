package com.sharingif.blockchain.ether.withdrawal.service.impl;


import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.ether.block.service.TransactionBusinessService;
import com.sharingif.blockchain.ether.withdrawal.service.WithdrawalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    private TransactionBusinessService transactionBusinessService;

    @Resource
    public void setTransactionBusinessService(TransactionBusinessService transactionBusinessService) {
        this.transactionBusinessService = transactionBusinessService;
    }

    @Override
    public void addUntreated(TransactionBusiness transactionBusiness) {
        transactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);
        transactionBusiness.setType(TransactionBusiness.TYPE_WITHDRAWAL);

        transactionBusinessService.add(transactionBusiness);
    }
}
