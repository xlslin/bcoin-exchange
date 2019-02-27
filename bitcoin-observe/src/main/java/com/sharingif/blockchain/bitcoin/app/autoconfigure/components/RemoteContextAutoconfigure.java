package com.sharingif.blockchain.bitcoin.app.autoconfigure.components;

import com.sharingif.cube.communication.remote.RemoteServices;
import com.sharingif.cube.communication.remote.RemoteServicesApplicationContext;
import com.sharingif.cube.core.handler.bind.support.BindingInitializer;
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
            ,RemoteServices blockchainServerRemoteServices
            ,RemoteServices bitCoinCoreRemoteServices
            ,RemoteServices ominCoreRemoteServices
    ) {


        List<RemoteServices> remoteServicesList = new ArrayList<RemoteServices>();
        remoteServicesList.add(blockchainServerRemoteServices);
        remoteServicesList.add(bitCoinCoreRemoteServices);
        remoteServicesList.add(ominCoreRemoteServices);

        RemoteServicesApplicationContext remoteServicesApplicationContext = new RemoteServicesApplicationContext();
        remoteServicesApplicationContext.setBindingInitializer(bindingInitializer);
        remoteServicesApplicationContext.setRemoteServices(remoteServicesList);

        return remoteServicesApplicationContext;
    }

}
