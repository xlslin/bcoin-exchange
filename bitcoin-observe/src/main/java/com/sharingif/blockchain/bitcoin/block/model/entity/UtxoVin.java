package com.sharingif.blockchain.bitcoin.block.model.entity;

import org.bitcoincore.api.rawtransactions.entity.Vin;
import org.bitcoincore.api.rawtransactions.entity.Vout;

public class UtxoVin {

    private Vin vin;
    private Vout vout;

    public Vin getVin() {
        return vin;
    }

    public void setVin(Vin vin) {
        this.vin = vin;
    }

    public Vout getVout() {
        return vout;
    }

    public void setVout(Vout vout) {
        this.vout = vout;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UtxoVin{");
        sb.append("vin=").append(vin);
        sb.append(", vout=").append(vout);
        sb.append('}');
        return sb.toString();
    }
}
