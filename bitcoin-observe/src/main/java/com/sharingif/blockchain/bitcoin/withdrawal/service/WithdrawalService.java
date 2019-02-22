package com.sharingif.blockchain.bitcoin.withdrawal.service;


import com.sharingif.blockchain.bitcoin.api.withdrawal.entity.ApplyWithdrawalBitCoinReq;
import com.sharingif.blockchain.bitcoin.api.withdrawal.entity.ApplyWithdrawalBitCoinRsp;
import com.sharingif.blockchain.bitcoin.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.Withdrawal;
import com.sharingif.cube.support.service.base.IBaseService;


public interface WithdrawalService extends IBaseService<Withdrawal, java.lang.String> {

    /**
     * 添加未处理提现
     * @param transactionBusiness
     */
    void addUntreated(TransactionBusiness transactionBusiness);

    /**
     * 修改状态为处理中
     * @param id
     * @return
     */
    int updateStatusToProcessing(String id);

    /**
     * 修改状态为提现通知中
     * @param id
     * @return
     */
    int updateStatusToNoticing(String id);

    /**
     * 修改状态为提现成功通知
     * @param id
     * @return
     */
    int updateStatusToSuccessNoticed(String id);

    /**
     * 修改状态为提现失败通知
     * @param id
     * @return
     */
    int updateStatusToFailureNoticed(String id);

    /**
     * BitCoin区块取现申请
     * @param req
     * @return
     */
    ApplyWithdrawalBitCoinRsp apply(ApplyWithdrawalBitCoinReq req);

    /**
     * 取现
     * @param
     */
    void btc();

    /**
     * 取现
     * @param
     */
    void usdt();

    /**
     * 取现处理中
     * @param transactionBusiness
     */
    void withdrawal(TransactionBusiness transactionBusiness);

    /**
     * 准备提现通知
     */
    void readyInitNotice();

    /**
     * 初始化提现通知
     * @param id
     */
    void initNotice(String id);

    /**
     * 取现确认
     * @param transactionBusiness
     * @param txStatus
     */
    void withdrawalConfirmed(TransactionBusiness transactionBusiness, String txStatus);

    /**
     * 提现完成通知
     * @return
     */
    void finishNotice();

    /**
     * 准备提现成功通知
     */
    void readyWithdrawalSuccessNotice();

    /**
     * 提现成功通知
     * @param id
     */
    void withdrawalSuccessNotice(String id);

    /**
     * 准备提现失败通知
     */
    void readyWithdrawalFailureNotice();

    /**
     * 提现失败通知
     * @param id
     */
    void withdrawalFailureNotice(String id);

}
