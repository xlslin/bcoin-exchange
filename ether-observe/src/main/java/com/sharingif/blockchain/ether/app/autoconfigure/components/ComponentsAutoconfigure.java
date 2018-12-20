package com.sharingif.blockchain.ether.app.autoconfigure.components;

import com.sharingif.cube.batch.core.JobConfig;
import com.sharingif.cube.batch.core.JobService;
import com.sharingif.cube.batch.core.handler.SimpleDispatcherHandler;
import com.sharingif.cube.batch.core.handler.adapter.JobRequestHandlerMethodArgumentResolver;
import com.sharingif.cube.batch.core.handler.chain.JobViewHandlerMethodChain;
import com.sharingif.cube.batch.core.request.JobRequestContextResolver;
import com.sharingif.cube.batch.core.view.JobViewResolver;
import com.sharingif.cube.communication.view.MultiViewResolver;
import com.sharingif.cube.communication.view.ViewResolver;
import com.sharingif.cube.components.handler.chain.RequestLocalContextHolderChain;
import com.sharingif.cube.core.handler.adapter.HandlerAdapter;
import com.sharingif.cube.core.handler.adapter.HandlerMethodArgumentResolver;
import com.sharingif.cube.core.handler.adapter.HandlerMethodHandlerAdapter;
import com.sharingif.cube.core.handler.adapter.MultiHandlerMethodAdapter;
import com.sharingif.cube.core.handler.bind.support.BindingInitializer;
import com.sharingif.cube.core.handler.chain.*;
import com.sharingif.cube.core.handler.mapping.HandlerMapping;
import com.sharingif.cube.core.handler.mapping.MultiHandlerMapping;
import com.sharingif.cube.core.handler.mapping.RequestMappingHandlerMapping;
import com.sharingif.cube.security.binary.Base64Coder;
import com.sharingif.cube.security.confidentiality.encrypt.TextEncryptor;
import com.sharingif.cube.security.confidentiality.encrypt.aes.AESECBEncryptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class ComponentsAutoconfigure {

    @Bean("taskScheduler")
    public TaskScheduler createTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(200);
        threadPoolTaskScheduler.setThreadNamePrefix("taskScheduler-");
        return threadPoolTaskScheduler;
    }

    @Bean("workThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor createThreadPoolTaskExecutor(@Value("${work.max.pool.size}") int poolSize) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setCorePoolSize(poolSize);
        threadPoolTaskExecutor.setMaxPoolSize(poolSize);

        return threadPoolTaskExecutor;
    }

    @Bean("multiHandlerMethodChain")
    public MultiHandlerMethodChain createRequestHandlerMethodChains(
            RequestLocalContextHolderChain requestLocalContextHolderChain
            , MDCChain mdcChain
            , MonitorPerformanceChain transactionMonitorPerformanceChain
    ) {
        List<HandlerMethodChain> requestHandlerMethodChains = new ArrayList<HandlerMethodChain>();
        requestHandlerMethodChains.add(requestLocalContextHolderChain);
        requestHandlerMethodChains.add(mdcChain);
        requestHandlerMethodChains.add(transactionMonitorPerformanceChain);

        MultiHandlerMethodChain multiHandlerMethodChain = new MultiHandlerMethodChain();
        multiHandlerMethodChain.setChains(requestHandlerMethodChains);

        return multiHandlerMethodChain;
    }

    @Bean("requestMappingHandlerMapping")
    public RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();
        handlerMapping.setUseSuffixPatternMatch(false);

        return handlerMapping;
    }

    @Bean("multiHandlerMapping")
    public MultiHandlerMapping createMultiHandlerMapping(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        List<HandlerMapping> handlerMappings = new ArrayList<HandlerMapping>();
        handlerMappings.add(requestMappingHandlerMapping);
        MultiHandlerMapping multiHandlerMapping = new MultiHandlerMapping();
        multiHandlerMapping.setHandlerMappings(handlerMappings);

        return multiHandlerMapping;
    }

    @Bean("multiHandlerMethodAdapter")
    public MultiHandlerMethodAdapter createMultiHandlerMethodAdapter(
            BindingInitializer bindingInitializer
            , MonitorPerformanceChain controllerMonitorPerformanceChain
            , AnnotationHandlerMethodChain annotationHandlerMethodChain
            , @Qualifier("allJobConfig") Map<String, JobConfig> allJobConfig
    ) {

        JobViewHandlerMethodChain jobViewHandlerMethodChain = new JobViewHandlerMethodChain();
        jobViewHandlerMethodChain.setAllJobConfig(allJobConfig);

        List<HandlerMethodChain> chains = new ArrayList<HandlerMethodChain>();
        chains.add(controllerMonitorPerformanceChain);
        chains.add(jobViewHandlerMethodChain);
        chains.add(annotationHandlerMethodChain);

        MultiHandlerMethodChain controllerChains = new MultiHandlerMethodChain();
        controllerChains.setChains(chains);

        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<HandlerMethodArgumentResolver>();
        argumentResolvers.add(new JobRequestHandlerMethodArgumentResolver());

        HandlerMethodHandlerAdapter handlerMethodHandlerAdapter = new HandlerMethodHandlerAdapter();
        handlerMethodHandlerAdapter.setHandlerMethodChain(controllerChains);
        handlerMethodHandlerAdapter.setArgumentResolvers(argumentResolvers);
        handlerMethodHandlerAdapter.setBindingInitializer(bindingInitializer);

        List<HandlerAdapter> handlerAdapters = new ArrayList<HandlerAdapter>();
        handlerAdapters.add(handlerMethodHandlerAdapter);
        MultiHandlerMethodAdapter multiHandlerMethodAdapter = new MultiHandlerMethodAdapter();
        multiHandlerMethodAdapter.setHandlerAdapters(handlerAdapters);

        return multiHandlerMethodAdapter;
    }

    @Bean("multiViewResolver")
    public MultiViewResolver createMultiViewResolver(JobService jobService) {
        List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
        viewResolvers.add(new JobViewResolver(jobService));
        MultiViewResolver multiViewResolver = new MultiViewResolver();
        multiViewResolver.setViewResolvers(viewResolvers);

        return multiViewResolver;
    }

    @Bean("simpleDispatcherHandler")
    public SimpleDispatcherHandler createSimpleDispatcherHandler(
            MultiHandlerMethodChain multiHandlerMethodChain
            , MultiHandlerMapping multiHandlerMapping
            , MultiHandlerMethodAdapter multiHandlerMethodAdapter
            , MultiViewResolver multiViewResolver
    ) {



        SimpleDispatcherHandler simpleDispatcherHandler = new SimpleDispatcherHandler();
        simpleDispatcherHandler.setHandlerMethodChain(multiHandlerMethodChain);
        simpleDispatcherHandler.setRequestContextResolver(new JobRequestContextResolver());
        simpleDispatcherHandler.setMultiHandlerMapping(multiHandlerMapping);
        simpleDispatcherHandler.setMultiHandlerMethodAdapter(multiHandlerMethodAdapter);
        simpleDispatcherHandler.setMultiViewResolver(multiViewResolver);
        return simpleDispatcherHandler;
    }

    @Bean("propertyTextEncryptor")
    public TextEncryptor createPropertyTextEncryptor(@Value("${property.key}") String key, Base64Coder base64Coder) throws UnsupportedEncodingException {
        byte[] keysByte = base64Coder.decode(key);
        AESECBEncryptor encryptor = new AESECBEncryptor(keysByte, base64Coder);

        return encryptor;
    }

    @Bean("web3j")
    public Web3j createWeb3j(@Value("${eth.web3j.address}")String ethWeb3jAddress) {
        return Web3j.build(new HttpService(ethWeb3jAddress));
    }

}
