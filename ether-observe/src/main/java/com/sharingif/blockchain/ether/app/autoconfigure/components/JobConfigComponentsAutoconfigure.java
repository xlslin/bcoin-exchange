package com.sharingif.blockchain.ether.app.autoconfigure.components;

import com.sharingif.cube.batch.core.JobConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class JobConfigComponentsAutoconfigure {

    @Bean("blockChainSynchingDataJobConfig")
    public JobConfig createBlockChainSynchingDataJobConfig() {
        JobConfig jobConfig = new JobConfig(null,"/blockChain/synchingData", null);
        jobConfig.setMaxExecuteCount(100);
        return jobConfig;
    }

    @Bean("blockChainValidateBolckJobConfig")
    public JobConfig createBlockChainValidateBolckJobConfig() {
        JobConfig jobConfig = new JobConfig(null,"/blockChain/validateBolck", null);
        jobConfig.setMaxExecuteCount(100);
        return jobConfig;
    }

    @Bean("depositInitNoticeJobConfig")
    public JobConfig createDepositInitDepositNoticeJobConfigJobConfig() {
        JobConfig jobConfig = new JobConfig(null,"/deposit/initNotice", null);
        jobConfig.setIntervalPlanExecuteTime(1000*60*5);
        jobConfig.setMaxExecuteCount(5);
        return jobConfig;
    }

    @Bean("depositFinishNoticeJobConfig")
    public JobConfig createDepositFinishNoticeJobConfig() {
        JobConfig jobConfig = new JobConfig(null,"/deposit/finishNotice", null);
        jobConfig.setIntervalPlanExecuteTime(1000*60*5);
        jobConfig.setMaxExecuteCount(5);
        return jobConfig;
    }

    @Bean("withdrawalInitNoticeNoticeJobConfig")
    public JobConfig createWithdrawalInitNoticeNoticeJobConfig() {
        JobConfig jobConfig = new JobConfig(null,"/withdrawal/initNotice", null);
        jobConfig.setIntervalPlanExecuteTime(1000*60*5);
        jobConfig.setMaxExecuteCount(5);
        return jobConfig;
    }

    @Bean("withdrawalSuccessNoticeJobConfig")
    public JobConfig createWithdrawalSuccessNoticeJobConfig() {
        JobConfig jobConfig = new JobConfig(null,"/withdrawal/successNotice", null);
        jobConfig.setIntervalPlanExecuteTime(1000*60*5);
        jobConfig.setMaxExecuteCount(5);
        return jobConfig;
    }

    @Bean("withdrawalFailureNoticeJobConfig")
    public JobConfig createWithdrawalFailureNoticeJobConfig() {
        JobConfig jobConfig = new JobConfig(null,"/withdrawal/failureNotice", null);
        jobConfig.setIntervalPlanExecuteTime(1000*60*5);
        jobConfig.setMaxExecuteCount(5);
        return jobConfig;
    }

    @Bean("allJobConfig")
    public Map<String, JobConfig> createAllJobConfig(List<JobConfig> jobConfigList) {
        Map<String, JobConfig> allJobConfig = new HashMap<>(jobConfigList.size());
        for(JobConfig jobConfig : jobConfigList) {
            allJobConfig.put(jobConfig.getLookupPath(), jobConfig);
        }

        return allJobConfig;
    }

}
