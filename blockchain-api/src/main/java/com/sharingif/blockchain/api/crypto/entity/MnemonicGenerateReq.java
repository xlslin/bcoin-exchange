package com.sharingif.blockchain.api.crypto.entity;

import java.util.Locale;

/**
 * 生成助记词请求
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/2 下午4:35
 */
public class MnemonicGenerateReq {

    /**
     * 名称
     */
    private String name;
    /**
     * 语言
     */
    private Locale locale;
    /**
     * 助记词
     */
    private String mnemonic;
    /**
     * 助记词长度
     */
    private int length;
    /**
     * 密码
     */
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MnemonicGenerateReq{");
        sb.append("name='").append(name).append('\'');
        sb.append(", locale=").append(locale);
        sb.append(", mnemonic='").append(mnemonic).append('\'');
        sb.append(", length=").append(length);
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
