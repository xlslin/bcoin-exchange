package com.sharingif.blockchain.crypto.btc.service.impl;

import com.sharingif.blockchain.crypto.app.constants.CoinType;
import com.sharingif.blockchain.crypto.btc.service.BtcService;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * BtcServiceImpl
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/8/14 下午2:29
 */
@Service
public class BtcServiceImpl implements BtcService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public NetworkParameters getNetworkParameters(int coinType) {
        if (coinType == CoinType.BTC_TEST.getBipCoinType()) {
            return TestNet3Params.get();
        }

        return MainNetParams.get();
    }


}
