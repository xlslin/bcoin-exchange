package com.sharingif.blockchain.api.sys.entity;

public class CurrentChangeExtendedKeyReq {

    /**
     * change ExtendedKey id
     */
    private String changeExtendedKeyId;

    public String getChangeExtendedKeyId() {
        return changeExtendedKeyId;
    }

    public void setChangeExtendedKeyId(String changeExtendedKeyId) {
        this.changeExtendedKeyId = changeExtendedKeyId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CurrentChangeExtendedKeyReq{");
        sb.append("changeExtendedKeyId='").append(changeExtendedKeyId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
