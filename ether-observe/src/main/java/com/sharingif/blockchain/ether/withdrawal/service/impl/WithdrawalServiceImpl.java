package com.sharingif.blockchain.ether.withdrawal.service.impl;


import com.sharingif.blockchain.ether.account.model.entity.AccountJnl;
import com.sharingif.blockchain.ether.account.service.AccountService;
import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherReq;
import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherRsp;
import com.sharingif.blockchain.ether.app.autoconfigure.constants.CoinType;
import com.sharingif.blockchain.ether.app.exception.InvalidAddressException;
import com.sharingif.blockchain.ether.block.model.entity.Transaction;
import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.ether.block.service.TransactionBusinessService;
import com.sharingif.blockchain.ether.withdrawal.dao.WithdrawalDAO;
import com.sharingif.blockchain.ether.withdrawal.model.entity.Withdrawal;
import com.sharingif.blockchain.ether.withdrawal.service.WithdrawalService;
import com.sharingif.cube.batch.core.JobConfig;
import com.sharingif.cube.batch.core.JobModel;
import com.sharingif.cube.batch.core.JobService;
import com.sharingif.cube.core.util.StringUtils;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.utils.Numeric;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WithdrawalServiceImpl extends BaseServiceImpl<Withdrawal, String> implements WithdrawalService {

    private WithdrawalDAO withdrawalDAO;
    private TransactionBusinessService transactionBusinessService;
    private AccountService accountService;
    private JobConfig withdrawalFinishNoticeJobConfig;
    private JobService jobService;

    public void setWithdrawalDAO(WithdrawalDAO withdrawalDAO) {
        super.setBaseDAO(withdrawalDAO);
        this.withdrawalDAO = withdrawalDAO;
    }

    @Resource
    public void setTransactionBusinessService(TransactionBusinessService transactionBusinessService) {
        this.transactionBusinessService = transactionBusinessService;
    }
    @Resource
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
    @Resource
    public void setWithdrawalFinishNoticeJobConfig(JobConfig withdrawalFinishNoticeJobConfig) {
        this.withdrawalFinishNoticeJobConfig = withdrawalFinishNoticeJobConfig;
    }
    @Resource
    public void setJobService(JobService jobService) {
        this.jobService = jobService;
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

    @Transactional
    protected void readyFinishNotice(TransactionBusiness transactionBusiness) {
        transactionBusinessService.updateStatusToFinishNoticing(transactionBusiness.getId());

        JobModel jobModel = new JobModel();
        jobModel.setLookupPath(withdrawalFinishNoticeJobConfig.getLookupPath());
        jobModel.setDataId(transactionBusiness.getId());
        jobModel.setPlanExecuteTime(transactionBusiness.getTxTime());
        jobService.add(null, jobModel);
    }

    @Override
    public void readyFinishNotice() {
        TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
        queryTransactionBusiness.setStatus(TransactionBusiness.STATUS_SETTLED);
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
            readyFinishNotice(transactionBusiness);
        }
    }

    @Override
    public void finishNotice(String id) {
        TransactionBusiness transactionBusiness = transactionBusinessService.getById(id);
        if(!TransactionBusiness.STATUS_FINISH_NOTICING.equals(transactionBusiness.getStatus())) {
            return;
        }

        transactionBusinessService.updateStatusToFinishNoticed(id);
    }

    @Override
    public WithdrawalEtherRsp ether(WithdrawalEtherReq req) {
        // 校验地址
        String ethAddress = req.getAddress();
        if (StringUtils.isTrimEmpty(ethAddress) || !ethAddress.startsWith("0x") || ethAddress.length() != 42) {
            throw new InvalidAddressException();
        }

        try {
            String cleanInput = Numeric.cleanHexPrefix(ethAddress);
            // 校验不通过会报错
            Numeric.toBigIntNoPrefix(cleanInput);
        } catch (Exception e) {
            throw new InvalidAddressException();
        }

        Withdrawal withdrawal = Withdrawal.convertWithdrawalEtherReqToWithdrawal(req);
        withdrawal.setStatus(Withdrawal.STATUS_PROCESSING);

        withdrawalDAO.insert(withdrawal);

        WithdrawalEtherRsp withdrawalEtherRsp = new WithdrawalEtherRsp();
        withdrawalEtherRsp.setId(withdrawal.getId());

        return withdrawalEtherRsp;
    }
}
