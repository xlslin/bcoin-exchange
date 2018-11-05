package com.sharingif.blockchain.crypto.key.service.impl;

import com.sharingif.blockchain.crypto.app.components.Keystore;
import com.sharingif.blockchain.crypto.app.constants.CoinType;
import com.sharingif.blockchain.crypto.key.model.entity.SecretKey;
import com.sharingif.blockchain.crypto.key.service.BIP44IndexService;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.params.MainNetParams;
import org.springframework.stereotype.Service;
import org.web3j.crypto.ECKeyPair;

import javax.annotation.Resource;

/**
 * 生成BTC 主网 index secret key
 */
@Service
public class BIP44IndexBTCMainService implements BIP44IndexService {

    private Keystore keystore;

    @Resource
    public void setKeystore(Keystore keystore) {
        this.keystore = keystore;
    }

    @Override
    public SecretKey buildSecretKey(DeterministicKey addressIndexDeterministicKey, String extendedKeyPath, int currentIndexNumber, String password) {
        SecretKey secretKey = new SecretKey();

        ECKeyPair ecKeyPair = ECKeyPair.create(addressIndexDeterministicKey.getPrivKey());
        String filePath = keystore.persistenceSecretKey(extendedKeyPath, currentIndexNumber, password, ecKeyPair);

        secretKey.setAddress(addressIndexDeterministicKey.toAddress(MainNetParams.get()).toBase58());
        secretKey.setFilePath(filePath);

        return secretKey;
    }

    @Override
    public int getCoinType() {
        return CoinType.BTC_MAIN.getBipCoinType();
    }
}
