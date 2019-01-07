package com.sharingif.blockchain.ether.withdrawal.service;


import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherReq;
import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherRsp;
import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.ether.withdrawal.model.entity.Withdrawal;
import com.sharingif.cube.support.service.base.IBaseService;

public interface WithdrawalService extends IBaseService<Withdrawal, String> {

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
     * 根据交易hash查询提现信息
     * @param txHash
     * @return
     */
    Withdrawal getWithdrawalByTxHash(String txHash);

    /**
     * 添加未处理提现
     * @param transactionBusiness
     */
    void addUntreated(TransactionBusiness transactionBusiness);

    /**
     * 初始化提现通知
     * @param id
     */
    void initWithdrawalNotice(String id);

    /**
     * 取现处理中
     * @param transactionBusiness
     */
    void processingWithdrawal(TransactionBusiness transactionBusiness);

    /**
     * 取现成功
     * @param transactionBusiness
     */
    void withdrawalSuccess(TransactionBusiness transactionBusiness);

    /**
     * 取现失败
     * @param transactionBusiness
     */
    void withdrawalFailure(TransactionBusiness transactionBusiness);

    /**
     * 提现完成通知
     * @return
     */
    void finishNotice();

    /**
     * 添加ether取现
     * @param req
     * @return
     */
    WithdrawalEtherRsp ether(WithdrawalEtherReq req);

    /**
     * ether取现
     */
    void withdrawalEther();

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
