package com.sharingif.blockchain.app.autoconfigure.components;

import com.sharingif.cube.communication.JsonModel;
import com.sharingif.cube.communication.exception.JsonModelBusinessCommunicationExceptionHandler;
import com.sharingif.cube.communication.http.apache.transport.HttpJsonConnection;
import com.sharingif.cube.communication.http.transport.HandlerMethodCommunicationTransportRequestContextResolver;
import com.sharingif.cube.communication.remote.RemoteServices;
import com.sharingif.cube.communication.transport.ProxyInterfaceHandlerMethodCommunicationTransportFactory;
import com.sharingif.cube.communication.transport.transform.ProxyInterfaceHandlerMethodCommunicationTransform;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class BitcoinObserveRemoteContextAutoconfigure {

    @Bean(name = "bitcoinObserveHttpJsonConnection")
    public HttpJsonConnection createBitcoinObserveHttpJsonConnection(
            @Value("${bitcoin.observe.http.host}")String host
            ,@Value("${bitcoin.observe.http.port}")int port
            ,@Value("${bitcoin.observe.http.contextPath}") String contextPath
            ,@Value("${bitcoin.observe.http.so.timeout}")int soTimeout
    ) {
        HttpJsonConnection apacheHttpJsonConnection = new HttpJsonConnection(host, port, contextPath);
        apacheHttpJsonConnection.setSoTimeout(soTimeout);

        return apacheHttpJsonConnection;
    }


    @Bean(name= "bitcoinObserveHttpJsonRemoteHandlerMethodTransportFactory")
    public ProxyInterfaceHandlerMethodCommunicationTransportFactory<String,String,JsonModel<Object>> createBitcoinObserveHttpJsonRemoteHandlerMethodTransportFactory(
            HttpJsonConnection bitcoinObserveHttpJsonConnection
            , ProxyInterfaceHandlerMethodCommunicationTransform<String,String,JsonModel<Object>> jsonModelProxyInterfaceHandlerMethodCommunicationTransform
            , JsonModelBusinessCommunicationExceptionHandler jsonModelBusinessCommunicationExceptionHandler
            , MultiHandlerMethodChain transportChains
    ) {
        ProxyInterfaceHandlerMethodCommunicationTransportFactory<String,String,JsonModel<Object>> httpJsonRemoteHandlerMethodTransportFactory = new ProxyInterfaceHandlerMethodCommunicationTransportFactory<String,String,JsonModel<Object>>();
        httpJsonRemoteHandlerMethodTransportFactory.setConnection(bitcoinObserveHttpJsonConnection);
        httpJsonRemoteHandlerMethodTransportFactory.setTransform(jsonModelProxyInterfaceHandlerMethodCommunicationTransform);
        httpJsonRemoteHandlerMethodTransportFactory.setBusinessCommunicationExceptionHandler(jsonModelBusinessCommunicationExceptionHandler);
        httpJsonRemoteHandlerMethodTransportFactory.setHandlerMethodChain(transportChains);

        return httpJsonRemoteHandlerMethodTransportFactory;
    }

    @Bean(name = "bitcoinObserveRemoteServices")
    public RemoteServices createBitcoinObserveRemoteServices(
            HandlerMethodCommunicationTransportRequestContextResolver handlerMethodCommunicationTransportRequestContextResolver
            ,ProxyInterfaceHandlerMethodCommunicationTransportFactory<String,String,JsonModel<Object>> bitcoinObserveHttpJsonRemoteHandlerMethodTransportFactory
    ) {
        List<String> services = new ArrayList<String>();

        services.add("com.sharingif.blockchain.bitcoin.api.account.service.AddressListenerApiService");
        services.add("com.sharingif.blockchain.bitcoin.api.withdrawal.service.WithdrawalBitCoinApiService");

        RemoteServices remoteServices = new RemoteServices();
        remoteServices.setRequestContextResolver(handlerMethodCommunicationTransportRequestContextResolver);
        remoteServices.setHandlerMethodCommunicationTransportFactory(bitcoinObserveHttpJsonRemoteHandlerMethodTransportFactory);
        remoteServices.setServices(services);

        return remoteServices;
    }

}
