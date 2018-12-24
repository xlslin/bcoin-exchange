package com.sharingif.blockchain.ether.withdrawal.service.impl;


import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.ether.block.service.TransactionBusinessService;
import com.sharingif.blockchain.ether.withdrawal.service.WithdrawalService;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public void initDepositNotice(String id) {
        TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
        queryTransactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);
        queryTransactionBusiness.setType(TransactionBusiness.TYPE_WITHDRAWAL);
        PaginationCondition<TransactionBusiness> blockChainPaginationCondition = new PaginationCondition<TransactionBusiness>();
        blockChainPaginationCondition.setCondition(queryTransactionBusiness);
        blockChainPaginationCondition.setQueryCount(false);
        blockChainPaginationCondition.setCurrentPage(1);
        blockChainPaginationCondition.setPageSize(20);

        PaginationRepertory<TransactionBusiness> transactionBusinessPaginationRepertory = transactionBusinessService.getPagination(blockChainPaginationCondition);
        List<TransactionBusiness> transactionBusinessList = transactionBusinessPaginationRepertory.getPageItems();
        if(transactionBusinessList == null || transactionBusinessList.isEmpty()) {
            return;
        }

        for (TransactionBusiness transactionBusiness : transactionBusinessList) {
            transactionBusinessService.updateStatusToInitNoticed(transactionBusiness.getId());
        }
    }
}
