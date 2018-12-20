package com.sharingif.blockchain.ether.app.autoconfigure.components;

import com.sharingif.cube.batch.core.JobConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class JobConfigComponentsAutoconfigure {

    @Bean("blockChainSyncDataJobConfig")
    public JobConfig createBlockChainSyncDataJobConfig() {
        return new JobConfig("/blockChain/syncDataJob","/blockChain/syncData");
    }


    @Bean("allJobConfig")
    public Map<String, JobConfig> createAllJobConfig(List<JobConfig> jobConfigList) {
        Map<String, JobConfig> allJobConfig = new HashMap<>(jobConfigList.size());
        for(JobConfig jobConfig : jobConfigList) {
            allJobConfig.put(jobConfig.getPreLookupPath(), jobConfig);
        }

        return allJobConfig;
    }

}
