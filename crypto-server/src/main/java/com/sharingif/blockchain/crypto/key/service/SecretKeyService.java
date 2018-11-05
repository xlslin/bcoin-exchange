package com.sharingif.blockchain.crypto.key.service;


import com.sharingif.blockchain.crypto.api.key.entity.BIP44AddressIndexReq;
import com.sharingif.blockchain.crypto.api.key.entity.BIP44AddressIndexRsp;
import com.sharingif.blockchain.crypto.key.model.entity.SecretKey;
import com.sharingif.cube.support.service.base.IBaseService;


public interface SecretKeyService extends IBaseService<SecretKey, java.lang.String> {

    /**
     * 生成addressIndex key
     * @param req
     * @return
     */
    BIP44AddressIndexRsp addressIndex(BIP44AddressIndexReq req);

}
