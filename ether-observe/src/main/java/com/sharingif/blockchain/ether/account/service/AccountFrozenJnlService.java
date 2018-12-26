package com.sharingif.blockchain.ether.account.service;


import com.sharingif.blockchain.ether.account.model.entity.AccountFrozenJnl;
import com.sharingif.cube.support.service.base.IBaseService;


public interface AccountFrozenJnlService extends IBaseService<AccountFrozenJnl, java.lang.String> {

    /**
     * 添加冻结流水
     * @param accountFrozenJnl
     */
    void addAccountFrozenJnl(AccountFrozenJnl accountFrozenJnl);

    /**
     * 添加解冻流水
     * @param accountFrozenJnl
     */
    void addAccountUnFrozenJnl(AccountFrozenJnl accountFrozenJnl);
	
}
