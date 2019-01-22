package org.bitcoincore.api.rawtransactions.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Vin {

    @JsonProperty("txid")
    private String txId;
    @JsonProperty("vout")
    private Integer vOut;
    private ScriptSig scriptSig;
    private String coinbase;
    private List<String> txinwitness;
    private Long sequence;

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

    public ScriptSig getScriptSig() {
        return scriptSig;
    }

    public void setScriptSig(ScriptSig scriptSig) {
        this.scriptSig = scriptSig;
    }

    public String getCoinbase() {
        return coinbase;
    }

    public void setCoinbase(String coinbase) {
        this.coinbase = coinbase;
    }

    public List<String> getTxinwitness() {
        return txinwitness;
    }

    public void setTxinwitness(List<String> txinwitness) {
        this.txinwitness = txinwitness;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vin{");
        sb.append("txId='").append(txId).append('\'');
        sb.append(", vOut=").append(vOut);
        sb.append(", scriptSig=").append(scriptSig);
        sb.append(", coinbase='").append(coinbase).append('\'');
        sb.append(", txinwitness=").append(txinwitness);
        sb.append(", sequence=").append(sequence);
        sb.append('}');
        return sb.toString();
    }
}
