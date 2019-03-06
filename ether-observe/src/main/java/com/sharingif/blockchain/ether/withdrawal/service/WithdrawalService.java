package com.sharingif.blockchain.ether.withdrawal.service;


import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherReq;
import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherRsp;
import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.ether.withdrawal.model.entity.Withdrawal;
import com.sharingif.cube.support.service.base.IBaseService;

import java.util.List;

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
     * 准备提现通知
     * @param withdrawalList
     */
    void readyInitNotice(List<Withdrawal> withdrawalList);

    /**
     * 初始化提现通知
     * @param id
     */
    void initNotice(String id);

    /**
     * 取现处理中
     * @param transactionBusiness
     */
    void withdrawal(TransactionBusiness transactionBusiness);

    /**
     * 取现确认
     * @param transactionBusiness
     * @param txStatus
     */
    void withdrawalConfirmed(TransactionBusiness transactionBusiness, String txStatus);

    /**
     * 提现完成通知
     * @param transactionBusinessList
     */
    void finishNotice(List<TransactionBusiness> transactionBusinessList);

    /**
     * 添加ether取现
     * @param req
     * @return
     */
    WithdrawalEtherRsp ether(WithdrawalEtherReq req);

    /**
     * ether取现
     * @param withdrawalList
     */
    void withdrawalEther(List<Withdrawal> withdrawalList);

    /**
     * 准备提现成功通知
     * @param withdrawalList
     */
    void readyWithdrawalSuccessNotice(List<Withdrawal> withdrawalList);

    /**
     * 提现成功通知
     * @param id
     */
    void withdrawalSuccessNotice(String id);

    /**
     * 准备提现失败通知
     * @param withdrawalList
     */
    void readyWithdrawalFailureNotice(List<Withdrawal> withdrawalList);

    /**
     * 提现失败通知
     * @param id
     */
    void withdrawalFailureNotice(String id);

}
