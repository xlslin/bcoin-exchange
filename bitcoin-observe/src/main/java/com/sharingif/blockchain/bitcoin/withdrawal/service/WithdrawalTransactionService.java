package com.sharingif.blockchain.bitcoin.withdrawal.service;


import com.sharingif.blockchain.bitcoin.account.model.entity.AccountUnspent;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.Withdrawal;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalTransaction;
import com.sharingif.cube.support.service.base.IBaseService;

import java.math.BigInteger;
import java.util.List;


public interface WithdrawalTransactionService extends IBaseService<WithdrawalTransaction, java.lang.String> {

    /**
     * WithdrawalVinService
     * @return
     */
    WithdrawalVinService getWithdrawalVinService();
    /**
     * 返回WithdrawalVoutService
     * @return
     */
    WithdrawalVoutService getWithdrawalVoutService();

    /**
     * 添加取现交易
     * @param txHash
     * @param fee
     * @param accountUnspent
     * @param withdrawal
     */
    void addWithdrawalTransaction(String txHash, BigInteger fee, AccountUnspent accountUnspent, Withdrawal withdrawal);

    /**
     * 添加取现交易
     * @param txHash
     * @param fee
     * @param accountUnspentList
     * @param withdrawalList
     */
    void addWithdrawalTransaction(String txHash, BigInteger fee, List<AccountUnspent> accountUnspentList, List<Withdrawal> withdrawalList);

    /**
     * 根据交易hash修改状态为"初始化通知中"
     * @param txHash
     * @return
     */
    int updateStatusToInitNotice(String txHash);

    /**
     * 根据交易hash修改状态为"初始化通知成功"
     * @param txHash
     * @return
     */
    int updateStatusToInitNoticed(String txHash);

    /**
     * 根据交易hash修改状态为"提现成功"
     * @param txHash
     * @return
     */
    int updateStatusToSuccess(String txHash);
	
}
