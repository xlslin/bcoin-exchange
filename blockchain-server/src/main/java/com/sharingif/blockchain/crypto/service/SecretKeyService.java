package com.sharingif.blockchain.crypto.service;


import com.sharingif.blockchain.api.crypto.entity.BIP44AddressIndexReq;
import com.sharingif.blockchain.api.crypto.entity.BIP44AddressIndexRsp;
import com.sharingif.blockchain.crypto.model.entity.SecretKey;
import com.sharingif.cube.support.service.base.IBaseService;

/**
 * SecretKeyService
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/25 下午7:28
 */
public interface SecretKeyService extends IBaseService<SecretKey, String> {

    /**
     * 生成addressIndex key
     * @param req
     * @return
     */
    BIP44AddressIndexRsp addressIndex(BIP44AddressIndexReq req);

    /**
     * 根据地址区块类型
     * @param address
     * @return
     */
    String getBlockType(String address);

}
