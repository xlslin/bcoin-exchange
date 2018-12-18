package com.sharingif.blockchain.crypto.service.impl;

import com.sharingif.blockchain.account.service.AddressListenerService;
import com.sharingif.blockchain.account.service.BitCoinService;
import com.sharingif.blockchain.api.crypto.entity.BIP44AddressIndexReq;
import com.sharingif.blockchain.api.crypto.entity.BIP44AddressIndexRsp;
import com.sharingif.blockchain.crypto.api.key.service.BIP44ApiService;
import com.sharingif.blockchain.crypto.dao.SecretKeyDAO;
import com.sharingif.blockchain.crypto.model.entity.Bip44KeyPath;
import com.sharingif.blockchain.crypto.model.entity.ExtendedKey;
import com.sharingif.blockchain.crypto.model.entity.SecretKey;
import com.sharingif.blockchain.crypto.service.ExtendedKeyService;
import com.sharingif.blockchain.crypto.service.SecretKeyService;
import com.sharingif.cube.core.util.StringUtils;
import com.sharingif.cube.security.confidentiality.encrypt.TextEncryptor;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SecretKeyServiceImpl
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/25 下午7:28
 */
@Service
public class SecretKeyServiceImpl extends BaseServiceImpl<SecretKey, String> implements SecretKeyService {

    private SecretKeyDAO secretKeyDAO;

    private ExtendedKeyService extendedKeyService;
    private TextEncryptor passwordTextEncryptor;
    private BIP44ApiService bip44ApiService;
    private AddressListenerService addressListenerService;
    private BitCoinService bitCoinService;

    @Resource
    public void setSecretKeyDAO(SecretKeyDAO secretKeyDAO) {
        super.setBaseDAO(secretKeyDAO);
        this.secretKeyDAO = secretKeyDAO;
    }

    @Resource
    public void setExtendedKeyService(ExtendedKeyService extendedKeyService) {
        this.extendedKeyService = extendedKeyService;
    }
    @Resource
    public void setPasswordTextEncryptor(TextEncryptor passwordTextEncryptor) {
        this.passwordTextEncryptor = passwordTextEncryptor;
    }
    @Resource
    public void setBip44ApiService(BIP44ApiService bip44ApiService) {
        this.bip44ApiService = bip44ApiService;
    }
    @Resource
    public void setAddressListenerService(AddressListenerService addressListenerService) {
        this.addressListenerService = addressListenerService;
    }
    @Resource
    public void setBitCoinService(BitCoinService bitCoinService) {
        this.bitCoinService = bitCoinService;
    }

    @Override
    public BIP44AddressIndexRsp addressIndex(BIP44AddressIndexReq req) {
        // 获取ExtendedKey信息
        ExtendedKey extendedKey = null;
        String changeExtendedKeyId = req.getChangeExtendedKeyId();
        if(StringUtils.isTrimEmpty(changeExtendedKeyId)) {
            extendedKey = extendedKeyService.getExtendedKey(req.getCoinType());
        } else {
            extendedKey = extendedKeyService.getById(changeExtendedKeyId);
        }

        String encrypt = extendedKey.getPassword();
        String extendedKeyPassword = passwordTextEncryptor.decrypt(encrypt);

        // 调用crypto-api生成key
        com.sharingif.blockchain.crypto.api.key.entity.BIP44AddressIndexReq apiReq = new com.sharingif.blockchain.crypto.api.key.entity.BIP44AddressIndexReq();
        apiReq.setChangeExtendedKeyId(extendedKey.getId());
        apiReq.setChangeExtendedKeyPassword(extendedKeyPassword);
        apiReq.setPassword(extendedKeyPassword);

        com.sharingif.blockchain.crypto.api.key.entity.BIP44AddressIndexRsp apiRsp = bip44ApiService.addressIndex(apiReq);

        // 保存key信息
        SecretKey secretKey = new SecretKey();
        secretKey.setExtendedKeyId(extendedKey.getId());
        secretKey.setAddress(apiRsp.getAddress());
        secretKey.setKeyPath(apiRsp.getKeyPath());
        secretKey.setPassword(encrypt);
        secretKeyDAO.insert(secretKey);

        // 返回值
        BIP44AddressIndexRsp rsp = new BIP44AddressIndexRsp();
        rsp.setId(secretKey.getId());
        rsp.setAddress(secretKey.getAddress());

        // 注册地址监听
        if(req.isWatch()) {
            addressListenerService.add(secretKey.getAddress());
        }

        return rsp;
    }

    @Override
    public String getBlockType(String address) {
        SecretKey secretKey = new SecretKey();
        secretKey.setAddress(address);

        secretKey = secretKeyDAO.query(secretKey);
        String keyPath = secretKey.getKeyPath();
        Bip44KeyPath bip44KeyPath = new Bip44KeyPath(keyPath);

        return bitCoinService.getBlockTypeByBip44CoinType(bip44KeyPath.getCoinType());
    }

}
