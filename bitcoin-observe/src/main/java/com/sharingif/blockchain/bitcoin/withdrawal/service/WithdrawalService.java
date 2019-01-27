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
     * BitCoin区块取现申请
     * @param req
     * @return
     */
    ApplyWithdrawalBitCoinRsp apply(ApplyWithdrawalBitCoinReq req);

    /**
     * 取现
     * @param
     */
    void withdrawal();

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

	
}
