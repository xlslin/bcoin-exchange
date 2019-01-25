package com.sharingif.blockchain.bitcoin.account.dao.impl;


import com.sharingif.blockchain.bitcoin.account.dao.AccountDAO;
import com.sharingif.blockchain.bitcoin.account.model.entity.Account;
import com.sharingif.blockchain.bitcoin.app.dao.impl.BaseDAOImpl;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;


@Repository
public class AccountDAOImpl extends BaseDAOImpl<Account, String> implements AccountDAO {

    @Override
    public int updateAddTotalInBalanceByAddressCoinType(String address, String coinType, BigInteger balance) {
        Account account = new Account();
        account.setAddress(address);
        account.setCoinType(coinType);
        account.setBalance(balance);

        return update("updateAddTotalInBalanceByAddressCoinType", account);
    }

    @Override
    public int updateSubTotalOutBalanceByAddressCoinType(String address, String coinType, BigInteger balance) {
        Account account = new Account();
        account.setAddress(address);
        account.setCoinType(coinType);
        account.setBalance(balance);

        return update("updateSubTotalOutBalanceByAddressCoinType", account);
    }

    @Override
    public int updateSubBalanceFrozenAmountByAddressCoinType(String address, String coinType, BigInteger balance) {
        Account account = new Account();
        account.setAddress(address);
        account.setCoinType(coinType);
        account.setBalance(balance);

        return update("updateSubBalanceFrozenAmountByAddressCoinType", account);
    }

    @Override
    public int updateAddBalanceFrozenAmountByAddressCoinType(String address, String coinType, BigInteger balance) {
        Account account = new Account();
        account.setAddress(address);
        account.setCoinType(coinType);
        account.setBalance(balance);

        return update("updateAddBalanceFrozenAmountByAddressCoinType", account);
    }

    @Override
    public int updateSubFrozenAmountTotalOutByAddressCoinType(String address, String coinType, BigInteger frozenAmount) {
        Account account = new Account();
        account.setAddress(address);
        account.setCoinType(coinType);
        account.setFrozenAmount(frozenAmount);

        return update("updateSubFrozenAmountTotalOutByAddressCoinType", account);
    }

    @Override
    public PaginationRepertory<Account> queryPaginationListOrderByBalanceAsc(PaginationCondition<Account> paginationCondition) {
        return queryPagination("queryPaginationListOrderByBalanceAsc", paginationCondition);
    }

}
