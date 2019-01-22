package org.bitcoincore.api.rawtransactions.entity;

import java.util.List;

public class ScriptPubKey extends ScriptSig {

    private Integer reqSigs;
    private String type;
    private List<String> addresses;

    public Integer getReqSigs() {
        return reqSigs;
    }

    public void setReqSigs(Integer reqSigs) {
        this.reqSigs = reqSigs;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ScriptPubKey{");
        sb.append("reqSigs=").append(reqSigs);
        sb.append(", type='").append(type).append('\'');
        sb.append(", addresses=").append(addresses);
        sb.append('}');
        return sb.toString();
    }
}
