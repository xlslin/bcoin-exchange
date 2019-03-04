package com.sharingif.blockchain.api.bitcoin.entity;

import java.math.BigInteger;
import java.util.List;

public class OmniSimpleSendSignMessageReq {

    /**
     * 手续费
     */
    private BigInteger fee;

    /**
     * vin
     */
    private SignMessageVinReq vin;

    /**
     * vout
     */
    private SignMessageVoutReq vout;

    /**
     * op_return
     */
    private String opReturn;
    /**
     * change
     */
    private String change;

    public BigInteger getFee() {
        return fee;
    }

    public void setFee(BigInteger fee) {
        this.fee = fee;
    }

    public SignMessageVinReq getVin() {
        return vin;
    }

    public void setVin(SignMessageVinReq vin) {
        this.vin = vin;
    }

    public SignMessageVoutReq getVout() {
        return vout;
    }

    public void setVout(SignMessageVoutReq vout) {
        this.vout = vout;
    }

    public String getOpReturn() {
        return opReturn;
    }

    public void setOpReturn(String opReturn) {
        this.opReturn = opReturn;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OmniSimpleSendSignMessageReq{");
        sb.append("fee=").append(fee);
        sb.append(", vin=").append(vin);
        sb.append(", vout=").append(vout);
        sb.append(", opReturn='").append(opReturn).append('\'');
        sb.append(", change='").append(change).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
