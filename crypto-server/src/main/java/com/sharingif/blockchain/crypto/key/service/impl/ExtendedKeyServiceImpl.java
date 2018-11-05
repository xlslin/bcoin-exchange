package com.sharingif.blockchain.crypto.key.service.impl;


import com.sharingif.blockchain.crypto.api.key.entity.BIP44ChangeReq;
import com.sharingif.blockchain.crypto.api.key.entity.BIP44ChangeRsp;
import com.sharingif.blockchain.crypto.app.components.Keystore;
import com.sharingif.blockchain.crypto.app.constants.ErrorConstants;
import com.sharingif.blockchain.crypto.btc.service.BtcService;
import com.sharingif.blockchain.crypto.key.dao.ExtendedKeyDAO;
import com.sharingif.blockchain.crypto.key.model.entity.ExtendedKey;
import com.sharingif.blockchain.crypto.key.model.entity.KeyPath;
import com.sharingif.blockchain.crypto.key.service.ExtendedKeyService;
import com.sharingif.blockchain.crypto.mnemonic.model.entity.Mnemonic;
import com.sharingif.blockchain.crypto.mnemonic.service.MnemonicService;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ExtendedKeyServiceImpl extends BaseServiceImpl<ExtendedKey, java.lang.String> implements ExtendedKeyService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private ExtendedKeyDAO extendedKeyDAO;
	private Keystore keystore;
	private MnemonicService mnemonicService;
	private BtcService btcService;

	@Resource
	public void setExtendedKeyDAO(ExtendedKeyDAO extendedKeyDAO) {
		super.setBaseDAO(extendedKeyDAO);
		this.extendedKeyDAO = extendedKeyDAO;
	}
	@Resource
	public void setKeystore(Keystore keystore) {
		this.keystore = keystore;
	}
	@Resource
	public void setMnemonicService(MnemonicService mnemonicService) {
		this.mnemonicService = mnemonicService;
	}
	@Resource
	public void setBtcService(BtcService btcService) {
		this.btcService = btcService;
	}


	@Override
	public BIP44ChangeRsp change(BIP44ChangeReq req) {

		// BIP44路径
		KeyPath keyPath = new KeyPath(req);

		Mnemonic mnemonicObject = mnemonicService.getMnemonic(req.getMnemonicId(), req.getMnemonicPassword());
		String mnemonic = mnemonicObject.getMnemonic();

		DeterministicSeed seed;
		try {
			seed = new DeterministicSeed(mnemonic, null, "", MnemonicCode.BIP39_STANDARDISATION_TIME_SECS);
		} catch (UnreadableWalletException e) {
			throw new RuntimeException(ErrorConstants.GENERATE_CHANGE_ERROR, e);
		}
		DeterministicKeyChain chain = DeterministicKeyChain.builder().seed(seed).build();
		List<ChildNumber> childNumberList = HDUtils.parsePath(keyPath.getBitcoinjPath());
		DeterministicKey changeDeterministicKey = chain.getKeyByPath(childNumberList, true);
		NetworkParameters networkParameters = btcService.getNetworkParameters(req.getCoinType());
		String deterministicKeyStr = changeDeterministicKey.serializePrivB58(networkParameters);

		String mnemonicPath = mnemonicObject.getMnemonicDirectory();
		String filePath = keystore.persistenceExtendedKey(mnemonicPath, keyPath.getPath(), "extendedKey", deterministicKeyStr, req.getPassword());

		// 保存助记词信息
		ExtendedKey extendedKey = new ExtendedKey();
		extendedKey.setMnemonicId(req.getMnemonicId());
		extendedKey.setExtendedKeyPath(keyPath.getPath());
		extendedKey.setFilePath(filePath);
		extendedKey.setCurrentIndexNumber(-1);
		extendedKeyDAO.insert(extendedKey);

		// 返回助记词id
		BIP44ChangeRsp rsp = new BIP44ChangeRsp();
		rsp.setId(extendedKey.getId());

		return rsp;
	}

	@Override
	public synchronized ExtendedKey incrementCurrentIndexNumber(String changeExtendedKeyId) {
		ExtendedKey extendedKey = extendedKeyDAO.queryById(changeExtendedKeyId);
		Integer addressIndex = extendedKey.getCurrentIndexNumber();
		addressIndex++;
		extendedKey.setCurrentIndexNumber(addressIndex);

		ExtendedKey updateExtendedKey = new ExtendedKey();
		updateExtendedKey.setId(changeExtendedKeyId);
		updateExtendedKey.setCurrentIndexNumber(extendedKey.getCurrentIndexNumber());
		extendedKeyDAO.updateById(updateExtendedKey);

		return extendedKey;
	}
}
