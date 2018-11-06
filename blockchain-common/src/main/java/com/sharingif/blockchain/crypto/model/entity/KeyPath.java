package com.sharingif.blockchain.crypto.model.entity;


/**
 * KeyPath
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/3 下午2:32
 */
public class KeyPath {

    private String path;

    public KeyPath(String path) {
        this.path = path;
    }

    public KeyPath(Integer coinType, Integer account, Integer change, Integer addressIndex) {
        StringBuilder stringBuilder = new StringBuilder("m/44'");
        if(coinType != null) {
            stringBuilder.append("/").append(coinType).append("'");
        }
        if(account != null) {
            stringBuilder.append("/").append(account).append("'");
        }
        if(change != null) {
            stringBuilder.append("/").append(change);
        }
        if(addressIndex != null) {
            stringBuilder.append("/").append(addressIndex);
        }

        path = stringBuilder.toString();
    }


    public String getPath() {
        return path;
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

}
