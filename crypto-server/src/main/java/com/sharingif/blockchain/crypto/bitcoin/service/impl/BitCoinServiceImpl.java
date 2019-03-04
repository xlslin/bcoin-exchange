package com.sharingif.blockchain.crypto.bitcoin.service.impl;

import com.sharingif.blockchain.crypto.api.bitcoin.entity.*;
import com.sharingif.blockchain.crypto.app.constants.CoinType;
import com.sharingif.blockchain.crypto.bitcoin.service.BitCoinService;
import com.sharingif.blockchain.crypto.key.model.entity.Bip44KeyPath;
import com.sharingif.blockchain.crypto.key.model.entity.SecretKey;
import com.sharingif.blockchain.crypto.key.service.SecretKeyService;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import sd.fomin.gerbera.transaction.TransactionBuilder;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
public class BitCoinServiceImpl implements BitCoinService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private SecretKeyService secretKeyService;

    @Resource
    public void setSecretKeyService(SecretKeyService secretKeyService) {
        this.secretKeyService = secretKeyService;
    }

    @Override
    public NetworkParameters getNetworkParameters(int coinType) {
        if (coinType == CoinType.BTC_TEST.getBipCoinType()) {
            return TestNet3Params.get();
        }

        return MainNetParams.get();
    }

    @Override
    public SignMessageRsp signMessage(SignMessageReq req) {
        TransactionBuilder builder = null;
        NetworkParameters networkParameters = null;

        List<SignMessageVinReq> vinList = req.getVinList();
        for(SignMessageVinReq signMessageVinReq : vinList) {
            SecretKey secretKey = secretKeyService.getById(signMessageVinReq.getFromAddress());
            Credentials credentials = secretKeyService.getCredentials(secretKey, signMessageVinReq.getPassword());
            BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();

            if(builder == null) {
                Bip44KeyPath keyPath = new Bip44KeyPath(secretKey.getKeyPath());
                boolean isMainNet = keyPath.getCoinType() == CoinType.BTC_MAIN.getBipCoinType() ? true : false;

                networkParameters = getNetworkParameters(keyPath.getCoinType());

                builder = TransactionBuilder.create(isMainNet);
            }

            for(SignMessageUnspentReq signMessageUnspentReq : signMessageVinReq.getUnspentList()) {
                builder.from(
                        signMessageUnspentReq.getTxId(),
                        signMessageUnspentReq.getVout(),
                        signMessageUnspentReq.getScriptPubKey(),
                        signMessageUnspentReq.getAmount().longValue(),
                        ECKey.fromPrivate(privateKey).getPrivateKeyAsWiF(networkParameters)
                );
            }
        }

        for(SignMessageVoutReq signMessageVoutReq : req.getVoutList()) {
            builder.to(signMessageVoutReq.getToAddress(), signMessageVoutReq.getAmount().longValue());
        }

        builder.withFee(req.getFee().longValue());
        builder.changeTo(req.getChangeAddress());

        SignMessageRsp rsp = new SignMessageRsp();
        rsp.setHexValue(builder.build().getRawTransaction());

        return rsp;
    }

    @Override
    public SignMessageRsp omniSimpleSendSignMessage(OmniSimpleSendSignMessageReq req) {
        TransactionBuilder builder = null;
        NetworkParameters networkParameters = null;

        SignMessageVinReq signMessageVinReq = req.getVin();
        SecretKey secretKey = secretKeyService.getById(signMessageVinReq.getFromAddress());
        Credentials credentials = secretKeyService.getCredentials(secretKey, signMessageVinReq.getPassword());
        BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();

        Bip44KeyPath keyPath = new Bip44KeyPath(secretKey.getKeyPath());
        boolean isMainNet = keyPath.getCoinType() == CoinType.BTC_MAIN.getBipCoinType() ? true : false;

        networkParameters = getNetworkParameters(keyPath.getCoinType());

        builder = TransactionBuilder.create(isMainNet);

        for(SignMessageUnspentReq signMessageUnspentReq : signMessageVinReq.getUnspentList()) {
            builder.from(
                    signMessageUnspentReq.getTxId(),
                    signMessageUnspentReq.getVout(),
                    signMessageUnspentReq.getScriptPubKey(),
                    signMessageUnspentReq.getAmount().longValue(),
                    ECKey.fromPrivate(privateKey).getPrivateKeyAsWiF(networkParameters)
            );
        }

        SignMessageVoutReq signMessageVoutReq = req.getVout();
        builder.to(signMessageVoutReq.getToAddress(), signMessageVoutReq.getAmount().longValue());

        builder.put(req.getOpReturn(), 0L);

        builder.withFee(req.getFee().longValue());
        builder.changeTo(req.getChange());

        SignMessageRsp rsp = new SignMessageRsp();
        rsp.setHexValue(builder.build().getRawTransaction());

        return rsp;
    }

}
