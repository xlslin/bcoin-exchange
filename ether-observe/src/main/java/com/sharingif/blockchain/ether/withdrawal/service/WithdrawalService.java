package com.sharingif.blockchain.ether.withdrawal.service;


import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherReq;
import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherRsp;
import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;
import com.sharingif.blockchain.ether.withdrawal.model.entity.Withdrawal;
import com.sharingif.cube.support.service.base.IBaseService;

public interface WithdrawalService extends IBaseService<Withdrawal, String> {

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
     * 准备提现完成通知
     * @return
     */
    void readyFinishNotice();

    /**
     * 提现完成通知
     * @param id
     * @return
     */
    void finishNotice(String id);

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

}
