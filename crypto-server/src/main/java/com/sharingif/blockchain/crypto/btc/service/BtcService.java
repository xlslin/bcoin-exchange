package com.sharingif.blockchain.crypto.btc.service;

import org.bitcoinj.core.NetworkParameters;

/**
 * BtcService
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/8/14 下午2:17
 */
public interface BtcService {

    /**
     * 根据币种获取btc网络类型
     * @param coinType
     * @return
     */
    NetworkParameters getNetworkParameters(int coinType);

}
