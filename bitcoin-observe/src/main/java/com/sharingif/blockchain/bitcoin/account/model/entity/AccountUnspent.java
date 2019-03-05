package com.sharingif.blockchain.bitcoin.account.model.entity;

import com.sharingif.blockchain.api.bitcoin.entity.SignMessageUnspentReq;
import com.sharingif.blockchain.api.bitcoin.entity.SignMessageVinReq;
import org.bitcoincore.api.wallet.entity.Unspent;

import java.util.ArrayList;
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

    public SignMessageVinReq getSignMessageVinReq() {

        SignMessageVinReq signMessageVinReq = new SignMessageVinReq();

        signMessageVinReq.setFromAddress(account.getAddress());

        List<SignMessageUnspentReq> signMessageUnspentReqList =  new ArrayList<SignMessageUnspentReq>(unspentList.size());
        for(Unspent unspent : unspentList) {
            SignMessageUnspentReq signMessageUnspentReq = new SignMessageUnspentReq();
            signMessageUnspentReq.setTxId(unspent.getTxId());
            signMessageUnspentReq.setVout(unspent.getvOut());
            signMessageUnspentReq.setScriptPubKey(unspent.getScriptPubKey());
            signMessageUnspentReq.setAmount(unspent.getAmount().toBigInteger());

            signMessageUnspentReqList.add(signMessageUnspentReq);
        }

        signMessageVinReq.setUnspentList(signMessageUnspentReqList);

        return signMessageVinReq;
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
