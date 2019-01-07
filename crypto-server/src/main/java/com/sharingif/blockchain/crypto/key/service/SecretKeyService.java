package com.sharingif.blockchain.crypto.key.service;


import com.sharingif.blockchain.crypto.api.key.entity.BIP44AddressIndexReq;
import com.sharingif.blockchain.crypto.api.key.entity.BIP44AddressIndexRsp;
import com.sharingif.blockchain.crypto.key.model.entity.SecretKey;
import com.sharingif.cube.support.service.base.IBaseService;
import org.web3j.crypto.Credentials;


public interface SecretKeyService extends IBaseService<SecretKey, java.lang.String> {

    /**
     * 生成addressIndex key
     * @param req
     * @return
     */
    BIP44AddressIndexRsp addressIndex(BIP44AddressIndexReq req);

    /**
     * 获取Credentials
     * @param secretKeyId
     * @param password
     * @return
     */
    Credentials getCredentials(String secretKeyId, String password);

    /**
     * 获取Credentials
     * @param secretKey
     * @param password
     * @return
     */
    Credentials getCredentials(SecretKey secretKey, String password);

}
