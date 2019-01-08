package com.sharingif.blockchain.app.autoconfigure.components;

import com.sharingif.blockchain.crypto.api.ether.service.EtherApiService;
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
import java.util.Arrays;
import java.util.List;

/**
 * crypto-server服务
 */
@Configuration
public class CryptoServerRemoteContextAutoconfigure {

    @Bean(name = "cryptoServerHttpJsonConnection")
    public HttpJsonConnection createCryptoServerHttpJsonConnection(
            @Value("${crypto.server.http.host}")String host
            ,@Value("${crypto.server.http.port}")int port
            ,@Value("${crypto.server.http.contextPath}") String contextPath
            ,@Value("${crypto.server.http.so.timeout}")int soTimeout
    ) {
        HttpJsonConnection apacheHttpJsonConnection = new HttpJsonConnection(host, port, contextPath);
        apacheHttpJsonConnection.setSoTimeout(soTimeout);
        apacheHttpJsonConnection.setExcludeLogTransList(
                Arrays.asList(
                        "/mnemonic/generate"
                        ,"/bip44/change"
                        ,"/bip44/addressIndex"
                )
        );

        return apacheHttpJsonConnection;
    }


    @Bean(name= "cryptoServerHttpJsonRemoteHandlerMethodTransportFactory")
    public ProxyInterfaceHandlerMethodCommunicationTransportFactory<String,String,JsonModel<Object>> createCryptoServerHttpJsonRemoteHandlerMethodTransportFactory(
            HttpJsonConnection cryptoServerHttpJsonConnection
            , ProxyInterfaceHandlerMethodCommunicationTransform<String,String,JsonModel<Object>> jsonModelProxyInterfaceHandlerMethodCommunicationTransform
            , JsonModelBusinessCommunicationExceptionHandler jsonModelBusinessCommunicationExceptionHandler
            , MultiHandlerMethodChain transportChains
    ) {
        ProxyInterfaceHandlerMethodCommunicationTransportFactory<String,String,JsonModel<Object>> httpJsonRemoteHandlerMethodTransportFactory = new ProxyInterfaceHandlerMethodCommunicationTransportFactory<String,String,JsonModel<Object>>();
        httpJsonRemoteHandlerMethodTransportFactory.setConnection(cryptoServerHttpJsonConnection);
        httpJsonRemoteHandlerMethodTransportFactory.setTransform(jsonModelProxyInterfaceHandlerMethodCommunicationTransform);
        httpJsonRemoteHandlerMethodTransportFactory.setBusinessCommunicationExceptionHandler(jsonModelBusinessCommunicationExceptionHandler);
        httpJsonRemoteHandlerMethodTransportFactory.setHandlerMethodChain(transportChains);

        return httpJsonRemoteHandlerMethodTransportFactory;
    }

    @Bean(name = "cryptoServerRemoteServices")
    public RemoteServices createCryptoServerRemoteServices(
            HandlerMethodCommunicationTransportRequestContextResolver handlerMethodCommunicationTransportRequestContextResolver
            ,ProxyInterfaceHandlerMethodCommunicationTransportFactory<String,String,JsonModel<Object>> cryptoServerHttpJsonRemoteHandlerMethodTransportFactory
    ) {
        List<String> services = new ArrayList<String>();

        services.add("com.sharingif.blockchain.crypto.api.mnemonic.service.MnemonicApiService");
        services.add("com.sharingif.blockchain.crypto.api.key.service.BIP44ApiService");
        services.add("com.sharingif.blockchain.crypto.api.ether.service.EtherApiService");

        RemoteServices remoteServices = new RemoteServices();
        remoteServices.setRequestContextResolver(handlerMethodCommunicationTransportRequestContextResolver);
        remoteServices.setHandlerMethodCommunicationTransportFactory(cryptoServerHttpJsonRemoteHandlerMethodTransportFactory);
        remoteServices.setServices(services);

        return remoteServices;
    }

}
