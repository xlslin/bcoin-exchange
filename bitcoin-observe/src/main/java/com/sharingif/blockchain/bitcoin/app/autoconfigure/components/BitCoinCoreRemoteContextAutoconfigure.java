package com.sharingif.blockchain.bitcoin.app.autoconfigure.components;

import com.sharingif.cube.communication.JsonModel;
import com.sharingif.cube.communication.exception.JsonModelBusinessCommunicationExceptionHandler;
import com.sharingif.cube.communication.http.apache.transport.HttpJsonConnection;
import com.sharingif.cube.communication.http.transport.HandlerMethodCommunicationTransportRequestContextResolver;
import com.sharingif.cube.communication.http.transport.transform.ObjectToJsonStringMarshaller;
import com.sharingif.cube.communication.http.transport.transform.StringToJsonModelMarshaller;
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
public class BitCoinCoreRemoteContextAutoconfigure {

    @Bean(name = "bitCoinCoreRequestContextResolver")
    public BitCoinCoreRequestContextResolver createBitCoinCoreRequestContextResolver() {
        return new BitCoinCoreRequestContextResolver();
    }

    @Bean(name = "bitCoinCoreHttpJsonConnection")
    public HttpJsonConnection createBitCoinCoreHttpJsonConnection(
            @Value("${bitcoincore.server.http.host}")String host
            ,@Value("${bitcoincore.server.http.port}")int port
            ,@Value("${bitcoincore.server.http.so.timeout}")int soTimeout
            ,@Value("${bitcoincore.server.http.user}")String user
            ,@Value("${bitcoincore.server.http.password}")String password
            ,@Value("${bitcoincore.server.http.receive.logger}")Boolean receiveLogger
    ) {
        HttpJsonConnection apacheHttpJsonConnection = new HttpJsonConnection(host, port, null);
        apacheHttpJsonConnection.setSoTimeout(soTimeout);
        apacheHttpJsonConnection.setUser(user);
        apacheHttpJsonConnection.setPassword(password);
        apacheHttpJsonConnection.setReceiveLogger(receiveLogger);

        return apacheHttpJsonConnection;
    }

    @Bean(name="bitCoinCoreStringToJsonModelMarshaller")
    public BitCoinCoreStringToJsonModelMarshaller createBitCoinCoreStringToJsonModelMarshaller() {
        BitCoinCoreStringToJsonModelMarshaller bitCoinCoreStringToJsonModelMarshaller = new BitCoinCoreStringToJsonModelMarshaller();

        return bitCoinCoreStringToJsonModelMarshaller;
    }

    @Bean(name="bitCoinCoreJsonModelProxyInterfaceHandlerMethodCommunicationTransform")
    public ProxyInterfaceHandlerMethodCommunicationTransform<String,String,JsonModel<Object>> createBitCoinCoreJsonModelProxyInterfaceHandlerMethodCommunicationTransform(
            ObjectToJsonStringMarshaller objectToJsonStringMarshaller
            ,BitCoinCoreStringToJsonModelMarshaller bitCoinCoreStringToJsonModelMarshaller
    ) {
        ProxyInterfaceHandlerMethodCommunicationTransform<String,String,JsonModel<Object>> proxyInterfaceHandlerMethodCommunicationTransform = new ProxyInterfaceHandlerMethodCommunicationTransform<String,String,JsonModel<Object>>();
        proxyInterfaceHandlerMethodCommunicationTransform.setMarshaller(objectToJsonStringMarshaller);
        proxyInterfaceHandlerMethodCommunicationTransform.setUnmarshaller(bitCoinCoreStringToJsonModelMarshaller);

        return proxyInterfaceHandlerMethodCommunicationTransform;
    }

    @Bean(name= "bitCoinCoreHttpJsonRemoteHandlerMethodTransportFactory")
    public ProxyInterfaceHandlerMethodCommunicationTransportFactory<String,String,JsonModel<Object>> createBitCoinCoreHttpJsonRemoteHandlerMethodTransportFactory(
            HttpJsonConnection bitCoinCoreHttpJsonConnection
            , ProxyInterfaceHandlerMethodCommunicationTransform<String,String,JsonModel<Object>> bitCoinCoreJsonModelProxyInterfaceHandlerMethodCommunicationTransform
            , JsonModelBusinessCommunicationExceptionHandler jsonModelBusinessCommunicationExceptionHandler
            , MultiHandlerMethodChain transportChains
    ) {
        ProxyInterfaceHandlerMethodCommunicationTransportFactory<String,String,JsonModel<Object>> httpJsonRemoteHandlerMethodTransportFactory = new ProxyInterfaceHandlerMethodCommunicationTransportFactory<String,String,JsonModel<Object>>();
        httpJsonRemoteHandlerMethodTransportFactory.setConnection(bitCoinCoreHttpJsonConnection);
        httpJsonRemoteHandlerMethodTransportFactory.setTransform(bitCoinCoreJsonModelProxyInterfaceHandlerMethodCommunicationTransform);
        httpJsonRemoteHandlerMethodTransportFactory.setBusinessCommunicationExceptionHandler(jsonModelBusinessCommunicationExceptionHandler);
        httpJsonRemoteHandlerMethodTransportFactory.setHandlerMethodChain(transportChains);

        return httpJsonRemoteHandlerMethodTransportFactory;
    }

    @Bean(name = "bitCoinCoreRemoteServices")
    public RemoteServices createBitCoinCoreRemoteServices(
            BitCoinCoreRequestContextResolver bitCoinCoreRequestContextResolver
            ,ProxyInterfaceHandlerMethodCommunicationTransportFactory<String,String,JsonModel<Object>> bitCoinCoreHttpJsonRemoteHandlerMethodTransportFactory
    ) {
        List<String> services = new ArrayList<String>();

        services.add("org.bitcoincore.api.blockchain.service.BlockChainApiService");
        services.add("org.bitcoincore.api.rawtransactions.service.RawTransactionsApiService");
        services.add("org.bitcoincore.api.wallet.service.WalletApiService");

        RemoteServices remoteServices = new RemoteServices();
        remoteServices.setRequestContextResolver(bitCoinCoreRequestContextResolver);
        remoteServices.setHandlerMethodCommunicationTransportFactory(bitCoinCoreHttpJsonRemoteHandlerMethodTransportFactory);
        remoteServices.setServices(services);

        return remoteServices;
    }

}
