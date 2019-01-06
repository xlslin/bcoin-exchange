package com.sharingif.blockchain.ether.block.dao.impl;


import com.sharingif.blockchain.ether.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.ether.block.dao.TransactionBusinessAccountDAO;
import com.sharingif.blockchain.ether.block.model.entity.TransactionBusinessAccount;
import org.springframework.stereotype.Repository;


@Repository
public class TransactionBusinessAccountDAOImpl extends BaseDAOImpl<TransactionBusinessAccount, String> implements TransactionBusinessAccountDAO {

    @Override
    public TransactionBusinessAccount queryByAddressCoinTypeForUpdate(String address, String coinType) {
        TransactionBusinessAccount transactionBusinessAccount = new TransactionBusinessAccount();
        transactionBusinessAccount.setAddress(address);
        transactionBusinessAccount.setCoinType(coinType);

        return selectOne("queryByAddressCoinTypeForUpdate", transactionBusinessAccount);
    }
}
