package com.sharingif.blockchain.bitcoin.withdrawal.scheduled;

import com.sharingif.blockchain.bitcoin.app.constants.CoinType;
import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.bitcoin.block.service.TransactionBusinessService;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.Withdrawal;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalTransaction;
import com.sharingif.blockchain.bitcoin.withdrawal.service.WithdrawalService;
import com.sharingif.cube.batch.core.handler.SimpleDispatcherHandler;
import com.sharingif.cube.batch.core.request.JobRequest;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@EnableScheduling
public class WithdrawalScheduled {

    private SimpleDispatcherHandler simpleDispatcherHandler;
    private WithdrawalService withdrawalService;
    private TransactionBusinessService transactionBusinessService;

    @Resource
    public void setSimpleDispatcherHandler(SimpleDispatcherHandler simpleDispatcherHandler) {
        this.simpleDispatcherHandler = simpleDispatcherHandler;
    }
    @Resource
    public void setWithdrawalService(WithdrawalService withdrawalService) {
        this.withdrawalService = withdrawalService;
    }
    @Resource
    public void setTransactionBusinessService(TransactionBusinessService transactionBusinessService) {
        this.transactionBusinessService = transactionBusinessService;
    }

    @Scheduled(fixedRate = 1000*60*1)
    public synchronized void withdrawalBtc() {
        Withdrawal queryWithdrawal = new Withdrawal();
        queryWithdrawal.setCoinType(CoinType.BTC.name());
        queryWithdrawal.setStatus(Withdrawal.STATUS_UNTREATED);
        PaginationCondition<Withdrawal> paginationCondition = new PaginationCondition<Withdrawal>();
        paginationCondition.setCondition(queryWithdrawal);
        paginationCondition.setQueryCount(false);
        paginationCondition.setCurrentPage(1);
        paginationCondition.setPageSize(20);

        PaginationRepertory<Withdrawal> withdrawalPaginationRepertory = withdrawalService.getPagination(paginationCondition);
        List<Withdrawal> withdrawalList = withdrawalPaginationRepertory.getPageItems();
        if(withdrawalList == null || withdrawalList.isEmpty()) {
            return;
        }


        JobRequest<List<Withdrawal>> jobRequest = new JobRequest();
        jobRequest.setLookupPath("/withdrawal/btc");
        jobRequest.setData(withdrawalList);

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

    @Scheduled(fixedRate = 1000*60*1)
    public synchronized void withdrawalUsdt() {

        Withdrawal queryWithdrawal = new Withdrawal();
        queryWithdrawal.setCoinType(CoinType.USDT.name());
        queryWithdrawal.setStatus(Withdrawal.STATUS_UNTREATED);
        PaginationCondition<Withdrawal> paginationCondition = new PaginationCondition<Withdrawal>();
        paginationCondition.setCondition(queryWithdrawal);
        paginationCondition.setQueryCount(false);
        paginationCondition.setCurrentPage(1);
        paginationCondition.setPageSize(20);

        PaginationRepertory<Withdrawal> withdrawalPaginationRepertory = withdrawalService.getPagination(paginationCondition);
        List<Withdrawal> withdrawalList = withdrawalPaginationRepertory.getPageItems();
        if(withdrawalList == null || withdrawalList.isEmpty()) {
            return;
        }

        JobRequest<List<Withdrawal>> jobRequest = new JobRequest();
        jobRequest.setLookupPath("/withdrawal/usdt");
        jobRequest.setData(withdrawalList);

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

    @Scheduled(fixedRate = 1000*1)
    public synchronized void readyInitNotice() {
        WithdrawalTransaction queryWithdrawalTransaction = new WithdrawalTransaction();
        queryWithdrawalTransaction.setStatus(Withdrawal.STATUS_PROCESSING);

        PaginationCondition<WithdrawalTransaction> paginationCondition = new PaginationCondition<>();
        paginationCondition.setCondition(queryWithdrawalTransaction);
        paginationCondition.setQueryCount(false);
        paginationCondition.setCurrentPage(1);
        paginationCondition.setPageSize(20);

        PaginationRepertory<WithdrawalTransaction> withdrawalPaginationRepertory = withdrawalService.getWithdrawalTransactionService().getPagination(paginationCondition);
        List<WithdrawalTransaction> withdrawalTransactionList = withdrawalPaginationRepertory.getPageItems();
        if(withdrawalTransactionList == null || withdrawalTransactionList.isEmpty()) {
            return;
        }

        JobRequest<List<WithdrawalTransaction>> jobRequest = new JobRequest();
        jobRequest.setLookupPath("/withdrawal/readyInitNotice");
        jobRequest.setData(withdrawalTransactionList);

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

    @Scheduled(fixedRate = 1000*1)
    public synchronized void finishNotice() {
        TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
        queryTransactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);
        queryTransactionBusiness.setSettleStatus(TransactionBusiness.SETTLE_STATUS_FINISH);
        queryTransactionBusiness.setTxStatus(BlockChain.STATUS_VERIFY_VALID);
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

        JobRequest<List<TransactionBusiness>> jobRequest = new JobRequest();
        jobRequest.setLookupPath("/withdrawal/finishNotice");
        jobRequest.setData(transactionBusinessList);

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

    @Scheduled(fixedRate = 1000*1)
    public synchronized void readySuccessNotice() {
        Withdrawal queryWithdrawal = new Withdrawal();
        queryWithdrawal.setStatus(Withdrawal.STATUS_SUCCESS);
        PaginationCondition<Withdrawal> paginationCondition = new PaginationCondition<Withdrawal>();
        paginationCondition.setCondition(queryWithdrawal);
        paginationCondition.setQueryCount(false);
        paginationCondition.setCurrentPage(1);
        paginationCondition.setPageSize(20);

        PaginationRepertory<Withdrawal> withdrawalPaginationRepertory = withdrawalService.getPagination(paginationCondition);
        List<Withdrawal> withdrawalList = withdrawalPaginationRepertory.getPageItems();
        if(withdrawalList == null || withdrawalList.isEmpty()) {
            return;
        }

        JobRequest<List<Withdrawal>> jobRequest = new JobRequest();
        jobRequest.setLookupPath("/withdrawal/readySuccessNotice");
        jobRequest.setData(withdrawalList);

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

    @Scheduled(fixedRate = 1000*1)
    public synchronized void readyFailureNotice() {
        Withdrawal queryWithdrawal = new Withdrawal();
        queryWithdrawal.setStatus(Withdrawal.STATUS_FAILURE);
        PaginationCondition<Withdrawal> paginationCondition = new PaginationCondition<Withdrawal>();
        paginationCondition.setCondition(queryWithdrawal);
        paginationCondition.setQueryCount(false);
        paginationCondition.setCurrentPage(1);
        paginationCondition.setPageSize(20);

        PaginationRepertory<Withdrawal> withdrawalPaginationRepertory = withdrawalService.getPagination(paginationCondition);
        List<Withdrawal> withdrawalList = withdrawalPaginationRepertory.getPageItems();
        if(withdrawalList == null || withdrawalList.isEmpty()) {
            return;
        }

        JobRequest<List<Withdrawal>> jobRequest = new JobRequest();
        jobRequest.setLookupPath("/withdrawal/readyFailureNotice");
        jobRequest.setData(withdrawalList);

        simpleDispatcherHandler.doDispatch(jobRequest);
    }

}
