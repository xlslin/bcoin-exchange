package org.bitcoincore.request;

import com.sharingif.cube.communication.MediaType;
import com.sharingif.cube.communication.transport.AbstractHandlerMethodTransport;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.request.RequestContext;
import com.sharingif.cube.core.request.RequestContextResolver;
import com.sharingif.cube.core.util.UUIDUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Arrays;
import java.util.Locale;

public class BitCoinCoreRequestContextResolver implements RequestContextResolver<Object[], RequestContext<Object[]>> {

    @Override
    public RequestContext<Object[]> resolveRequest(Object[] request) {
        AbstractHandlerMethodTransport<?,?,?,?> remoteHttpJsonHandlerMethod = (AbstractHandlerMethodTransport<?,?,?,?>) request[0];
        Object[] args = (Object[])request[1];

        RequestMapping methodRequestMapping = AnnotationUtils.findAnnotation(remoteHttpJsonHandlerMethod.getMethod(), RequestMapping.class);
        String bestMethodValue = methodRequestMapping.value()[0];

        BitCoinCoreRequest bitCoinCoreRequest = new BitCoinCoreRequest();
        bitCoinCoreRequest.setJsonrpc("1.0");
        bitCoinCoreRequest.setId(UUIDUtils.generateUUID());
        bitCoinCoreRequest.setMethod(bestMethodValue);
        bitCoinCoreRequest.setParams(Arrays.asList(args));

        args = new Object[]{bitCoinCoreRequest};

        RequestContext<Object[]> requestContext = new RequestContext<Object[]>(
                MediaType.APPLICATION_JSON.toString()
                ,bestMethodValue, Locale.getDefault()
                ,methodRequestMapping.name()
                ,args
        );

        return requestContext;
    }

}
