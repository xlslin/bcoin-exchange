package com.sharingif.blockchain.crypto.model.entity;


/**
 * KeyPath
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/3 下午2:32
 */
public class Bip44KeyPath {

    public static final String BIP44 = "m/44'";

    private String path;

    public Bip44KeyPath(String path) {
        this.path = path;
    }

    public Bip44KeyPath(Integer coinType, Integer account, Integer change, Integer addressIndex) {
        StringBuilder stringBuilder = new StringBuilder(BIP44);
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

    public String getCoinType() {
        String[] pathArray = this.path.split("/");

        return pathArray[2].replace("'","");
    }

    public String getAccount() {
        String[] pathArray = this.path.split("/");

        return pathArray[3].replace("'","");
    }

    public String getChange() {
        String[] pathArray = this.path.split("/");

        return pathArray[4];
    }

}
