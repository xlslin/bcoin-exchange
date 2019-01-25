package com.sharingif.blockchain.crypto.api.bitcoin.entity;

import java.math.BigInteger;

/**
 * eth转账请求
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/26 下午1:00
 */
public class SignMessageReq {

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SignMessageReq{");
        sb.append("fee=").append(fee);
        sb.append(", vin=").append(vin);
        sb.append(", vout=").append(vout);
        sb.append('}');
        return sb.toString();
    }
}
