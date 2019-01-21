package com.sharingif.blockchain.bitcoin.job.controller;


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

    @RequestMapping(value="putQueue", method= RequestMethod.POST)
    public void putQueue() {

        jobService.putQueue();
    }

    @RequestMapping(value="consume", method= RequestMethod.POST)
    public void consume() {

        jobService.consume();
    }

    @RequestMapping(value="deleteJobHistory", method= RequestMethod.POST)
    public void deleteJobHistory() {

        jobService.deleteJobHistory();
    }

}
