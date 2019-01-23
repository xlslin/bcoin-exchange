package com.sharingif.blockchain.bitcoin.deposit.service.impl;


import com.sharingif.blockchain.bitcoin.account.model.entity.AccountJnl;
import com.sharingif.blockchain.bitcoin.account.service.AccountService;
import com.sharingif.blockchain.bitcoin.block.dao.TransactionBusinessDAO;
import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.bitcoin.deposit.service.DepositService;
import com.sharingif.cube.batch.core.JobConfig;
import com.sharingif.cube.batch.core.JobService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DepositServiceImpl implements DepositService {

    private TransactionBusinessDAO transactionBusinessDAO;
    private JobConfig depositInitNoticeJobConfig;
    private JobService jobService;
    private AccountService accountService;
    private JobConfig depositFinishNoticeJobConfig;

    @Resource
    public void setTransactionBusinessDAO(TransactionBusinessDAO transactionBusinessDAO) {
        this.transactionBusinessDAO = transactionBusinessDAO;
    }
    @Resource
    public void setDepositInitNoticeJobConfig(JobConfig depositInitNoticeJobConfig) {
        this.depositInitNoticeJobConfig = depositInitNoticeJobConfig;
    }
    @Resource
    public void setJobService(JobService jobService) {
        this.jobService = jobService;
    }
    @Resource
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
    @Resource
    public void setDepositFinishNoticeJobConfig(JobConfig depositFinishNoticeJobConfig) {
        this.depositFinishNoticeJobConfig = depositFinishNoticeJobConfig;
    }

    @Override
    public void addUntreated(TransactionBusiness transactionBusiness) {

        transactionBusiness.setType(TransactionBusiness.TYPE_DEPOSIT);
        transactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);
        transactionBusiness.setSettleStatus(TransactionBusiness.SETTLE_STATUS_UNTREATED);
        transactionBusiness.setTxStatus(BlockChain.STATUS_UNVERIFIED);

        transactionBusinessDAO.insert(transactionBusiness);

        deposit(transactionBusiness);
    }

    public void deposit(TransactionBusiness transactionBusiness) {
        accountService.addBalance(
                transactionBusiness.getTxTo()
                ,transactionBusiness.getCoinType()
                ,transactionBusiness.getAmount()
                ,transactionBusiness.getTxFrom()
                ,transactionBusiness.getTxTo()
                ,AccountJnl.TYPE_DEPOSIT
                ,transactionBusiness.getId()
                ,transactionBusiness.getTxTime()
        );
    }

    protected void depositReback(TransactionBusiness transactionBusiness) {
        accountService.subtractBalance(
                transactionBusiness.getTxTo()
                ,transactionBusiness.getCoinType()
                ,transactionBusiness.getAmount()
                ,transactionBusiness.getTxFrom()
                ,transactionBusiness.getTxTo()
                ,AccountJnl.TYPE_DEPOSIT_REBACK
                ,transactionBusiness.getId()
                ,transactionBusiness.getTxTime()
        );
    }

    @Override
    public void depositConfirmed(TransactionBusiness transactionBusiness) {
        depositReback(transactionBusiness);
    }

}
