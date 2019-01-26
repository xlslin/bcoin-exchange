package com.sharingif.blockchain.crypto.service;

import com.sharingif.blockchain.api.crypto.entity.BIP44ChangeReq;
import com.sharingif.blockchain.api.crypto.entity.BIP44ChangeRsp;
import com.sharingif.blockchain.crypto.model.entity.ExtendedKey;
import com.sharingif.cube.support.service.base.IBaseService;

/**
 * TOExtendedKeyServiceDO
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/19 下午4:13
 */
public interface ExtendedKeyService extends IBaseService<ExtendedKey, String> {

    /**
     * 生成change ExtendedKey
     * @param req
     * @return
     */
    BIP44ChangeRsp change(BIP44ChangeReq req);

    /**
     * 根据币种查询ExtendedKey
     * @param coinType
     * @return
     */
    ExtendedKey getExtendedKey(String coinType);

    /**
     * 获取bitcoin找零ExtendedKey
     * @return
     */
    ExtendedKey getBitCoinChangeExtendedKey();

}
