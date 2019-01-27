package com.sharingif.blockchain.bitcoin.withdrawal.service;


import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.Withdrawal;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalVout;
import com.sharingif.cube.support.service.base.IBaseService;

import java.util.List;


public interface WithdrawalVoutService extends IBaseService<WithdrawalVout, java.lang.String> {

    /**
     * 添加WithdrawalVout
     * @param txHash
     * @param withdrawalList
     */
    void addWithdrawalVout(String txHash, List<Withdrawal> withdrawalList);

    /**
     * 根据交易hash查询
     * @param txHash
     * @return
     */
    List<WithdrawalVout> getWithdrawalVoutByTxHash(String txHash);
	
}
