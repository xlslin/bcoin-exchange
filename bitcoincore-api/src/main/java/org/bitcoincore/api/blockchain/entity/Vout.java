package org.bitcoincore.api.blockchain.entity;

import java.math.BigDecimal;

public class Vout {

    private BigDecimal value;
    private Integer n;
    private ScriptPubKey scriptPubKey;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public ScriptPubKey getScriptPubKey() {
        return scriptPubKey;
    }

    public void setScriptPubKey(ScriptPubKey scriptPubKey) {
        this.scriptPubKey = scriptPubKey;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vout{");
        sb.append("value=").append(value);
        sb.append(", n=").append(n);
        sb.append(", scriptPubKey=").append(scriptPubKey);
        sb.append('}');
        return sb.toString();
    }
}
