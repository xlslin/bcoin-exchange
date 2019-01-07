package com.sharingif.blockchain.ether.account.service;


import com.sharingif.blockchain.ether.account.model.entity.Account;
import com.sharingif.cube.support.service.base.IBaseService;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;


public interface AccountService extends IBaseService<Account, java.lang.String> {

    /**
     * 根据地址、币种查询余额
     * @param address
     * @param coinType
     * @return
     */
    BigInteger getBalance(String address, String coinType);

    /**
     * 增加余额
     * @param address
     * @param coinType
     * @param balance
     * @param accountFrom
     * @param accountTo
     * @param type
     * @param txId
     * @param transTime
     */
    void addBalance(String address, String coinType, BigInteger balance, String accountFrom, String accountTo, String type, String txId, Date transTime);

    /**
     * 减少余额
     * @param address
     * @param coinType
     * @param balance
     * @param accountFrom
     * @param accountTo
     * @param type
     * @param txId
     * @param transTime
     */
    void subtractBalance(String address, String coinType, BigInteger balance, String accountFrom, String accountTo, String type, String txId, Date transTime);

    /**
     * 冻结余额
     * @param address
     * @param coinType
     * @param balance
     * @param accountFrom
     * @param accountTo
     * @param type
     * @param txId
     * @param transTime
     */
    void frozenBalance(String address, String coinType, BigInteger balance, String accountFrom, String accountTo, String type, String txId, Date transTime);

    /**
     * 解冻余额
     * @param address
     * @param coinType
     * @param balance
     * @param accountFrom
     * @param accountTo
     * @param type
     * @param txId
     * @param transTime
     */
    void unFrozenBalance(String address, String coinType, BigInteger balance, String accountFrom, String accountTo, String type, String txId, Date transTime);

    /**
     * 减少冻结余额
     * @param address
     * @param coinType
     * @param balance
     * @param accountFrom
     * @param accountTo
     * @param txId
     * @param transTime
     */
    void subtractFrozenBalance(String address, String coinType, BigInteger balance, String accountFrom, String accountTo, String txId, Date transTime);

    /**
     * 根据地址、币种、大于等于余额查询
     * @param address
     * @param coinType
     * @param balance
     * @return
     */
    List<Account> getAccount(String address, String coinType, BigInteger balance);
	
}
