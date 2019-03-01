package com.sharingif.blockchain.bitcoin.app.constants;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Constants
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/4/24 下午2:47
 */
public class Constants {

    /**
     * btc单位
     */
    public static final BigInteger BTC_UNIT = new BigInteger("100000000");
    /**
     * btc转账手续费
     */
    public static final BigInteger BTC_COIN_TRANSFOR_FEE = new BigInteger("30000");
    /**
     * USDT收款人BTC数量
     */
    public static final BigInteger USDT_RECIPIENT_BTC_AMOUNT = new BigInteger("546");

}
