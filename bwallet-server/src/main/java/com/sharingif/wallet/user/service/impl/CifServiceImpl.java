package com.sharingif.wallet.user.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sharingif.wallet.user.model.entity.Cif;
import com.sharingif.wallet.user.dao.CifDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.wallet.user.service.CifService;

@Service
public class CifServiceImpl extends BaseServiceImpl<Cif, java.lang.String> implements CifService {
	
	private CifDAO cifDAO;

	public CifDAO getCifDAO() {
		return cifDAO;
	}
	@Resource
	public void setCifDAO(CifDAO cifDAO) {
		super.setBaseDAO(cifDAO);
		this.cifDAO = cifDAO;
	}
	
	
}
