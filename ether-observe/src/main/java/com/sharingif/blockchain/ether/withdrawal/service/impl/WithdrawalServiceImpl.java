package com.sharingif.blockchain.ether.withdrawal.service.impl;


import com.sharingif.blockchain.ether.account.model.entity.AccountJnl;
import com.sharingif.blockchain.ether.account.service.AccountService;
import com.sharingif.blockchain.ether.app.autoconfigure.constants.CoinType;
import com.sharingif.blockchain.ether.block.model.entity.Transaction;
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
    private AccountService accountService;

    @Resource
    public void setTransactionBusinessService(TransactionBusinessService transactionBusinessService) {
        this.transactionBusinessService = transactionBusinessService;
    }
    @Resource
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void addUntreated(TransactionBusiness transactionBusiness) {
        transactionBusiness.setType(TransactionBusiness.TYPE_WITHDRAWAL);

        transactionBusinessService.addUntreated(transactionBusiness);

        processingWithdrawal(transactionBusiness);
    }

    @Override
    public void initWithdrawalNotice(String id) {
        TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
        queryTransactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);
        queryTransactionBusiness.setType(TransactionBusiness.TYPE_WITHDRAWAL);
        PaginationCondition<TransactionBusiness> paginationCondition = new PaginationCondition<TransactionBusiness>();
        paginationCondition.setCondition(queryTransactionBusiness);
        paginationCondition.setQueryCount(false);
        paginationCondition.setCurrentPage(1);
        paginationCondition.setPageSize(20);

        PaginationRepertory<TransactionBusiness> transactionBusinessPaginationRepertory = transactionBusinessService.getPagination(paginationCondition);
        List<TransactionBusiness> transactionBusinessList = transactionBusinessPaginationRepertory.getPageItems();
        if(transactionBusinessList == null || transactionBusinessList.isEmpty()) {
            return;
        }

        for (TransactionBusiness transactionBusiness : transactionBusinessList) {
            transactionBusinessService.updateStatusToInitNoticed(transactionBusiness.getId());
        }
    }

    @Override
    public void processingWithdrawal(TransactionBusiness transactionBusiness) {
        accountService.frozenBalance(
                transactionBusiness.getTxFrom()
                ,transactionBusiness.getCoinType()
                ,transactionBusiness.getAmount()
                ,transactionBusiness.getTxFrom()
                ,transactionBusiness.getTxTo()
                ,AccountJnl.TYPE_WITHDRAWAL
                ,transactionBusiness.getId()
                ,transactionBusiness.getTxTime()
        );

        // 手续费
        if(CoinType.ETH.name().equals(transactionBusiness.getCoinType())) {
            accountService.frozenBalance(
                    transactionBusiness.getTxFrom()
                    ,transactionBusiness.getCoinType()
                    ,transactionBusiness.getFee()
                    ,transactionBusiness.getTxFrom()
                    ,transactionBusiness.getTxTo()
                    ,AccountJnl.TYPE_WITHDRAWAL_FEE
                    ,transactionBusiness.getId()
                    ,transactionBusiness.getTxTime()
            );
        } else {
            accountService.frozenBalance(
                    transactionBusiness.getTxFrom()
                    ,CoinType.ETH.name()
                    ,transactionBusiness.getFee()
                    ,transactionBusiness.getTxFrom()
                    ,transactionBusiness.getTxTo()
                    ,AccountJnl.TYPE_WITHDRAWAL_FEE
                    ,transactionBusiness.getId()
                    ,transactionBusiness.getTxTime()
            );
        }
    }

    @Override
    public void withdrawalSuccess(TransactionBusiness transactionBusiness) {
        if(Transaction.TX_RECEIPT_STATUS_SUCCESS.equals(transactionBusiness.getTxReceiptStatus())) {
            accountService.subtractFrozenBalance(
                    transactionBusiness.getTxFrom()
                    ,transactionBusiness.getCoinType()
                    ,transactionBusiness.getAmount()
                    ,transactionBusiness.getTxFrom()
                    ,transactionBusiness.getTxTo()
                    ,transactionBusiness.getId()
                    ,transactionBusiness.getTxTime()
            );
        } else {
            accountService.unFrozenBalance(
                    transactionBusiness.getTxFrom()
                    ,transactionBusiness.getCoinType()
                    ,transactionBusiness.getAmount()
                    ,transactionBusiness.getTxFrom()
                    ,transactionBusiness.getTxTo()
                    ,AccountJnl.TYPE_WITHDRAWAL_REBACK
                    ,transactionBusiness.getId()
                    ,transactionBusiness.getTxTime()
            );
        }

        // 手续费
        accountService.subtractFrozenBalance(
                transactionBusiness.getTxFrom()
                ,CoinType.ETH.name()
                ,transactionBusiness.getFee()
                ,transactionBusiness.getTxFrom()
                ,transactionBusiness.getTxTo()
                ,transactionBusiness.getId()
                ,transactionBusiness.getTxTime()
        );
    }

    @Override
    public void withdrawalFailure(TransactionBusiness transactionBusiness) {
        accountService.unFrozenBalance(
                transactionBusiness.getTxFrom()
                ,transactionBusiness.getCoinType()
                ,transactionBusiness.getAmount()
                ,transactionBusiness.getTxFrom()
                ,transactionBusiness.getTxTo()
                ,AccountJnl.TYPE_WITHDRAWAL_REBACK
                ,transactionBusiness.getId()
                ,transactionBusiness.getTxTime()
        );

        accountService.unFrozenBalance(
                transactionBusiness.getTxFrom()
                ,CoinType.ETH.name()
                ,transactionBusiness.getAmount()
                ,transactionBusiness.getTxFrom()
                ,transactionBusiness.getTxTo()
                ,AccountJnl.TYPE_WITHDRAWAL_FEE_REBACK
                ,transactionBusiness.getId()
                ,transactionBusiness.getTxTime()
        );
    }
}
