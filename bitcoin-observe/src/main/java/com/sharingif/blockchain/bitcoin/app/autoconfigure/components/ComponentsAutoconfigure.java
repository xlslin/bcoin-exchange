package com.sharingif.blockchain.bitcoin.app.autoconfigure.components;

import com.sharingif.cube.batch.core.JobConfig;
import com.sharingif.cube.batch.core.exception.JobExceptionHandler;
import com.sharingif.cube.batch.core.handler.MultithreadDispatcherHandler;
import com.sharingif.cube.batch.core.handler.SimpleDispatcherHandler;
import com.sharingif.cube.batch.core.handler.adapter.JobRequestHandlerMethodArgumentResolver;
import com.sharingif.cube.batch.core.handler.chain.JobViewHandlerMethodChain;
import com.sharingif.cube.batch.core.request.JobRequestContextResolver;
import com.sharingif.cube.batch.core.view.JobView;
import com.sharingif.cube.batch.core.view.JobViewResolver;
import com.sharingif.cube.communication.view.MultiViewResolver;
import com.sharingif.cube.communication.view.ViewResolver;
import com.sharingif.cube.components.handler.chain.RequestLocalContextHolderChain;
import com.sharingif.cube.core.exception.handler.IExceptionHandler;
import com.sharingif.cube.core.exception.handler.MultiCubeExceptionHandler;
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

    @Bean("jobThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor createThreadPoolTaskExecutor(@Value("${work.max.pool.size}") int poolSize) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setCorePoolSize(poolSize);
        threadPoolTaskExecutor.setMaxPoolSize(poolSize);
        threadPoolTaskExecutor.setThreadNamePrefix("jobThreadPoolTaskExecutor-");

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

    @Bean("jobView")
    public JobView createJobView() {
        JobView jobView = new JobView();

        return jobView;
    }

    @Bean("multiViewResolver")
    public MultiViewResolver createMultiViewResolver(JobView jobView) {
        JobViewResolver jobViewResolver = new JobViewResolver();
        jobViewResolver.setJobView(jobView);

        List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
        viewResolvers.add(jobViewResolver);
        MultiViewResolver multiViewResolver = new MultiViewResolver();
        multiViewResolver.setViewResolvers(viewResolvers);

        return multiViewResolver;
    }

    @Bean("jobExceptionHandler")
    public JobExceptionHandler createJobExceptionHandler() {
        return new JobExceptionHandler();
    }

    @Bean("multiCubeExceptionHandler")
    public MultiCubeExceptionHandler createMultiCubeExceptionHandler(JobExceptionHandler jobExceptionHandler) {
        List<IExceptionHandler> exceptionHandlers = new ArrayList<IExceptionHandler>();
        exceptionHandlers.add(jobExceptionHandler);

        MultiCubeExceptionHandler multiCubeExceptionHandler = new MultiCubeExceptionHandler();
        multiCubeExceptionHandler.setCubeExceptionHandlers(exceptionHandlers);

        return multiCubeExceptionHandler;
    }

    @Bean("simpleDispatcherHandler")
    public SimpleDispatcherHandler createSimpleDispatcherHandler(
            MultiHandlerMethodChain multiHandlerMethodChain
            , MultiHandlerMapping multiHandlerMapping
            , MultiHandlerMethodAdapter multiHandlerMethodAdapter
            , MultiCubeExceptionHandler multiCubeExceptionHandler
            , MultiViewResolver multiViewResolver
    ) {



        SimpleDispatcherHandler simpleDispatcherHandler = new SimpleDispatcherHandler();
        simpleDispatcherHandler.setHandlerMethodChain(multiHandlerMethodChain);
        simpleDispatcherHandler.setRequestContextResolver(new JobRequestContextResolver());
        simpleDispatcherHandler.setMultiHandlerMapping(multiHandlerMapping);
        simpleDispatcherHandler.setMultiHandlerMethodAdapter(multiHandlerMethodAdapter);
        simpleDispatcherHandler.setMultiCubeExceptionHandler(multiCubeExceptionHandler);
        simpleDispatcherHandler.setMultiViewResolver(multiViewResolver);

        return simpleDispatcherHandler;
    }

    @Bean("jobMultithreadDispatcherHandler")
    public MultithreadDispatcherHandler createJobMultithreadDispatcherHandler(
            SimpleDispatcherHandler simpleDispatcherHandler
            , ThreadPoolTaskExecutor jobThreadPoolTaskExecutor
    ) {
        MultithreadDispatcherHandler multithreadDispatcherHandler = new MultithreadDispatcherHandler();

        multithreadDispatcherHandler.setMultithreadDispatcherHandlerThreadPoolTaskExecutor(jobThreadPoolTaskExecutor);
        multithreadDispatcherHandler.setSimpleDispatcherHandler(simpleDispatcherHandler);

        return multithreadDispatcherHandler;
    }

    @Bean("propertyTextEncryptor")
    public TextEncryptor createPropertyTextEncryptor(@Value("${property.key}") String key, Base64Coder base64Coder) throws UnsupportedEncodingException {
        byte[] keysByte = base64Coder.decode(key);
        AESECBEncryptor encryptor = new AESECBEncryptor(keysByte, base64Coder);

        return encryptor;
    }

}
