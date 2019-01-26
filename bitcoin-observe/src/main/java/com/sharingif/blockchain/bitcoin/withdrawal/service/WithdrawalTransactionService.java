package com.sharingif.blockchain.bitcoin.withdrawal.service;


import com.sharingif.blockchain.bitcoin.account.model.entity.AccountUnspent;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.Withdrawal;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalTransaction;
import com.sharingif.cube.support.service.base.IBaseService;

import java.math.BigInteger;
import java.util.List;


public interface WithdrawalTransactionService extends IBaseService<WithdrawalTransaction, java.lang.String> {

    /**
     * 添加取现交易
     * @param txHash
     * @param fee
     * @param accountUnspentList
     * @param withdrawalList
     */
    void addWithdrawalTransaction(String txHash, BigInteger fee, List<AccountUnspent> accountUnspentList, List<Withdrawal> withdrawalList);
	
}
