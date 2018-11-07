package com.sharingif.blockchain.sys.service.impl;


import com.sharingif.blockchain.account.model.entity.BitCoin;
import com.sharingif.blockchain.account.service.BitCoinService;
import com.sharingif.blockchain.api.sys.entity.CurrentChangeExtendedKeyReq;
import com.sharingif.blockchain.crypto.model.entity.ExtendedKey;
import com.sharingif.blockchain.crypto.model.entity.Bip44KeyPath;
import com.sharingif.blockchain.crypto.service.ExtendedKeyService;
import com.sharingif.blockchain.sys.dao.SysPrmDAO;
import com.sharingif.blockchain.sys.model.entity.SysPrm;
import com.sharingif.blockchain.sys.service.SysPrmService;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysPrmServiceImpl extends BaseServiceImpl<SysPrm, java.lang.String> implements SysPrmService {
	
	private SysPrmDAO sysPrmDAO;

	private ExtendedKeyService extendedKeyService;
	private BitCoinService bitCoinService;

	public SysPrmDAO getSysPrmDAO() {
		return sysPrmDAO;
	}
	@Resource
	public void setSysPrmDAO(SysPrmDAO sysPrmDAO) {
		super.setBaseDAO(sysPrmDAO);
		this.sysPrmDAO = sysPrmDAO;
	}

	@Resource
	public void setExtendedKeyService(ExtendedKeyService extendedKeyService) {
		this.extendedKeyService = extendedKeyService;
	}
	@Resource
	public void setBitCoinService(BitCoinService bitCoinService) {
		this.bitCoinService = bitCoinService;
	}

	@Override
	public String getCurrentChangeExtendedKeyByCoinType(String coinType) {
		BitCoin bitCoin = bitCoinService.getBitCoinByCoinType(coinType);

		SysPrm sysPrm = new SysPrm();
		sysPrm.setPrmName(SysPrm.CURRENT_CHANGE_EXTENDED_KEY_PREFIX+bitCoin.getBlockType());
		sysPrm.setPrmStatus(SysPrm.PRM_STATUS_VALID);

		sysPrm = sysPrmDAO.query(sysPrm);

		return sysPrm.getPrmValue();
	}

	@Override
	public void setCurrentChangeExtendedKey(CurrentChangeExtendedKeyReq req) {
		ExtendedKey extendedKey = extendedKeyService.getById(req.getChangeExtendedKeyId());

		Bip44KeyPath keyPath = new Bip44KeyPath(extendedKey.getExtendedKeyPath());

		String blockType = bitCoinService.getBlockTypeByBip44CoinType(String.valueOf(keyPath.getCoinType()));

		String prmName = SysPrm.CURRENT_CHANGE_EXTENDED_KEY_PREFIX+blockType;

		SysPrm querySysPrm = new SysPrm();
		querySysPrm.setPrmName(prmName);
		querySysPrm = sysPrmDAO.query(querySysPrm);

		if(querySysPrm == null) {
			SysPrm sysPrm = new SysPrm();
			sysPrm.setPrmName(prmName);
			sysPrm.setPrmValue(req.getChangeExtendedKeyId());
			sysPrm.setPrmDesc(prmName);
			sysPrm.setPrmStatus(SysPrm.PRM_STATUS_VALID);

			sysPrmDAO.insert(sysPrm);
		} else {
			SysPrm sysPrm = new SysPrm();
			sysPrm.setId(querySysPrm.getId());
			sysPrm.setPrmValue(req.getChangeExtendedKeyId());

			sysPrmDAO.updateById(sysPrm);
		}
	}
}
