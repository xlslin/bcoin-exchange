package com.sharingif.blockchain.ether.deposit.service;


import com.sharingif.blockchain.ether.deposit.model.entity.Deposit;
import com.sharingif.cube.support.service.base.IBaseService;


public interface DepositService extends IBaseService<Deposit, java.lang.String> {

    /**
     * 添加未处理充值
     * @param deposit
     */
    void addUntreatedDeposit(Deposit deposit);

}
