package com.sharingif.blockchain.app.autoconfigure.components;

import com.sharingif.cube.communication.JsonModel;
import com.sharingif.cube.communication.exception.JsonModelBusinessCommunicationExceptionHandler;
import com.sharingif.cube.communication.http.apache.transport.HttpJsonConnection;
import com.sharingif.cube.communication.http.transport.HandlerMethodCommunicationTransportRequestContextResolver;
import com.sharingif.cube.communication.remote.RemoteServices;
import com.sharingif.cube.communication.remote.RemoteServicesApplicationContext;
import com.sharingif.cube.communication.transport.ProxyInterfaceHandlerMethodCommunicationTransportFactory;
import com.sharingif.cube.communication.transport.transform.ProxyInterfaceHandlerMethodCommunicationTransform;
import com.sharingif.cube.core.handler.bind.support.BindingInitializer;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * RemoteContextAutoconfigure
 * 2017/6/1 下午3:23
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class RemoteContextAutoconfigure {

    @Bean(name = "remoteServicesApplicationContext")
    public RemoteServicesApplicationContext createRemoteServicesApplicationContext(
            BindingInitializer bindingInitializer
            ,RemoteServices cryptoServerRemoteServices
            ,RemoteServices etherObserveRemoteServices
            ,RemoteServices bitcoinObserveRemoteServices
    ) {


        List<RemoteServices> remoteServicesList = new ArrayList<RemoteServices>();
        remoteServicesList.add(cryptoServerRemoteServices);
        remoteServicesList.add(etherObserveRemoteServices);
        remoteServicesList.add(bitcoinObserveRemoteServices);

        RemoteServicesApplicationContext remoteServicesApplicationContext = new RemoteServicesApplicationContext();
        remoteServicesApplicationContext.setBindingInitializer(bindingInitializer);
        remoteServicesApplicationContext.setRemoteServices(remoteServicesList);

        return remoteServicesApplicationContext;
    }

}
