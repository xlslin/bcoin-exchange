package com.sharingif.blockchain.crypto.api.bitcoin.entity;

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
    private List<SignMessageVoutReq> voutList;

    /**
     * op_return
     */
    private String opReturn;

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

    public List<SignMessageVoutReq> getVoutList() {
        return voutList;
    }

    public void setVoutList(List<SignMessageVoutReq> voutList) {
        this.voutList = voutList;
    }

    public String getOpReturn() {
        return opReturn;
    }

    public void setOpReturn(String opReturn) {
        this.opReturn = opReturn;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OmniSimpleSendSignMessageReq{");
        sb.append("fee=").append(fee);
        sb.append(", vin=").append(vin);
        sb.append(", voutList=").append(voutList);
        sb.append(", opReturn='").append(opReturn).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
