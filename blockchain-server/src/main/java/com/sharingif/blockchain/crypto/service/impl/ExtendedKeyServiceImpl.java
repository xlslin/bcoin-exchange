package com.sharingif.blockchain.crypto.service.impl;

import com.sharingif.blockchain.account.model.entity.BitCoin;
import com.sharingif.blockchain.account.service.BitCoinService;
import com.sharingif.blockchain.api.crypto.entity.BIP44ChangeReq;
import com.sharingif.blockchain.api.crypto.entity.BIP44ChangeRsp;
import com.sharingif.blockchain.app.constants.CoinType;
import com.sharingif.blockchain.app.constants.ErrorConstants;
import com.sharingif.blockchain.crypto.api.key.service.BIP44ApiService;
import com.sharingif.blockchain.crypto.dao.ExtendedKeyDAO;
import com.sharingif.blockchain.crypto.model.entity.ExtendedKey;
import com.sharingif.blockchain.crypto.model.entity.Bip44KeyPath;
import com.sharingif.blockchain.crypto.model.entity.Mnemonic;
import com.sharingif.blockchain.crypto.model.entity.SecretKey;
import com.sharingif.blockchain.crypto.service.ExtendedKeyService;
import com.sharingif.blockchain.crypto.service.MnemonicService;
import com.sharingif.cube.core.exception.validation.ValidationCubeException;
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
    private BitCoinService bitCoinService;

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
    @Resource
    public void setBitCoinService(BitCoinService bitCoinService) {
        this.bitCoinService = bitCoinService;
    }

    @Override
    public BIP44ChangeRsp change(BIP44ChangeReq req) {
        // 验证bip44币种是否支持
        Integer bip44CoinType = req.getCoinType();
        BitCoin bitCoin = bitCoinService.getBitCoinByBip44CoinType(String.valueOf(bip44CoinType));
        if(bitCoin == null) {
            throw new ValidationCubeException(ErrorConstants.BIP44_COIN_TYPE_DOES_NOT_EXIST, new Object[]{bip44CoinType});
        }

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
        Bip44KeyPath keyPath = new Bip44KeyPath(apiReq.getCoinType(), req.getAccount(), req.getChange(), null);

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

    @Override
    public ExtendedKey getExtendedKey(String coinType) {
        BitCoin bitCoin = bitCoinService.getBitCoinByCoinType(coinType);

        if(bitCoin == null) {
            throw new ValidationCubeException(ErrorConstants.COIN_TYPE_DOES_NOT_EXIST, new Object[]{coinType});
        }

        ExtendedKey queryExtendedKey = new ExtendedKey();
        queryExtendedKey.setExtendedKeyPath(
                new StringBuilder(Bip44KeyPath.BIP44)
                        .append("/")
                        .append(bitCoin.getBip44CoinType())
                        .append("'")
                        .append("/0'")
                        .append("/0")
                        .toString()
        );

        ExtendedKey extendedKey = extendedKeyDAO.query(queryExtendedKey);

        return extendedKey;
    }

    @Override
    public ExtendedKey getBitCoinChangeExtendedKey() {
        ExtendedKey extendedKey = getExtendedKey(CoinType.BTC.name());

        Bip44KeyPath bip44KeyPath = new Bip44KeyPath(extendedKey.getExtendedKeyPath());

        Bip44KeyPath bitCoinChangeBip44KeyPath = new Bip44KeyPath(
                new Integer(bip44KeyPath.getCoinType())
                ,new Integer(bip44KeyPath.getAccount())
                ,new Integer(bip44KeyPath.getChange())+1
                ,null
        );

        ExtendedKey queryBitCoinChangeExtendedKey = new ExtendedKey();
        queryBitCoinChangeExtendedKey.setExtendedKeyPath(bitCoinChangeBip44KeyPath.getPath());
        ExtendedKey bitCoinChangeExtendedKey = extendedKeyDAO.query(queryBitCoinChangeExtendedKey);

        return bitCoinChangeExtendedKey;
    }

}
