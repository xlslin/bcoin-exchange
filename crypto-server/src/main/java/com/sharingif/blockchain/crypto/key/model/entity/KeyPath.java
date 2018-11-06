package com.sharingif.blockchain.crypto.key.model.entity;

import com.sharingif.blockchain.crypto.api.key.entity.BIP44ChangeReq;
import com.sharingif.blockchain.crypto.api.key.entity.BIP44GenerateReq;

/**
 * KeyPath
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/3 下午2:32
 */
public class KeyPath {

    private String parentPath;
    private String path;
    private String bitcoinjParentPath;
    private String bitcoinjPath;

    public KeyPath(String path) {
        this.path = path;
    }

    public KeyPath(String extendedKeyPath, int addressIndex) {
        this(
                new StringBuilder(extendedKeyPath)
                        .append("/")
                        .append(addressIndex)
                        .toString()
        );

        setAllPath(
                getCoinType()
                ,getAccount()
                ,getChange()
                ,getAddressIndex()
        );
    }

    public KeyPath(BIP44ChangeReq req) {
        setAllPath(
                req.getCoinType()
                ,req.getAccount()
                ,req.getChange()
                ,null
        );
    }

    public KeyPath(BIP44GenerateReq req) {
        setAllPath(
                req.getCoinType()
                ,req.getAccount()
                ,req.getChange()
                ,req.getAddressIndex()
        );
    }

    protected void setAllPath(Integer coinType, Integer account, Integer change, Integer addressIndex) {
        StringBuilder stringBuilder = new StringBuilder("m/44'");
        StringBuilder bitcoinjPathStringBuilder = new StringBuilder("M/44H");
        if(coinType != null) {
            parentPath = stringBuilder.toString();
            bitcoinjParentPath = bitcoinjPathStringBuilder.toString();
            stringBuilder.append("/").append(coinType).append("'");
            bitcoinjPathStringBuilder.append("/").append(coinType).append("H");
        }
        if(account != null) {
            parentPath = stringBuilder.toString();
            bitcoinjParentPath = bitcoinjPathStringBuilder.toString();
            stringBuilder.append("/").append(account).append("'");
            bitcoinjPathStringBuilder.append("/").append(account).append("H");
        }
        if(change != null) {
            parentPath = stringBuilder.toString();
            bitcoinjParentPath = bitcoinjPathStringBuilder.toString();
            stringBuilder.append("/").append(change);
            bitcoinjPathStringBuilder.append("/").append(change);
        }
        if(addressIndex != null) {
            parentPath = stringBuilder.toString();
            bitcoinjParentPath = bitcoinjPathStringBuilder.toString();
            stringBuilder.append("/").append(addressIndex);
            bitcoinjPathStringBuilder.append("/").append(addressIndex);
        }

        path = stringBuilder.toString();
        bitcoinjPath = bitcoinjPathStringBuilder.toString();
    }

    public String getParentPath() {
        return parentPath;
    }

    public String getPath() {
        return path;
    }

    public String getBitcoinjParentPath() {
        return bitcoinjParentPath;
    }

    public String getBitcoinjPath() {
        return bitcoinjPath;
    }

    public Integer getCoinType() {
        String[] pathArray = this.path.split("/");

        return new Integer(pathArray[2].replace("'",""));
    }

    public Integer getAccount() {
        String[] pathArray = this.path.split("/");

        return new Integer(pathArray[3].replace("'",""));
    }

    public Integer getChange() {
        String[] pathArray = this.path.split("/");

        return new Integer(pathArray[4]);
    }

    public Integer getAddressIndex() {
        String[] pathArray = this.path.split("/");

        return new Integer(pathArray[5]);
    }
}
