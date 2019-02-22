package com.sharingif.blockchain.bitcoin.account.dao;


import com.sharingif.blockchain.bitcoin.account.model.entity.Account;
import com.sharingif.blockchain.bitcoin.account.model.entity.SubAccount;
import com.sharingif.blockchain.bitcoin.app.dao.BaseDAO;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;

import java.math.BigInteger;


public interface AccountDAO extends BaseDAO<Account, String> {

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

    /**
     * 根据条件余额升序查询
     * @param paginationCondition
     * @return
     */
    PaginationRepertory<Account> queryPaginationListOrderByBalanceAsc(PaginationCondition<Account> paginationCondition);

    /**
     * 根据2个币种、大于等于余额查询
     * @param paginationCondition
     * @return
     */
    PaginationRepertory<Account> queryPaginationListByCoinTypeSubCoinTypeBalanceSubBalance(PaginationCondition<SubAccount> paginationCondition);

}
