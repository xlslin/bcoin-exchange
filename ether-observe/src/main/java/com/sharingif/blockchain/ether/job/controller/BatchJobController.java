package com.sharingif.blockchain.ether.job.controller;



import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.blockchain.ether.job.service.BatchJobService;


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
