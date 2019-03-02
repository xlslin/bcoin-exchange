package com.sharingif.blockchain.ether.withdrawal.service.impl;


import com.sharingif.blockchain.api.ether.entity.*;
import com.sharingif.blockchain.api.ether.service.EtherApiService;
import com.sharingif.blockchain.ether.account.model.entity.Account;
import com.sharingif.blockchain.ether.account.model.entity.AccountJnl;
import com.sharingif.blockchain.ether.account.service.AccountService;
import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherReq;
import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherRsp;
import com.sharingif.blockchain.ether.app.autoconfigure.constants.CoinType;
import com.sharingif.blockchain.ether.app.constants.Constants;
import com.sharingif.blockchain.ether.app.exception.InvalidAddressException;
import com.sharingif.blockchain.ether.block.dao.TransactionBusinessDAO;
import com.sharingif.blockchain.ether.block.model.entity.BlockChain;
import com.sharingif.blockchain.ether.block.model.entity.Transaction;
import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.ether.block.service.EthereumBlockService;
import com.sharingif.blockchain.ether.block.service.TransactionService;
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
import org.web3j.tx.Transfer;
import org.web3j.utils.Numeric;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
public class WithdrawalServiceImpl extends BaseServiceImpl<Withdrawal, String> implements WithdrawalService {

    private WithdrawalDAO withdrawalDAO;
    private TransactionBusinessDAO transactionBusinessDAO;
    private AccountService accountService;
    private JobConfig withdrawalInitNoticeNoticeJobConfig;
    private JobConfig withdrawalSuccessNoticeJobConfig;
    private JobConfig withdrawalFailureNoticeJobConfig;
    private JobService jobService;
    private EthereumBlockService ethereumBlockService;
    private EtherApiService etherApiService;
    private TransactionService transactionService;

    @Resource
    public void setWithdrawalDAO(WithdrawalDAO withdrawalDAO) {
        super.setBaseDAO(withdrawalDAO);
        this.withdrawalDAO = withdrawalDAO;
    }
    @Resource
    public void setTransactionBusinessDAO(TransactionBusinessDAO transactionBusinessDAO) {
        this.transactionBusinessDAO = transactionBusinessDAO;
    }
    @Resource
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
    @Resource
    public void setWithdrawalInitNoticeNoticeJobConfig(JobConfig withdrawalInitNoticeNoticeJobConfig) {
        this.withdrawalInitNoticeNoticeJobConfig = withdrawalInitNoticeNoticeJobConfig;
    }
    @Resource
    public void setWithdrawalSuccessNoticeJobConfig(JobConfig withdrawalSuccessNoticeJobConfig) {
        this.withdrawalSuccessNoticeJobConfig = withdrawalSuccessNoticeJobConfig;
    }
    @Resource
    public void setWithdrawalFailureNoticeJobConfig(JobConfig withdrawalFailureNoticeJobConfig) {
        this.withdrawalFailureNoticeJobConfig = withdrawalFailureNoticeJobConfig;
    }
    @Resource
    public void setJobService(JobService jobService) {
        this.jobService = jobService;
    }
    @Resource
    public void setEthereumBlockService(EthereumBlockService ethereumBlockService) {
        this.ethereumBlockService = ethereumBlockService;
    }
    @Resource
    public void setEtherApiService(EtherApiService etherApiService) {
        this.etherApiService = etherApiService;
    }
    @Resource
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public int updateStatusToProcessing(String id) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setId(id);

        withdrawal.setStatus(Withdrawal.STATUS_PROCESSING);

        return withdrawalDAO.updateById(withdrawal);
    }

    @Override
    public int updateStatusToNoticing(String id) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setId(id);

        withdrawal.setStatus(Withdrawal.STATUS_NOTICING);

        return withdrawalDAO.updateById(withdrawal);
    }

    @Override
    public int updateStatusToSuccessNoticed(String id) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setId(id);

        withdrawal.setStatus(Withdrawal.STATUS_SUCCESS_NOTICED);

        return withdrawalDAO.updateById(withdrawal);
    }

    @Override
    public int updateStatusToFailureNoticed(String id) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setId(id);

        withdrawal.setStatus(Withdrawal.STATUS_FAILURE_NOTICED);

        return withdrawalDAO.updateById(withdrawal);
    }

    @Override
    public Withdrawal getWithdrawalByTxHash(String txHash) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setTxHash(txHash);

        return withdrawalDAO.query(withdrawal);
    }

    @Override
    public void addUntreated(TransactionBusiness transactionBusiness) {
        if(Transaction.TX_RECEIPT_STATUS_FAIL.equals(transactionBusiness.getTxReceiptStatus())) {
            transactionBusiness.setAmount(BigInteger.ZERO);
        }

        transactionBusiness.setType(TransactionBusiness.TYPE_WITHDRAWAL);
        transactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);
        transactionBusiness.setSettleStatus(TransactionBusiness.SETTLE_STATUS_UNTREATED);
        transactionBusiness.setTxStatus(BlockChain.STATUS_UNVERIFIED);

        transactionBusinessDAO.insert(transactionBusiness);

        withdrawal(transactionBusiness);
    }

    @Transactional
    protected void readyInitNotice(Withdrawal withdrawal) {

        JobModel jobModel = new JobModel();
        jobModel.setLookupPath(withdrawalInitNoticeNoticeJobConfig.getLookupPath());
        jobModel.setDataId(withdrawal.getId());
        jobModel.setPlanExecuteTime(new Date());
        jobService.add(null, jobModel);

        Withdrawal updateWithdrawal = new Withdrawal();
        updateWithdrawal.setId(withdrawal.getId());
        updateWithdrawal.setStatus(Withdrawal.STATUS_INIT_NOTICE);

        withdrawalDAO.updateById(updateWithdrawal);
    }

    @Override
    public void readyInitNotice() {
        Withdrawal queryWithdrawal = new Withdrawal();
        queryWithdrawal.setStatus(Withdrawal.STATUS_PROCESSING);
        PaginationCondition<Withdrawal> paginationCondition = new PaginationCondition<>();
        paginationCondition.setCondition(queryWithdrawal);
        paginationCondition.setQueryCount(false);
        paginationCondition.setCurrentPage(1);
        paginationCondition.setPageSize(20);

        PaginationRepertory<Withdrawal> withdrawalPaginationRepertory = withdrawalDAO.queryPagination(paginationCondition);
        List<Withdrawal> withdrawalList = withdrawalPaginationRepertory.getPageItems();
        if(withdrawalList == null || withdrawalList.isEmpty()) {
            return;
        }

        for (Withdrawal withdrawal : withdrawalList) {
            readyInitNotice(withdrawal);
        }
    }

    @Override
    public void initNotice(String id) {
        Withdrawal withdrawal = withdrawalDAO.queryById(id);
        if(!Withdrawal.STATUS_INIT_NOTICE.equals(withdrawal.getStatus())) {
            return;
        }

        DepositWithdrawalNoticeReq req = new DepositWithdrawalNoticeReq();
        req.setId(withdrawal.getId());
        req.setTxHash(withdrawal.getTxHash());
        req.setCoinType(withdrawal.getCoinType());
        req.setTxTo(withdrawal.getTxTo());
        req.setAmount(withdrawal.getAmount());
        req.setContractAddress(withdrawal.getContractAddress());
        req.setStatus(DepositWithdrawalNoticeReq.STATUS_PROCESSING);
        etherApiService.depositWithdrawalNotice(req);

        Withdrawal updateWithdrawal = new Withdrawal();
        updateWithdrawal.setId(withdrawal.getId());
        updateWithdrawal.setStatus(Withdrawal.STATUS_INIT_NOTICED);

        withdrawalDAO.updateById(updateWithdrawal);

    }

    @Override
    public void withdrawal(TransactionBusiness transactionBusiness) {
        // 交易状态为失败，只冻结手续费
        if(Transaction.TX_RECEIPT_STATUS_SUCCESS.equals(transactionBusiness.getTxReceiptStatus())) {
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
        }

        // 手续费
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

    protected void withdrawalSuccess(TransactionBusiness transactionBusiness) {
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

    protected void withdrawalFailure(TransactionBusiness transactionBusiness) {
        if(Transaction.TX_RECEIPT_STATUS_SUCCESS.equals(transactionBusiness.getTxReceiptStatus())) {
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

    @Override
    public void withdrawalConfirmed(TransactionBusiness transactionBusiness, String txStatus) {
        if(BlockChain.STATUS_VERIFY_VALID.equals(txStatus)) {
            withdrawalSuccess(transactionBusiness);
        } else {
            withdrawalFailure(transactionBusiness);
        }
    }

    @Transactional
    protected void finishNotice(TransactionBusiness transactionBusiness, Transaction transaction, Withdrawal withdrawal) {
        TransactionBusiness updateTransactionBusiness = new TransactionBusiness();
        updateTransactionBusiness.setId(transactionBusiness.getId());
        updateTransactionBusiness.setStatus(TransactionBusiness.STATUS_FINISH_NOTICED);

        transactionBusinessDAO.updateById(updateTransactionBusiness);

        if(withdrawal == null) {
            return;
        }

        Withdrawal updateWithdrawal = new Withdrawal();
        updateWithdrawal.setId(withdrawal.getId());
        updateWithdrawal.setTransactionBusinessId(transactionBusiness.getId());
        updateWithdrawal.setGasLimit(transaction.getGasLimit());
        updateWithdrawal.setGasUsed(transaction.getGasUsed());
        updateWithdrawal.setGasPrice(transaction.getGasPrice());
        updateWithdrawal.setFee(transactionBusiness.getFee());
        updateWithdrawal.setTxTime(transaction.getTxTime());
        updateWithdrawal.setStatus(Withdrawal.STATUS_SUCCESS);

        withdrawalDAO.updateById(updateWithdrawal);

    }

    @Override
    public void finishNotice() {
        TransactionBusiness queryTransactionBusiness = new TransactionBusiness();
        queryTransactionBusiness.setStatus(TransactionBusiness.STATUS_UNTREATED);
        queryTransactionBusiness.setTxStatus(BlockChain.STATUS_VERIFY_INVALID);
        queryTransactionBusiness.setSettleStatus(TransactionBusiness.SETTLE_STATUS_FINISH);
        queryTransactionBusiness.setType(TransactionBusiness.TYPE_WITHDRAWAL);
        PaginationCondition<TransactionBusiness> paginationCondition = new PaginationCondition<TransactionBusiness>();
        paginationCondition.setCondition(queryTransactionBusiness);
        paginationCondition.setQueryCount(false);
        paginationCondition.setCurrentPage(1);
        paginationCondition.setPageSize(20);

        PaginationRepertory<TransactionBusiness> transactionBusinessPaginationRepertory = transactionBusinessDAO.queryPagination(paginationCondition);
        List<TransactionBusiness> transactionBusinessList = transactionBusinessPaginationRepertory.getPageItems();
        if(transactionBusinessList == null || transactionBusinessList.isEmpty()) {
            return;
        }

        for (TransactionBusiness transactionBusiness : transactionBusinessList) {
            Transaction transaction = transactionService.getById(transactionBusiness.getTransactionId());
            Withdrawal withdrawal = getWithdrawalByTxHash(transactionBusiness.getTxHash());
            finishNotice(transactionBusiness, transaction, withdrawal);
        }
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
        withdrawal.setStatus(Withdrawal.STATUS_UNTREATED);

        withdrawalDAO.insert(withdrawal);

        WithdrawalEtherRsp withdrawalEtherRsp = new WithdrawalEtherRsp();
        withdrawalEtherRsp.setId(withdrawal.getId());
        withdrawalEtherRsp.setWithdrawalId(withdrawal.getWithdrawalId());

        return withdrawalEtherRsp;
    }

    protected void withdrawalEther(Withdrawal withdrawal) {
        // 计算手续费
        BigInteger gasLimit = withdrawal.getGasLimit();
        if(gasLimit == null) {
            if(CoinType.ETH.name().equals(withdrawal.getCoinType())) {
                gasLimit = Transfer.GAS_LIMIT;
            } else {
                gasLimit = new BigInteger(Constants.ETH_CONTRACT_TRANSFOR_GAS_LIMIT);
            }
        }
        BigInteger gasPrice = withdrawal.getGasPrice();
        if(gasPrice == null) {
            gasPrice = ethereumBlockService.getGasPrice().add(new BigInteger(Constants.ETH_TRANSFOR_GAS_PRICE_ADD));
        }
        BigInteger fee = gasLimit.multiply(gasPrice);

        BigInteger withdrawalAmount = withdrawal.getAmount();

        Account account = accountService.getAccount(withdrawal.getCoinType(), withdrawalAmount, fee, withdrawal.getContractAddress());
        if(account == null) {
            return;
        }

        BigInteger nonce = ethereumBlockService.ethGetTransactionCount(account.getAddress());

        String hexValue;
        if(CoinType.ETH.name().equals(withdrawal.getCoinType())) {
            SignMessageReq req = new SignMessageReq();
            req.setNonce(nonce);
            req.setFromAddress(account.getAddress());
            req.setToAddress(withdrawal.getTxTo());
            req.setAmount(withdrawal.getAmount());
            req.setGasPrice(gasPrice);

            SignMessageRsp rsp = etherApiService.signMessage(req);
            hexValue = rsp.getHexValue();
        } else {
            Erc20SignMessageReq req = new Erc20SignMessageReq();
            req.setNonce(nonce);
            req.setFromAddress(account.getAddress());
            req.setToAddress(withdrawal.getTxTo());
            req.setContractAddress(withdrawal.getContractAddress());
            req.setAmount(withdrawal.getAmount());
            req.setGasPrice(gasPrice);
            req.setGasLimit(gasLimit);


            Erc20SignMessageRsp rsp = etherApiService.erc20SignMessage(req);
            hexValue = rsp.getHexValue();
        }

        updateStatusToProcessing(withdrawal.getId());

        String txHash = ethereumBlockService.ethSendRawTransaction(hexValue);

        if(StringUtils.isTrimEmpty(txHash)) {
            logger.error("withdrawal generate txhash is null, withdrawal:{}", withdrawal);
            return;
        }

        Withdrawal updateWithdrawal = new Withdrawal();
        updateWithdrawal.setId(withdrawal.getId());

        updateWithdrawal.setTxFrom(account.getAddress());
        updateWithdrawal.setTxHash(txHash);

        withdrawalDAO.updateById(updateWithdrawal);
    }

    @Override
    public void withdrawalEther() {
        Withdrawal queryWithdrawal = new Withdrawal();
        queryWithdrawal.setStatus(Withdrawal.STATUS_UNTREATED);
        PaginationCondition<Withdrawal> paginationCondition = new PaginationCondition<Withdrawal>();
        paginationCondition.setCondition(queryWithdrawal);
        paginationCondition.setQueryCount(false);
        paginationCondition.setCurrentPage(1);
        paginationCondition.setPageSize(20);

        PaginationRepertory<Withdrawal> withdrawalPaginationRepertory = withdrawalDAO.queryPagination(paginationCondition);
        List<Withdrawal> withdrawalList = withdrawalPaginationRepertory.getPageItems();
        if(withdrawalList == null || withdrawalList.isEmpty()) {
            return;
        }

        for(Withdrawal withdrawal : withdrawalList) {
            withdrawalEther(withdrawal);
        }
    }

    @Transactional
    protected void readyWithdrawalNotice(Withdrawal withdrawal, JobConfig jobConfig) {
        updateStatusToNoticing(withdrawal.getId());

        JobModel jobModel = new JobModel();
        jobModel.setLookupPath(jobConfig.getLookupPath());
        jobModel.setDataId(withdrawal.getId());
        jobModel.setPlanExecuteTime(withdrawal.getTxTime());
        jobService.add(null, jobModel);
    }

    protected void readyWithdrawalNotice(String status, JobConfig jobConfig) {
        Withdrawal queryWithdrawal = new Withdrawal();
        queryWithdrawal.setStatus(status);
        PaginationCondition<Withdrawal> paginationCondition = new PaginationCondition<Withdrawal>();
        paginationCondition.setCondition(queryWithdrawal);
        paginationCondition.setQueryCount(false);
        paginationCondition.setCurrentPage(1);
        paginationCondition.setPageSize(20);

        PaginationRepertory<Withdrawal> withdrawalPaginationRepertory = withdrawalDAO.queryPagination(paginationCondition);
        List<Withdrawal> withdrawalList = withdrawalPaginationRepertory.getPageItems();
        if(withdrawalList == null || withdrawalList.isEmpty()) {
            return;
        }

        for(Withdrawal withdrawal : withdrawalList) {
            readyWithdrawalNotice(withdrawal, jobConfig);
        }
    }

    @Override
    public void readyWithdrawalSuccessNotice() {
        readyWithdrawalNotice(Withdrawal.STATUS_SUCCESS, withdrawalSuccessNoticeJobConfig);
    }

    @Override
    public void withdrawalSuccessNotice(String id) {
        Withdrawal withdrawal = getById(id);

        TransactionBusiness transactionBusiness = transactionBusinessDAO.queryById(withdrawal.getTransactionBusinessId());
        Transaction transaction = transactionService.getById(transactionBusiness.getTransactionId());

        DepositWithdrawalNoticeReq req = new DepositWithdrawalNoticeReq();
        req.setId(withdrawal.getId());
        req.setBlockNumber(transactionBusiness.getBlockNumber());
        req.setBlockHash(transactionBusiness.getBlockHash());
        req.setTxHash(transactionBusiness.getTxHash());
        req.setTxIndex(transaction.getTxIndex());
        req.setCoinType(transactionBusiness.getCoinType());
        req.setTxFrom(transactionBusiness.getTxFrom());
        req.setTxTo(transactionBusiness.getTxTo());
        req.setAmount(transactionBusiness.getAmount());
        req.setNonce(transaction.getNonce());
        req.setTxTime(transaction.getTxTime().getTime());
        req.setGasLimit(transaction.getGasLimit());
        req.setGasUsed(transaction.getGasUsed());
        req.setGasPrice(transaction.getGasPrice());
        req.setActualFee(transaction.getActualFee());
        req.setContractAddress(transaction.getContractAddress());
        req.setStatus(DepositWithdrawalNoticeReq.STATUS_SUCCESS);
        etherApiService.depositWithdrawalNotice(req);

        updateStatusToSuccessNoticed(id);
    }

    @Override
    public void readyWithdrawalFailureNotice() {
        readyWithdrawalNotice(Withdrawal.STATUS_FAILURE, withdrawalFailureNoticeJobConfig);
    }

    @Override
    public void withdrawalFailureNotice(String id) {
        Withdrawal withdrawal = getById(id);

        TransactionBusiness transactionBusiness = transactionBusinessDAO.queryById(withdrawal.getTransactionBusinessId());
        Transaction transaction = transactionService.getById(transactionBusiness.getTransactionId());

        DepositWithdrawalNoticeReq req = new DepositWithdrawalNoticeReq();
        req.setId(withdrawal.getId());
        req.setBlockNumber(transactionBusiness.getBlockNumber());
        req.setBlockHash(transactionBusiness.getBlockHash());
        req.setTxHash(transactionBusiness.getTxHash());
        req.setTxIndex(transaction.getTxIndex());
        req.setCoinType(transactionBusiness.getCoinType());
        req.setTxFrom(transactionBusiness.getTxFrom());
        req.setTxTo(transactionBusiness.getTxTo());
        req.setAmount(transactionBusiness.getAmount());
        req.setNonce(transaction.getNonce());
        req.setTxTime(transaction.getTxTime().getTime());
        req.setGasLimit(transaction.getGasLimit());
        req.setGasUsed(transaction.getGasUsed());
        req.setGasPrice(transaction.getGasPrice());
        req.setActualFee(transaction.getActualFee());
        req.setContractAddress(transaction.getContractAddress());
        req.setStatus(DepositWithdrawalNoticeReq.STATUS_FAIL);
        etherApiService.depositWithdrawalNotice(req);

        updateStatusToFailureNoticed(id);
    }
}
