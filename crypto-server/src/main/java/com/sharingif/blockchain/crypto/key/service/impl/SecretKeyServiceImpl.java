package com.sharingif.blockchain.crypto.key.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.crypto.api.key.entity.BIP44AddressIndexReq;
import com.sharingif.blockchain.crypto.api.key.entity.BIP44AddressIndexRsp;
import com.sharingif.blockchain.crypto.app.components.Keystore;
import com.sharingif.blockchain.crypto.btc.service.BtcService;
import com.sharingif.blockchain.crypto.key.model.entity.ExtendedKey;
import com.sharingif.blockchain.crypto.key.model.entity.Bip44KeyPath;
import com.sharingif.blockchain.crypto.key.service.BIP44IndexService;
import com.sharingif.blockchain.crypto.key.service.ExtendedKeyService;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.HDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.crypto.key.model.entity.SecretKey;
import com.sharingif.blockchain.crypto.key.dao.SecretKeyDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.crypto.key.service.SecretKeyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SecretKeyServiceImpl extends BaseServiceImpl<SecretKey, java.lang.String> implements SecretKeyService {
	
	private SecretKeyDAO secretKeyDAO;
	private ExtendedKeyService extendedKeyService;
	private BtcService btcService;
	private Keystore keystore;
	private Map<Integer, BIP44IndexService> bip44IndexServiceMap;

	public SecretKeyDAO getSecretKeyDAO() {
		return secretKeyDAO;
	}
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
	public void setBtcService(BtcService btcService) {
		this.btcService = btcService;
	}
	@Resource
	public void setKeystore(Keystore keystore) {
		this.keystore = keystore;
	}
	@Autowired
	public void setBIP44IndexServiceList(List<BIP44IndexService> bip44IndexServiceList) {
		bip44IndexServiceMap = new HashMap<Integer, BIP44IndexService>(bip44IndexServiceList.size());
		for (BIP44IndexService bip44IndexService : bip44IndexServiceList) {
			bip44IndexServiceMap.put(bip44IndexService.getCoinType(), bip44IndexService);
		}
	}

	@Override
	public BIP44AddressIndexRsp addressIndex(BIP44AddressIndexReq req) {

		ExtendedKey extendedKey = extendedKeyService.incrementCurrentIndexNumber(req.getChangeExtendedKeyId());

		// BIP44路径
		Bip44KeyPath keyPath = new Bip44KeyPath(extendedKey.getExtendedKeyPath(), extendedKey.getCurrentIndexNumber());

		NetworkParameters networkParameters = btcService.getNetworkParameters(keyPath.getCoinType());

		String changeDeterministicKeyBase58 = keystore.load(extendedKey.getFilePath(), req.getChangeExtendedKeyPassword());
		DeterministicKey changeDeterministicKey = DeterministicKey.deserializeB58(changeDeterministicKeyBase58, networkParameters);
		changeDeterministicKey = new DeterministicKey(HDUtils.append(HDUtils.parsePath(keyPath.getBitcoinjParentPath()), ChildNumber.ZERO), changeDeterministicKey.getChainCode(), changeDeterministicKey.getPrivKey(), changeDeterministicKey);

		DeterministicKey addressIndexDeterministicKey = HDKeyDerivation.deriveChildKey(changeDeterministicKey, new ChildNumber(extendedKey.getCurrentIndexNumber(), false));

		SecretKey secretKey = bip44IndexServiceMap.get(keyPath.getCoinType()).buildSecretKey(addressIndexDeterministicKey, extendedKey.getExtendedKeyDirectory(), extendedKey.getCurrentIndexNumber(), req.getPassword());

		// 保存key信息
		secretKey.setExtendedKeyId(extendedKey.getId());
		secretKey.setKeyPath(keyPath.getPath());
		secretKeyDAO.insert(secretKey);

		BIP44AddressIndexRsp rsp = new BIP44AddressIndexRsp();
		rsp.setId(secretKey.getId());
		rsp.setAddress(secretKey.getAddress());

		return rsp;
	}
}
