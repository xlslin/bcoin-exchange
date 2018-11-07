package com.sharingif.blockchain.account.service;


import com.sharingif.blockchain.account.model.entity.Account;
import com.sharingif.cube.support.service.base.IBaseService;


public interface AccountService extends IBaseService<Account, java.lang.String> {

    /**
     * 初始化账户
     * @param coinType
     * @param address
     */
    void initAccount(String coinType, String address);

}
