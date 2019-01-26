package com.sharingif.blockchain.bitcoin.service.impl;

import com.sharingif.blockchain.api.bitcoin.entity.*;
import com.sharingif.blockchain.bitcoin.service.BitCoinService;
import com.sharingif.blockchain.crypto.api.bitcoin.service.BitCoinApiService;
import com.sharingif.blockchain.crypto.model.entity.SecretKey;
import com.sharingif.blockchain.crypto.service.SecretKeyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class BitCoinServiceImpl implements BitCoinService {

    private SecretKeyService secretKeyService;
    private BitCoinApiService bitCoinApiService;

    @Resource
    public void setSecretKeyService(SecretKeyService secretKeyService) {
        this.secretKeyService = secretKeyService;
    }
    @Resource
    public void setBitCoinApiService(BitCoinApiService bitCoinApiService) {
        this.bitCoinApiService = bitCoinApiService;
    }

    @Override
    public SignMessageRsp signMessage(SignMessageReq req) {

        SecretKey bitCoinChangeSecretKey = secretKeyService.getBitCoinChangeAddress();

        com.sharingif.blockchain.crypto.api.bitcoin.entity.SignMessageReq cryptoSignMessageReq = new com.sharingif.blockchain.crypto.api.bitcoin.entity.SignMessageReq();
        cryptoSignMessageReq.setChangeAddress(bitCoinChangeSecretKey.getAddress());
        cryptoSignMessageReq.setFee(req.getFee());

        List<com.sharingif.blockchain.crypto.api.bitcoin.entity.SignMessageVinReq> cryptoVinList = new ArrayList<>(req.getVinList().size());
        for(SignMessageVinReq signMessageVinReq : req.getVinList()) {
            SecretKey secretKey = secretKeyService.getSecretKeyByAddress(signMessageVinReq.getFromAddress());
            String password = secretKeyService.decryptPassword(secretKey.getPassword());

            com.sharingif.blockchain.crypto.api.bitcoin.entity.SignMessageVinReq cryptoSignMessageVinReq = new com.sharingif.blockchain.crypto.api.bitcoin.entity.SignMessageVinReq();
            cryptoSignMessageVinReq.setFromAddress(signMessageVinReq.getFromAddress());
            cryptoSignMessageVinReq.setPassword(password);

            List<SignMessageUnspentReq> unspentList = signMessageVinReq.getUnspentList();
            List<com.sharingif.blockchain.crypto.api.bitcoin.entity.SignMessageUnspentReq> cryptoUnspentList = new ArrayList<>(unspentList.size());
            for(SignMessageUnspentReq signMessageUnspentReq : unspentList) {
                com.sharingif.blockchain.crypto.api.bitcoin.entity.SignMessageUnspentReq cryptoSignMessageUnspentReq = new com.sharingif.blockchain.crypto.api.bitcoin.entity.SignMessageUnspentReq();
                cryptoSignMessageUnspentReq.setTxId(signMessageUnspentReq.getTxId());
                cryptoSignMessageUnspentReq.setVout(signMessageUnspentReq.getVout());
                cryptoSignMessageUnspentReq.setScriptPubKey(signMessageUnspentReq.getScriptPubKey());
                cryptoSignMessageUnspentReq.setAmount(signMessageUnspentReq.getAmount());

                cryptoUnspentList.add(cryptoSignMessageUnspentReq);
            }
            cryptoSignMessageVinReq.setUnspentList(cryptoUnspentList);
        }
        cryptoSignMessageReq.setVinList(cryptoVinList);

        List<SignMessageVoutReq> voutList = req.getVoutList();
        List<com.sharingif.blockchain.crypto.api.bitcoin.entity.SignMessageVoutReq> cryptoVoutList = new ArrayList<>(voutList.size());
        for(SignMessageVoutReq signMessageVoutReq : voutList) {
            com.sharingif.blockchain.crypto.api.bitcoin.entity.SignMessageVoutReq cryptoSignMessageVoutReq = new com.sharingif.blockchain.crypto.api.bitcoin.entity.SignMessageVoutReq();
            cryptoSignMessageVoutReq.setToAddress(signMessageVoutReq.getToAddress());
            cryptoSignMessageVoutReq.setAmount(signMessageVoutReq.getAmount());

            cryptoVoutList.add(cryptoSignMessageVoutReq);
        }
        cryptoSignMessageReq.setVoutList(cryptoVoutList);

        com.sharingif.blockchain.crypto.api.bitcoin.entity.SignMessageRsp cryptoSignMessageRsp = bitCoinApiService.signMessage(cryptoSignMessageReq);

        SignMessageRsp rsp = new SignMessageRsp();
        rsp.setHexValue(cryptoSignMessageRsp.getHexValue());

        return rsp;
    }

}
