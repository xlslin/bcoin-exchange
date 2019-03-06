package com.sharingif.blockchain.bitcoin.deposit.service.impl;


import com.sharingif.blockchain.api.bitcoin.entity.DepositWithdrawalNoticeReq;
import com.sharingif.blockchain.api.bitcoin.service.BitCoinApiService;
import com.sharingif.blockchain.bitcoin.account.model.entity.AccountJnl;
import com.sharingif.blockchain.bitcoin.account.service.AccountService;
import com.sharingif.blockchain.bitcoin.block.dao.TransactionBusinessDAO;
import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import com.sharingif.blockchain.bitcoin.block.model.entity.Transaction;
import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.bitcoin.block.service.TransactionService;
import com.sharingif.blockchain.bitcoin.deposit.service.DepositService;
import com.sharingif.cube.batch.core.JobConfig;
import com.sharingif.cube.batch.core.JobModel;
import com.sharingif.cube.batch.core.JobService;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DepositServiceImpl implements DepositService {

    private TransactionBusinessDAO transactionBusinessDAO;
    private TransactionService transactionService;
    private JobConfig depositInitNoticeJobConfig;
    private JobService jobService;
    private AccountService accountService;
    private JobConfig depositFinishNoticeJobConfig;
    private BitCoinApiService bitCoinApiService;

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
    @Resource
    public void setBitCoinApiService(BitCoinApiService bitCoinApiService) {
        this.bitCoinApiService = bitCoinApiService;
    }
    @Resource
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
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

    @Transactional
    protected void readyInitDepositNotice(TransactionBusiness transactionBusiness) {
        TransactionBusiness updateTransactionBusiness = new TransactionBusiness();
        updateTransactionBusiness.setId(transactionBusiness.getId());
        updateTransactionBusiness.setStatus(TransactionBusiness.STATUS_INIT_NOTICE);

        transactionBusinessDAO.updateById(updateTransactionBusiness);

        JobModel jobModel = new JobModel();
        jobModel.setLookupPath(depositInitNoticeJobConfig.getLookupPath());
        jobModel.setDataId(transactionBusiness.getId());
        jobModel.setPlanExecuteTime(transactionBusiness.getTxTime());
        jobService.add(null, jobModel);
    }

    @Override
    public void readyInitNotice(List<TransactionBusiness> transactionBusinessList) {
        transactionBusinessList.forEach(transactionBusiness ->{
            readyInitDepositNotice(transactionBusiness);
        });
    }

    @Override
    public void initNotice(String id) {
        TransactionBusiness transactionBusiness = transactionBusinessDAO.queryById(id);
        if(!TransactionBusiness.STATUS_INIT_NOTICE.equals(transactionBusiness.getStatus())) {
            return;
        }

        Transaction transaction = transactionService.getById(transactionBusiness.getTransactionId());

        DepositWithdrawalNoticeReq req = new DepositWithdrawalNoticeReq();
        req.setId(transactionBusiness.getId());
        req.setBlockNumber(transactionBusiness.getBlockNumber());
        req.setBlockHash(transactionBusiness.getBlockHash());
        req.setTxHash(transactionBusiness.getTxHash());
        req.setTxIndex(transaction.getTxIndex());
        req.setVioIndex(transactionBusiness.getVioIndex());
        req.setCoinType(transactionBusiness.getCoinType());
        req.setTxTo(transactionBusiness.getTxTo());
        req.setAmount(transactionBusiness.getAmount());
        req.setTxTime(transactionBusiness.getTxTime().getTime());
        req.setFee(transactionBusiness.getFee());
        req.setStatus(DepositWithdrawalNoticeReq.STATUS_PROCESSING);
        bitCoinApiService.depositWithdrawalNotice(req);

        TransactionBusiness updateTransactionBusiness = new TransactionBusiness();
        updateTransactionBusiness.setId(id);
        updateTransactionBusiness.setStatus(TransactionBusiness.STATUS_INIT_NOTICED);

        transactionBusinessDAO.updateById(updateTransactionBusiness);
    }

    @Override
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
        if(BlockChain.STATUS_VERIFY_VALID.equals(transactionBusiness.getTxStatus())) {
            return;
        } else {
            depositReback(transactionBusiness);
        }
    }

    @Transactional
    protected void readyFinishNotice(TransactionBusiness transactionBusiness) {
        TransactionBusiness updateTransactionBusiness = new TransactionBusiness();
        updateTransactionBusiness.setId(transactionBusiness.getId());
        updateTransactionBusiness.setStatus(TransactionBusiness.STATUS_FINISH_NOTICING);

        transactionBusinessDAO.updateById(updateTransactionBusiness);

        JobModel jobModel = new JobModel();
        jobModel.setLookupPath(depositFinishNoticeJobConfig.getLookupPath());
        jobModel.setDataId(transactionBusiness.getId());
        jobModel.setPlanExecuteTime(transactionBusiness.getTxTime());
        jobService.add(null, jobModel);
    }

    @Override
    public void readyFinishNotice(List<TransactionBusiness> transactionBusinessList) {
        transactionBusinessList.forEach(transactionBusiness -> {
            readyFinishNotice(transactionBusiness);
        });
    }

    @Override
    public void finishNotice(String id) {
        TransactionBusiness transactionBusiness = transactionBusinessDAO.queryById(id);
        if(!TransactionBusiness.STATUS_FINISH_NOTICING.equals(transactionBusiness.getStatus())) {
            return;
        }

        Transaction transaction = transactionService.getById(transactionBusiness.getTransactionId());

        DepositWithdrawalNoticeReq req = new DepositWithdrawalNoticeReq();
        req.setId(transactionBusiness.getId());
        req.setBlockNumber(transactionBusiness.getBlockNumber());
        req.setBlockHash(transactionBusiness.getBlockHash());
        req.setTxHash(transactionBusiness.getTxHash());
        req.setTxIndex(transaction.getTxIndex());
        req.setVioIndex(transactionBusiness.getVioIndex());
        req.setCoinType(transactionBusiness.getCoinType());
        req.setTxTo(transactionBusiness.getTxTo());
        req.setAmount(transactionBusiness.getAmount());
        req.setTxTime(transactionBusiness.getTxTime().getTime());
        req.setFee(transactionBusiness.getFee());
        if(BlockChain.STATUS_VERIFY_VALID.equals(transactionBusiness.getTxStatus())) {
            req.setStatus(com.sharingif.blockchain.api.ether.entity.DepositWithdrawalNoticeReq.STATUS_SUCCESS);
        } else {
            req.setStatus(com.sharingif.blockchain.api.ether.entity.DepositWithdrawalNoticeReq.STATUS_FAIL);
        }
        bitCoinApiService.depositWithdrawalNotice(req);

        TransactionBusiness updateTransactionBusiness = new TransactionBusiness();
        updateTransactionBusiness.setId(id);
        updateTransactionBusiness.setStatus(TransactionBusiness.STATUS_FINISH_NOTICED);

        transactionBusinessDAO.updateById(updateTransactionBusiness);
    }

}
