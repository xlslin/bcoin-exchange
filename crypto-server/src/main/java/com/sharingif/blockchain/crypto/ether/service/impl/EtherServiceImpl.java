package com.sharingif.blockchain.crypto.ether.service.impl;

import com.sharingif.blockchain.crypto.api.ether.entity.Erc20SignMessageReq;
import com.sharingif.blockchain.crypto.api.ether.entity.Erc20SignMessageRsp;
import com.sharingif.blockchain.crypto.api.ether.entity.SignMessageReq;
import com.sharingif.blockchain.crypto.api.ether.entity.SignMessageRsp;
import com.sharingif.blockchain.crypto.ether.service.EtherService;
import com.sharingif.blockchain.crypto.key.service.SecretKeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.tx.Transfer;
import org.web3j.utils.Numeric;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * EthServiceImpl
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/26 下午12:58
 */
@Service
public class EtherServiceImpl implements EtherService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private SecretKeyService secretKeyService;

    @Resource
    public void setSecretKeyService(SecretKeyService secretKeyService) {
        this.secretKeyService = secretKeyService;
    }

    @Override
    public SignMessageRsp signMessage(SignMessageReq req) {

        Credentials credentials = secretKeyService.getCredentials(req.getFromAddress(), req.getPassword());

        RawTransaction rawTransaction  = RawTransaction.createEtherTransaction(
                req.getNonce()
                ,req.getGasPrice()
                ,Transfer.GAS_LIMIT
                ,req.getToAddress()
                ,req.getAmount()
        );

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        SignMessageRsp rsp = new SignMessageRsp();
        rsp.setHexValue(hexValue);

        return rsp;
    }

    @Override
    public Erc20SignMessageRsp erc20SignMessage(Erc20SignMessageReq req) {

        Credentials credentials = secretKeyService.getCredentials(req.getFromAddress(), req.getPassword());

        Function function = new Function(
                "transfer"
                , Arrays.asList(new Address(req.getToAddress()), new Uint256(req.getAmount()))
                ,new ArrayList()
        );

        String encodedFunction = FunctionEncoder.encode(function);

        RawTransaction rawTransaction  = RawTransaction.createTransaction(
                req.getNonce()
                ,req.getGasPrice()
                ,req.getGasLimit()
                ,req.getContractAddress()
                ,encodedFunction
        );

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        Erc20SignMessageRsp rsp = new Erc20SignMessageRsp();
        rsp.setHexValue(hexValue);

        return rsp;
    }

}
