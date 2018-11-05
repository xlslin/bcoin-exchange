package com.sharingif.blockchain.crypto.key.service.impl;

import com.sharingif.blockchain.crypto.app.components.Keystore;
import com.sharingif.blockchain.crypto.app.constants.CoinType;
import com.sharingif.blockchain.crypto.key.model.entity.SecretKey;
import com.sharingif.blockchain.crypto.key.service.BIP44IndexService;
import org.bitcoinj.crypto.DeterministicKey;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;

import javax.annotation.Resource;

/**
 * 生成ETH index secret key
 */
@Service
public class BIP44IndexETHService implements BIP44IndexService {

    private Keystore keystore;

    @Resource
    public void setKeystore(Keystore keystore) {
        this.keystore = keystore;
    }

    @Override
    public SecretKey buildSecretKey(DeterministicKey addressIndexDeterministicKey, String extendedKeyPath, int currentIndexNumber, String password) {
        SecretKey secretKey = new SecretKey();

        ECKeyPair ecKeyPair = ECKeyPair.create(addressIndexDeterministicKey.getPrivKey());
        Credentials credentials = Credentials.create(ecKeyPair);
        String filePath = keystore.persistenceSecretKey(extendedKeyPath, currentIndexNumber, password, ecKeyPair);

        secretKey.setAddress(credentials.getAddress());
        secretKey.setFilePath(filePath);

        return null;
    }

    @Override
    public int getCoinType() {
        return CoinType.ETH.getBipCoinType();
    }
}
