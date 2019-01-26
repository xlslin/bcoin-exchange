package com.sharingif.blockchain.app.constants;

/**
 * 错误码
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/4/12 下午11:58
 */
public class ErrorConstants {

    /**
     * 币种不支持
     */
    public static final String COIN_TYPE_DOES_NOT_EXIST="001000";
    /**
     * B44IP币种不支持
     */
    public static final String BIP44_COIN_TYPE_DOES_NOT_EXIST="001001";

    /**
     * 未设置bitcoin找零ExtendedKey
     */
    public static final String NOT_SET_UP_BTC_CHANGE_EXTENDEDKEY = "001002";

    /**
     * 未生成bitcoin找零地址
     */
    public static final String NOT_GENERATED_BTC_CHANGE_ADDRESS = "001003";

}
