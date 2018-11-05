package com.sharingif.blockchain.crypto.api.key.entity;

/**
 * 生成addressIndex请求
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/4 下午2:28
 */
public class BIP44AddressIndexReq {

    /**
     * change扩展密钥id
     */
    private String changeExtendedKeyId;
    /**
     * change扩展密钥密码
     */
    private String changeExtendedKeyPassword;
    /**
     * 密码
     */
    private String password;

    public String getChangeExtendedKeyId() {
        return changeExtendedKeyId;
    }

    public void setChangeExtendedKeyId(String changeExtendedKeyId) {
        this.changeExtendedKeyId = changeExtendedKeyId;
    }

    public String getChangeExtendedKeyPassword() {
        return changeExtendedKeyPassword;
    }

    public void setChangeExtendedKeyPassword(String changeExtendedKeyPassword) {
        this.changeExtendedKeyPassword = changeExtendedKeyPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BIP44AddressIndexReq{");
        sb.append("changeExtendedKeyId='").append(changeExtendedKeyId).append('\'');
        sb.append(", changeExtendedKeyPassword='").append(changeExtendedKeyPassword).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
