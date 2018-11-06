package com.sharingif.blockchain.crypto.service.impl;

import com.sharingif.blockchain.api.crypto.entity.BIP44AddressIndexReq;
import com.sharingif.blockchain.api.crypto.entity.BIP44AddressIndexRsp;
import com.sharingif.blockchain.api.crypto.entity.BIP44ChangeReq;
import com.sharingif.blockchain.api.crypto.entity.BIP44ChangeRsp;
import com.sharingif.blockchain.crypto.service.BIP44Service;
import com.sharingif.blockchain.crypto.service.ExtendedKeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private ExtendedKeyService extendedKeyService;

    @Resource
    public void setExtendedKeyService(ExtendedKeyService extendedKeyService) {
        this.extendedKeyService = extendedKeyService;
    }

    @Override
    public BIP44ChangeRsp change(BIP44ChangeReq req) {
        return extendedKeyService.change(req);
    }

    @Override
    public BIP44AddressIndexRsp addressIndex(BIP44AddressIndexReq req) {
        return null;
    }
}