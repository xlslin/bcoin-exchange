package com.sharingif.blockchain.ether.withdrawal.service;


import com.sharingif.blockchain.ether.withdrawal.model.entity.Withdrawal;
import com.sharingif.cube.support.service.base.IBaseService;


public interface WithdrawalService extends IBaseService<Withdrawal, java.lang.String> {

    /**
     * 添加未处理提现
     * @param withdrawal
     */
    void addUntreated(Withdrawal withdrawal);
	
}
