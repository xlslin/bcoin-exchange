package com.sharingif.blockchain.bitcoin.app.autoconfigure.components;

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

/**
 * blockchain-server服务
 */
@Configuration
public class BlockchainServerRemoteContextAutoconfigure {

    @Bean(name = "blockchainServerHttpJsonConnection")
    public HttpJsonConnection createBlockchainServerHttpJsonConnection(
            @Value("${blockchain.server.http.host}")String host
            ,@Value("${blockchain.server.http.port}")int port
            ,@Value("${blockchain.server.http.contextPath}") String contextPath
            ,@Value("${blockchain.server.http.so.timeout}")int soTimeout
    ) {
        HttpJsonConnection apacheHttpJsonConnection = new HttpJsonConnection(host, port, contextPath);
        apacheHttpJsonConnection.setSoTimeout(soTimeout);

        return apacheHttpJsonConnection;
    }


    @Bean(name= "blockchainServerHttpJsonRemoteHandlerMethodTransportFactory")
    public ProxyInterfaceHandlerMethodCommunicationTransportFactory<String,String,JsonModel<Object>> createBlockchainServerHttpJsonRemoteHandlerMethodTransportFactory(
            HttpJsonConnection blockchainServerHttpJsonConnection
            , ProxyInterfaceHandlerMethodCommunicationTransform<String,String,JsonModel<Object>> jsonModelProxyInterfaceHandlerMethodCommunicationTransform
            , JsonModelBusinessCommunicationExceptionHandler jsonModelBusinessCommunicationExceptionHandler
            , MultiHandlerMethodChain transportChains
    ) {
        ProxyInterfaceHandlerMethodCommunicationTransportFactory<String,String,JsonModel<Object>> httpJsonRemoteHandlerMethodTransportFactory = new ProxyInterfaceHandlerMethodCommunicationTransportFactory<String,String,JsonModel<Object>>();
        httpJsonRemoteHandlerMethodTransportFactory.setConnection(blockchainServerHttpJsonConnection);
        httpJsonRemoteHandlerMethodTransportFactory.setTransform(jsonModelProxyInterfaceHandlerMethodCommunicationTransform);
        httpJsonRemoteHandlerMethodTransportFactory.setBusinessCommunicationExceptionHandler(jsonModelBusinessCommunicationExceptionHandler);
        httpJsonRemoteHandlerMethodTransportFactory.setHandlerMethodChain(transportChains);

        return httpJsonRemoteHandlerMethodTransportFactory;
    }

    @Bean(name = "blockchainServerRemoteServices")
    public RemoteServices createBlockchainServerRemoteServices(
            HandlerMethodCommunicationTransportRequestContextResolver handlerMethodCommunicationTransportRequestContextResolver
            ,ProxyInterfaceHandlerMethodCommunicationTransportFactory<String,String,JsonModel<Object>> blockchainServerHttpJsonRemoteHandlerMethodTransportFactory
    ) {
        List<String> services = new ArrayList<String>();

        services.add("com.sharingif.blockchain.api.ether.service.EtherApiService");
        services.add("com.sharingif.blockchain.api.bitcoin.service.BitCoinApiService");

        RemoteServices remoteServices = new RemoteServices();
        remoteServices.setRequestContextResolver(handlerMethodCommunicationTransportRequestContextResolver);
        remoteServices.setHandlerMethodCommunicationTransportFactory(blockchainServerHttpJsonRemoteHandlerMethodTransportFactory);
        remoteServices.setServices(services);

        return remoteServices;
    }

}
