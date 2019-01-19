package com.sharingif.wallet.user.controller;


import com.sharingif.wallet.user.service.CifService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;


@Controller
@RequestMapping(value="cif")
public class CifController {
	
	private CifService cifService;

	public CifService getCifService() {
		return cifService;
	}
	@Resource
	public void setCifService(CifService cifService) {
		this.cifService = cifService;
	}
	
}
