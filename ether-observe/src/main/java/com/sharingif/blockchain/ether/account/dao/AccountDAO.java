package com.sharingif.blockchain.ether.account.dao;


import com.sharingif.blockchain.ether.account.model.entity.Account;
import com.sharingif.blockchain.ether.app.dao.BaseDAO;

import java.math.BigInteger;


public interface AccountDAO extends BaseDAO<Account, String> {

    /**
     * 查询并锁定
     * @param account
     * @return
     */
    Account queryForUpdate(Account account);

    /**
     * 增加总入账、增加余额
     * @param address
     * @param coinType
     * @param balance
     * @return
     */
    int updateAddTotalInBalanceByAddressCoinType(String address, String coinType, BigInteger balance);

    /**
     * 增加总出账、减少余额
     * @param address
     * @param coinType
     * @param balance
     * @return
     */
    int updateSubTotalOutBalanceByAddressCoinType(String address, String coinType, BigInteger balance);

    /**
     * 减去余额、增加冻结金额
     * @param address
     * @param coinType
     * @param balance
     * @return
     */
    int updateSubBalanceFrozenAmountByAddressCoinType(String address, String coinType, BigInteger balance);

    /**
     * 增加余额、减去冻结金额
     * @param address
     * @param coinType
     * @param balance
     * @return
     */
    int updateAddBalanceFrozenAmountByAddressCoinType(String address, String coinType, BigInteger balance);

    /**
     * 减去总出账、减去冻结金额
     * @param address
     * @param coinType
     * @param frozenAmount
     * @return
     */
    int updateSubFrozenAmountTotalOutByAddressCoinType(String address, String coinType, BigInteger frozenAmount);

}
