package com.sharingif.blockchain.ether.deposit.service;


import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;

public interface DepositService {

    /**
     * 添加未处理充值
     * @param transactionBusiness
     */
    void addUntreated(TransactionBusiness transactionBusiness);

    /**
     * 修改状态为初始化充值通知中
     * @param id
     * @return
     */
    int updateStatusToInitNotice(String id);

    /**
     * 修改状态为初始化充值通知成功
     * @param id
     * @return
     */
    int updateStatusToInitNoticed(String id);

    /**
     * 准备充值通知
     */
    void readyDepositNotice();

    /**
     * 初始化充值通知中
     * @param id
     */
    void initDepositNotice(String id);

}
