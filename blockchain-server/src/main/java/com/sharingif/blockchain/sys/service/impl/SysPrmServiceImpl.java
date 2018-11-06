package com.sharingif.blockchain.sys.service.impl;


import com.sharingif.blockchain.api.sys.entity.CurrentChangeExtendedKeyReq;
import com.sharingif.blockchain.crypto.model.entity.ExtendedKey;
import com.sharingif.blockchain.crypto.model.entity.KeyPath;
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


	@Override
	public String getCurrentChangeExtendedKey(int coinType) {
		SysPrm sysPrm = new SysPrm();
		sysPrm.setPrmName(SysPrm.CURRENT_CHANGE_EXTENDED_KEY_PREFIX+coinType);
		sysPrm.setPrmStatus(SysPrm.PRM_STATUS_VALID);

		sysPrm = sysPrmDAO.query(sysPrm);

		return sysPrm.getPrmValue();
	}

	@Override
	public void setCurrentChangeExtendedKey(CurrentChangeExtendedKeyReq req) {
		ExtendedKey extendedKey = extendedKeyService.getById(req.getChangeExtendedKeyId());

		KeyPath keyPath = new KeyPath(extendedKey.getExtendedKeyPath());

		String prmName = SysPrm.CURRENT_CHANGE_EXTENDED_KEY_PREFIX+keyPath.getCoinType();

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
