package com.sharingif.blockchain.crypto.service.impl;

import com.sharingif.blockchain.api.crypto.entity.MnemonicGenerateReq;
import com.sharingif.blockchain.api.crypto.entity.MnemonicGenerateRsp;
import com.sharingif.blockchain.crypto.api.mnemonic.service.MnemonicApiService;
import com.sharingif.blockchain.crypto.dao.MnemonicDAO;
import com.sharingif.blockchain.crypto.model.entity.Mnemonic;
import com.sharingif.blockchain.crypto.service.MnemonicService;
import com.sharingif.cube.security.confidentiality.encrypt.TextEncryptor;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * MnemonicServiceImpl
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/5 下午7:55
 */
@Service
public class MnemonicServiceImpl extends BaseServiceImpl<Mnemonic, String> implements MnemonicService {

    private MnemonicDAO mnemonicDAO;
    private MnemonicApiService mnemonicApiService;
    private TextEncryptor passwordTextEncryptor;

    @Resource
    public void setMnemonicDAO(MnemonicDAO mnemonicDAO) {
        super.setBaseDAO(mnemonicDAO);
        this.mnemonicDAO = mnemonicDAO;
    }
    @Resource
    public void setMnemonicApiService(MnemonicApiService mnemonicApiService) {
        this.mnemonicApiService = mnemonicApiService;
    }
    @Resource
    public void setPasswordTextEncryptor(TextEncryptor passwordTextEncryptor) {
        this.passwordTextEncryptor = passwordTextEncryptor;
    }

    @Override
    public MnemonicGenerateRsp generate(MnemonicGenerateReq req) {

        // 调用crypto-api生成助记词
        com.sharingif.blockchain.crypto.api.mnemonic.entity.MnemonicGenerateReq apiReq = new com.sharingif.blockchain.crypto.api.mnemonic.entity.MnemonicGenerateReq();
        apiReq.setLocale(req.getLocale());
        apiReq.setMnemonic(req.getMnemonic());
        apiReq.setLength(req.getLength());
        apiReq.setPassword(req.getPassword());
        com.sharingif.blockchain.crypto.api.mnemonic.entity.MnemonicGenerateRsp apiRsp = mnemonicApiService.generate(apiReq);

        // 保存助记词信息
        Mnemonic mnemonic = new Mnemonic();
        mnemonic.setId(apiRsp.getId());
        mnemonic.setName(req.getName());
        mnemonic.setPassword(passwordTextEncryptor.encrypt(req.getPassword()));
        mnemonicDAO.insert(mnemonic);

        // 返回助记词id、助记词
        MnemonicGenerateRsp mnemonicGenerateRsp = new MnemonicGenerateRsp();
        mnemonicGenerateRsp.setId(mnemonic.getId());
        mnemonicGenerateRsp.setMnemonic(apiRsp.getMnemonic());

        return mnemonicGenerateRsp;
    }

}
