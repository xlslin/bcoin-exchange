package com.sharingif.blockchain.bitcoin.withdrawal.service;


import com.sharingif.blockchain.bitcoin.account.model.entity.AccountUnspent;
import com.sharingif.blockchain.bitcoin.withdrawal.model.entity.WithdrawalVin;
import com.sharingif.cube.support.service.base.IBaseService;

import java.util.List;


public interface WithdrawalVinService extends IBaseService<WithdrawalVin, java.lang.String> {

    /**
     * 添加vin
     * @param accountUnspentList
     * @param accountUnspentList
     */
    void addWithdrawalVin(String txHash, List<AccountUnspent> accountUnspentList);

    /**
     * 根据交易hash查询
     * @param txHash
     * @return
     */
    List<WithdrawalVin> getByTxHash(String txHash);
	
}
