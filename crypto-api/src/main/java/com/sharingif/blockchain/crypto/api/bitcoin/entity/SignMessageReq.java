package com.sharingif.blockchain.crypto.api.bitcoin.entity;

import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigInteger;
import java.util.List;

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
     * 找零地址
     */
    @NotEmpty
    private String changeAddress;
    /**
     * 手续费
     */
    private BigInteger fee;
    /**
     * vin
     */
    private List<SignMessageVinReq> vinList;
    /**
     * vout
     */
    private List<SignMessageVoutReq> voutList;

    public String getChangeAddress() {
        return changeAddress;
    }

    public void setChangeAddress(String changeAddress) {
        this.changeAddress = changeAddress;
    }

    public BigInteger getFee() {
        return fee;
    }

    public void setFee(BigInteger fee) {
        this.fee = fee;
    }

    public List<SignMessageVinReq> getVinList() {
        return vinList;
    }

    public void setVinList(List<SignMessageVinReq> vinList) {
        this.vinList = vinList;
    }

    public List<SignMessageVoutReq> getVoutList() {
        return voutList;
    }

    public void setVoutList(List<SignMessageVoutReq> voutList) {
        this.voutList = voutList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SignMessageReq{");
        sb.append("changeAddress='").append(changeAddress).append('\'');
        sb.append(", fee=").append(fee);
        sb.append(", vinList=").append(vinList);
        sb.append(", voutList=").append(voutList);
        sb.append('}');
        return sb.toString();
    }
}
