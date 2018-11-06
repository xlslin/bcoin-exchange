package com.sharingif.blockchain.crypto.service.impl;

import com.sharingif.blockchain.api.crypto.entity.BIP44ChangeReq;
import com.sharingif.blockchain.api.crypto.entity.BIP44ChangeRsp;
import com.sharingif.blockchain.crypto.api.key.service.BIP44ApiService;
import com.sharingif.blockchain.crypto.dao.ExtendedKeyDAO;
import com.sharingif.blockchain.crypto.model.entity.ExtendedKey;
import com.sharingif.blockchain.crypto.model.entity.KeyPath;
import com.sharingif.blockchain.crypto.model.entity.Mnemonic;
import com.sharingif.blockchain.crypto.service.ExtendedKeyService;
import com.sharingif.blockchain.crypto.service.MnemonicService;
import com.sharingif.cube.security.confidentiality.encrypt.TextEncryptor;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ExtendedKeyServiceImpl
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/19 下午4:14
 */
@Service
public class ExtendedKeyServiceImpl extends BaseServiceImpl<ExtendedKey, String> implements ExtendedKeyService {

    private ExtendedKeyDAO extendedKeyDAO;

    private MnemonicService mnemonicService;
    private TextEncryptor passwordTextEncryptor;
    private BIP44ApiService bip44ApiService;

    public ExtendedKeyDAO getExtendedKeyDAO() {
        return extendedKeyDAO;
    }

    @Resource
    public void setExtendedKeyDAO(ExtendedKeyDAO extendedKeyDAO) {
        super.setBaseDAO(extendedKeyDAO);
        this.extendedKeyDAO = extendedKeyDAO;
    }
    @Resource
    public void setPasswordTextEncryptor(TextEncryptor passwordTextEncryptor) {
        this.passwordTextEncryptor = passwordTextEncryptor;
    }

    @Resource
    public void setMnemonicService(MnemonicService mnemonicService) {
        this.mnemonicService = mnemonicService;
    }
    @Resource
    public void setBip44ApiService(BIP44ApiService bip44ApiService) {
        this.bip44ApiService = bip44ApiService;
    }

    @Override
    public BIP44ChangeRsp change(BIP44ChangeReq req) {
        // 查询密码
        Mnemonic mnemonic = mnemonicService.getById(req.getMnemonicId());
        String mnemonicPassword = passwordTextEncryptor.decrypt(mnemonic.getPassword());

        // 调用crypto-api生成change ExtendedKey
        com.sharingif.blockchain.crypto.api.key.entity.BIP44ChangeReq apiReq = new com.sharingif.blockchain.crypto.api.key.entity.BIP44ChangeReq();
        apiReq.setMnemonicId(req.getMnemonicId());
        apiReq.setCoinType(req.getCoinType());
        apiReq.setAccount(req.getAccount());
        apiReq.setChange(req.getChange());
        apiReq.setMnemonicPassword(mnemonicPassword);
        apiReq.setPassword(req.getPassword());
        com.sharingif.blockchain.crypto.api.key.entity.BIP44ChangeRsp apiRsp = bip44ApiService.change(apiReq);

        // BIP44路径
        KeyPath keyPath = new KeyPath(apiReq.getCoinType(), req.getAccount(), req.getChange(), null);

        // 保存助记词信息
        ExtendedKey extendedKey = new ExtendedKey();
        extendedKey.setId(apiRsp.getId());
        extendedKey.setMnemonicId(req.getMnemonicId());
        extendedKey.setExtendedKeyPath(keyPath.getPath());
        extendedKey.setPassword(passwordTextEncryptor.encrypt(req.getPassword()));
        extendedKeyDAO.insert(extendedKey);

        // 返回助记词id
        BIP44ChangeRsp rsp = new BIP44ChangeRsp();
        rsp.setId(extendedKey.getId());

        return rsp;
    }
}
