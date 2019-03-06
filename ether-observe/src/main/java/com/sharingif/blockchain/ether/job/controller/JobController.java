package com.sharingif.blockchain.ether.job.controller;


import com.sharingif.cube.batch.core.JobService;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@RequestMapping(value="job")
public class JobController {

    private JobService jobService;

    @Resource
    public void setJobService(JobService jobService) {
        this.jobService = jobService;
    }

    @RequestMapping(value="deleteJobHistory", method= RequestMethod.POST)
    public void deleteJobHistory() {

        jobService.deleteJobHistory();
    }

}
