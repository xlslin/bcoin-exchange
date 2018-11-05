package com.sharingif.blockchain.crypto.key.service;


import com.sharingif.blockchain.crypto.api.key.entity.BIP44ChangeReq;
import com.sharingif.blockchain.crypto.api.key.entity.BIP44ChangeRsp;
import com.sharingif.blockchain.crypto.key.model.entity.ExtendedKey;
import com.sharingif.cube.support.service.base.IBaseService;


public interface ExtendedKeyService extends IBaseService<ExtendedKey, java.lang.String> {

    /**
     * 生成change ExtendedKey
     * @param req
     * @return
     */
    BIP44ChangeRsp change(BIP44ChangeReq req);

    /**
     * 自增CurrentIndexNumber
     * @param changeExtendedKeyId
     * @return
     */
    ExtendedKey incrementCurrentIndexNumber(String changeExtendedKeyId);

}
