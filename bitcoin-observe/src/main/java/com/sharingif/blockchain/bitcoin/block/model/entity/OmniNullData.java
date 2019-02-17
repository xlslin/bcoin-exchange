package com.sharingif.blockchain.bitcoin.block.model.entity;

public class OmniNullData {

    private int voutIndex;
    private String nullData;

    public int getVoutIndex() {
        return voutIndex;
    }

    public void setVoutIndex(int voutIndex) {
        this.voutIndex = voutIndex;
    }

    public String getNullData() {
        return nullData;
    }

    public void setNullData(String nullData) {
        this.nullData = nullData;
    }

    public String getProperty() {
        if(nullData == null)
            return null;

        return nullData;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OmniNullData{");
        sb.append("voutIndex=").append(voutIndex);
        sb.append(", nullData='").append(nullData).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
