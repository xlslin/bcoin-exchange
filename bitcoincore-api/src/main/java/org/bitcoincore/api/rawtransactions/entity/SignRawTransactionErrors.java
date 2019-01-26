package org.bitcoincore.api.rawtransactions.entity;

import java.math.BigInteger;
import java.util.List;

public class SignRawTransactionErrors {

    private String txid;
    private Integer vout;
    private List<String> witness;
    private String scriptSig;
    private BigInteger sequence;
    private String error;

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public Integer getVout() {
        return vout;
    }

    public void setVout(Integer vout) {
        this.vout = vout;
    }

    public List<String> getWitness() {
        return witness;
    }

    public void setWitness(List<String> witness) {
        this.witness = witness;
    }

    public String getScriptSig() {
        return scriptSig;
    }

    public void setScriptSig(String scriptSig) {
        this.scriptSig = scriptSig;
    }

    public BigInteger getSequence() {
        return sequence;
    }

    public void setSequence(BigInteger sequence) {
        this.sequence = sequence;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SignRawTransactionErrors{");
        sb.append("txid='").append(txid).append('\'');
        sb.append(", vout=").append(vout);
        sb.append(", witness=").append(witness);
        sb.append(", scriptSig='").append(scriptSig).append('\'');
        sb.append(", sequence=").append(sequence);
        sb.append(", error='").append(error).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
