package com.sharingif.blockchain.bitcoin.account.model.entity;

import org.bitcoincore.api.wallet.entity.Unspent;

import java.util.List;

public class AccountUnspent {

    private Account account;
    private List<Unspent> unspentList;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Unspent> getUnspentList() {
        return unspentList;
    }

    public void setUnspentList(List<Unspent> unspentList) {
        this.unspentList = unspentList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccountUnspent{");
        sb.append("account=").append(account);
        sb.append(", unspentList=").append(unspentList);
        sb.append('}');
        return sb.toString();
    }
}
