package com.sharingif.blockchain.crypto.key.service;

import com.sharingif.blockchain.crypto.key.model.entity.SecretKey;
import org.bitcoinj.crypto.DeterministicKey;

/**
 * 生成index secret key
 */
public interface BIP44IndexService {

    /**
     * 创建SecretKey并保存
     * @param addressIndexDeterministicKey
     * @param extendedKeyPath
     * @param currentIndexNumber
     * @param password
     * @return
     */
    SecretKey buildSecretKey(DeterministicKey addressIndexDeterministicKey, String extendedKeyPath, int currentIndexNumber, String password);

    /**
     * 返回BIP币种
     * @return
     */
    int getCoinType();

}
