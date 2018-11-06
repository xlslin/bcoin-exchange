package com.sharingif.blockchain.sys.controller;


import com.sharingif.blockchain.api.sys.entity.CurrentChangeExtendedKeyReq;
import com.sharingif.blockchain.sys.service.SysPrmService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


@Controller
@RequestMapping(value="sysPrm")
public class SysPrmController {
	
	private SysPrmService sysPrmService;

	public SysPrmService getSysPrmService() {
		return sysPrmService;
	}
	@Resource
	public void setSysPrmService(SysPrmService sysPrmService) {
		this.sysPrmService = sysPrmService;
	}

	/**
	 * 设置当前change ExtendedKey
	 */
	@RequestMapping(value="setCurrentChangeExtendedKey", method= RequestMethod.POST)
	public void setCurrentChangeExtendedKey(CurrentChangeExtendedKeyReq req) {
		sysPrmService.setCurrentChangeExtendedKey(req);
	}
	
}
