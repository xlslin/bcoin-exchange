package com.sharingif.blockchain.bitcoin.job.controller;


import com.sharingif.blockchain.bitcoin.job.service.BatchJobService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


@Controller
@RequestMapping(value="batchJob")
public class BatchJobController {
	
	private BatchJobService batchJobService;

	public BatchJobService getBatchJobService() {
		return batchJobService;
	}
	@Resource
	public void setBatchJobService(BatchJobService batchJobService) {
		this.batchJobService = batchJobService;
	}
	
}
