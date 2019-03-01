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
        this.nullData = nullData.replace("OP_RETURN ", "");
    }

    public String getOmni() {
        if(nullData == null)
            return null;

        return nullData.substring(0,8);
    }

    public String getVersion() {
        if(nullData == null)
            return null;

        return nullData.substring(8,12);
    }

    public String getType() {
        if(nullData == null)
            return null;

        return nullData.substring(12,16);
    }

    public String getProperty() {
        if(nullData == null)
            return null;

        return nullData.substring(16,24);
    }

    public String getAmount() {
        return nullData.substring(24);
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
