package org.bitcoincore.api.wallet.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Unspent {

    @JsonProperty("txid")
    private String txId;
    @JsonProperty("vout")
    private Integer vOut;
    private String address;
    private String account;
    private String scriptPubKey;
    private String redeemScript;
    private BigDecimal amount;
    private Integer confirmations;
    private Boolean spendable;

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public Integer getvOut() {
        return vOut;
    }

    public void setvOut(Integer vOut) {
        this.vOut = vOut;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getScriptPubKey() {
        return scriptPubKey;
    }

    public void setScriptPubKey(String scriptPubKey) {
        this.scriptPubKey = scriptPubKey;
    }

    public String getRedeemScript() {
        return redeemScript;
    }

    public void setRedeemScript(String redeemScript) {
        this.redeemScript = redeemScript;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public Boolean getSpendable() {
        return spendable;
    }

    public void setSpendable(Boolean spendable) {
        this.spendable = spendable;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Unspent{");
        sb.append("txId='").append(txId).append('\'');
        sb.append(", vOut=").append(vOut);
        sb.append(", address='").append(address).append('\'');
        sb.append(", account='").append(account).append('\'');
        sb.append(", scriptPubKey='").append(scriptPubKey).append('\'');
        sb.append(", redeemScript='").append(redeemScript).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", confirmations=").append(confirmations);
        sb.append(", spendable=").append(spendable);
        sb.append('}');
        return sb.toString();
    }
}
