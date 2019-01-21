package org.bitcoincore.transport.transform;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sharingif.cube.communication.JsonModel;
import com.sharingif.cube.core.config.CubeConfigure;
import com.sharingif.cube.core.handler.bind.support.BindingInitializer;
import com.sharingif.cube.core.transport.exception.MarshallerException;
import com.sharingif.cube.core.transport.transform.Marshaller;
import com.sharingif.cube.core.transport.transform.MethodParameterArgument;
import org.bitcoincore.request.BitCoinCoreResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.TimeZone;

public class BitCoinCoreStringToJsonModelMarshaller implements Marshaller<MethodParameterArgument<Object[],String>, JsonModel<Object>> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private ObjectMapper objectMapper;
    private BindingInitializer bindingInitializer;

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public BitCoinCoreStringToJsonModelMarshaller() {
        objectMapper = new ObjectMapper();
        objectMapper.setTimeZone(TimeZone.getTimeZone(CubeConfigure.DEFAULT_TIME_ZONE));
    }

    public BitCoinCoreStringToJsonModelMarshaller(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public BindingInitializer getBindingInitializer() {
        return bindingInitializer;
    }

    public void setBindingInitializer(BindingInitializer bindingInitializer) {
        this.bindingInitializer = bindingInitializer;
    }


    @Override
    public JsonModel<Object> marshaller(MethodParameterArgument<Object[], String> methodParameterArgument) throws MarshallerException {

        JavaType javaType = getJavaType(methodParameterArgument.getMethodParameter().getNestedGenericParameterType());

        BitCoinCoreResponse<Object> object;
        try {
            object = objectMapper.readValue(methodParameterArgument.getConnectReturnValue(), javaType);
        } catch (Exception e) {
            this.logger.error("marshaller object to json error : {}", e);
            throw new MarshallerException("marshaller json to object error", e);
        }

        JsonModel<Object> jsonModel = new JsonModel<Object>();
        if(object.getError() == null) {
            jsonModel.set_tranStatus(true);
            jsonModel.set_data(object.getResult());
        } else {
            jsonModel.set_tranStatus(false);
            jsonModel.set_exceptionMessage(String.valueOf(object.getError().getCode()));
            jsonModel.set_exceptionLocalizedMessage(object.getError().getMessage());
        }

        return jsonModel;
    }

    protected JavaType getJavaType(Type type) {
        TypeFactory typeFactory = this.objectMapper.getTypeFactory();

        if(type.getTypeName().equals(Void.TYPE.getTypeName())) {
            return typeFactory.constructType(BitCoinCoreResponse.class);
        }

        JavaType javaType = typeFactory.constructType(type);
        return typeFactory.constructParametricType(BitCoinCoreResponse.class, javaType);
    }

}
