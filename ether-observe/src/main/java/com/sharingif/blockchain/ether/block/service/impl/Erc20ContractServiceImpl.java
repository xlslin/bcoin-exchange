package com.sharingif.blockchain.ether.block.service.impl;

import com.sharingif.blockchain.ether.block.service.Erc20ContractService;
import com.sharingif.cube.core.exception.validation.ValidationCubeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.Utils;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.tx.exceptions.ContractCallException;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class Erc20ContractServiceImpl implements Erc20ContractService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private Web3j web3j;

    @Resource
    public void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }

    @Override
    public List<Type> getTransfer(String txInput) {
        if(txInput.length()<10) {
            return null;
        }

        Function function = new Function(
                "transfer",
                Arrays.<Type>asList(Address.DEFAULT, Uint256.DEFAULT),
                Collections.<TypeReference<?>>emptyList());

        String functionSignature = FunctionEncoder.encode(function);

        String method = txInput.substring(0,10);

        if(!method.equals(functionSignature.substring(0,10))) {
            return null;
        }

        List<Type> results = FunctionReturnDecoder.decode(
                txInput.substring(10),
                Utils.convert(Arrays.<TypeReference<?>>asList(
                        new TypeReference<Address>(){},
                        new TypeReference<Uint256>(){})
                )
        );

        return results;
    }

    @Override
    public String name(String contractAddress) {
        final Function function = new Function("name",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));

        RemoteCall<String> remoteCall = executeRemoteCallSingleValueReturn(function, String.class, contractAddress);

        try {
            return remoteCall.send();
        } catch (Exception e) {
            throw new ValidationCubeException("get name error");
        }
    }

    @Override
    public String symbol(String contractAddress) {
        final Function function = new Function("symbol",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        RemoteCall<String> remoteCall = executeRemoteCallSingleValueReturn(function, String.class, contractAddress);

        try {
            return remoteCall.send();
        } catch (Exception e) {
            throw new ValidationCubeException("get symbol error");
        }
    }

    @Override
    public BigInteger decimals(String contractAddress) {
        final Function function = new Function("decimals",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        RemoteCall<BigInteger> remoteCall = executeRemoteCallSingleValueReturn(function, BigInteger.class, contractAddress);

        try {
            return remoteCall.send();
        } catch (Exception e) {
            throw new ValidationCubeException("get decimals error");
        }
    }

    @Override
    public BigInteger totalSupply(String contractAddress) {
        final Function function = new Function("totalSupply",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        RemoteCall<BigInteger> remoteCall = executeRemoteCallSingleValueReturn(function, BigInteger.class, contractAddress);

        try {
            return remoteCall.send();
        } catch (Exception e) {
            throw new ValidationCubeException("get totalSupply error");
        }
    }

    protected <T> RemoteCall<T> executeRemoteCallSingleValueReturn(
            Function function, Class<T> returnType, String contractAddress) {
        return new RemoteCall<>(() -> executeCallSingleValueReturn(function, returnType, contractAddress));
    }

    private List<Type> executeCall(
            Function function, String contractAddress) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
                Transaction.createEthCallTransaction(
                        "0x0000000000000000000000000000000000000000", contractAddress, encodedFunction),
                DefaultBlockParameterName.LATEST)
                .send();

        String value = ethCall.getValue();
        return FunctionReturnDecoder.decode(value, function.getOutputParameters());
    }

    protected <T extends Type> T executeCallSingleValueReturn(
            Function function, String contractAddress) throws IOException {
        List<Type> values = executeCall(function, contractAddress);
        if (!values.isEmpty()) {
            return (T) values.get(0);
        } else {
            return null;
        }
    }

    protected <T extends Type, R> R executeCallSingleValueReturn(
            Function function, Class<R> returnType, String contractAddress) throws IOException {
        T result = executeCallSingleValueReturn(function, contractAddress);
        if (result == null) {
            throw new ContractCallException("Empty value (0x) returned from contract");
        }

        Object value = result.getValue();
        if (returnType.isAssignableFrom(value.getClass())) {
            return (R) value;
        } else if (result.getClass().equals(Address.class) && returnType.equals(String.class)) {
            return (R) result.toString();  // cast isn't necessary
        } else {
            throw new ContractCallException(
                    "Unable to convert response: " + value
                            + " to expected type: " + returnType.getSimpleName());
        }
    }


}
