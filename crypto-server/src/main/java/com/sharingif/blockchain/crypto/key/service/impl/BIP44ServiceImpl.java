package com.sharingif.blockchain.crypto.key.service.impl;

import com.sharingif.blockchain.crypto.api.key.entity.BIP44AddressIndexReq;
import com.sharingif.blockchain.crypto.api.key.entity.BIP44AddressIndexRsp;
import com.sharingif.blockchain.crypto.api.key.entity.BIP44ChangeReq;
import com.sharingif.blockchain.crypto.api.key.entity.BIP44ChangeRsp;
import com.sharingif.blockchain.crypto.key.service.BIP44Service;
import com.sharingif.blockchain.crypto.key.service.ExtendedKeyService;
import com.sharingif.blockchain.crypto.key.service.SecretKeyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * KeyServiceImpl
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/3 下午12:38
 */
@Service("bip44Service")
public class BIP44ServiceImpl implements BIP44Service {

    private ExtendedKeyService extendedKeyService;
    private SecretKeyService secretKeyService;

    @Resource
    public void setExtendedKeyService(ExtendedKeyService extendedKeyService) {
        this.extendedKeyService = extendedKeyService;
    }
    @Resource
    public void setSecretKeyService(SecretKeyService secretKeyService) {
        this.secretKeyService = secretKeyService;
    }

    @Override
    public BIP44ChangeRsp change(BIP44ChangeReq req) {
        return extendedKeyService.change(req);
    }

    @Override
    public BIP44AddressIndexRsp addressIndex(BIP44AddressIndexReq req) {
        return secretKeyService.addressIndex(req);
    }

}
