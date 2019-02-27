package com.sharingif.blockchain.bitcoin.app.autoconfigure.components;

import com.sharingif.cube.communication.JsonModel;
import com.sharingif.cube.communication.exception.JsonModelBusinessCommunicationExceptionHandler;
import com.sharingif.cube.communication.http.apache.transport.HttpJsonConnection;
import com.sharingif.cube.communication.http.transport.transform.ObjectToJsonStringMarshaller;
import com.sharingif.cube.communication.remote.RemoteServices;
import com.sharingif.cube.communication.transport.ProxyInterfaceHandlerMethodCommunicationTransportFactory;
import com.sharingif.cube.communication.transport.transform.ProxyInterfaceHandlerMethodCommunicationTransform;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import org.bitcoincore.request.BitCoinCoreRequestContextResolver;
import org.bitcoincore.transport.transform.BitCoinCoreStringToJsonModelMarshaller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * BitCoinCore服务
 */
@Configuration
public class OmniCoreRemoteContextAutoconfigure {


    @Bean(name = "ominCoreHttpJsonConnection")
    public HttpJsonConnection createOminCoreHttpJsonConnection(
            @Value("${omincore.server.http.host}")String host
            ,@Value("${omincore.server.http.port}")int port
            ,@Value("${omincore.server.http.so.timeout}")int soTimeout
            ,@Value("${omincore.server.http.user}")String user
            ,@Value("${omincore.server.http.password}")String password
            ,@Value("${omincore.server.http.receive.logger}")Boolean receiveLogger
    ) {
        HttpJsonConnection apacheHttpJsonConnection = new HttpJsonConnection(host, port, null);
        apacheHttpJsonConnection.setSoTimeout(soTimeout);
        apacheHttpJsonConnection.setUser(user);
        apacheHttpJsonConnection.setPassword(password);
        apacheHttpJsonConnection.setReceiveLogger(receiveLogger);

        return apacheHttpJsonConnection;
    }

    @Bean(name= "ominCoreHttpJsonRemoteHandlerMethodTransportFactory")
    public ProxyInterfaceHandlerMethodCommunicationTransportFactory<String,String,JsonModel<Object>> createOminCoreHttpJsonRemoteHandlerMethodTransportFactory(
            HttpJsonConnection omincoreHttpJsonConnection
            , ProxyInterfaceHandlerMethodCommunicationTransform<String,String,JsonModel<Object>> bitCoinCoreJsonModelProxyInterfaceHandlerMethodCommunicationTransform
            , JsonModelBusinessCommunicationExceptionHandler jsonModelBusinessCommunicationExceptionHandler
            , MultiHandlerMethodChain transportChains
    ) {
        ProxyInterfaceHandlerMethodCommunicationTransportFactory<String,String,JsonModel<Object>> httpJsonRemoteHandlerMethodTransportFactory = new ProxyInterfaceHandlerMethodCommunicationTransportFactory<String,String,JsonModel<Object>>();
        httpJsonRemoteHandlerMethodTransportFactory.setConnection(omincoreHttpJsonConnection);
        httpJsonRemoteHandlerMethodTransportFactory.setTransform(bitCoinCoreJsonModelProxyInterfaceHandlerMethodCommunicationTransform);
        httpJsonRemoteHandlerMethodTransportFactory.setBusinessCommunicationExceptionHandler(jsonModelBusinessCommunicationExceptionHandler);
        httpJsonRemoteHandlerMethodTransportFactory.setHandlerMethodChain(transportChains);

        return httpJsonRemoteHandlerMethodTransportFactory;
    }

    @Bean(name = "ominCoreRemoteServices")
    public RemoteServices createOminCoreRemoteServices(
            BitCoinCoreRequestContextResolver bitCoinCoreRequestContextResolver
            ,ProxyInterfaceHandlerMethodCommunicationTransportFactory<String,String,JsonModel<Object>> ominCoreHttpJsonRemoteHandlerMethodTransportFactory
    ) {
        List<String> services = new ArrayList<String>();

        services.add("org.omnilayer.api.rawtransactions.service.OmniRawTransactionsApiService");
        services.add("org.omnilayer.api.wallet.service.OmniWalletApiService");

        RemoteServices remoteServices = new RemoteServices();
        remoteServices.setRequestContextResolver(bitCoinCoreRequestContextResolver);
        remoteServices.setHandlerMethodCommunicationTransportFactory(ominCoreHttpJsonRemoteHandlerMethodTransportFactory);
        remoteServices.setServices(services);

        return remoteServices;
    }

}
