package com.sharingif.blockchain.ether.service.impl;

import com.sharingif.blockchain.api.ether.entity.Erc20SignMessageReq;
import com.sharingif.blockchain.api.ether.entity.Erc20SignMessageRsp;
import com.sharingif.blockchain.api.ether.entity.SignMessageReq;
import com.sharingif.blockchain.api.ether.entity.SignMessageRsp;
import com.sharingif.blockchain.crypto.api.ether.service.EtherApiService;
import com.sharingif.blockchain.crypto.model.entity.SecretKey;
import com.sharingif.blockchain.crypto.service.SecretKeyService;
import com.sharingif.blockchain.ether.service.EtherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    private EtherApiService etherApiService;
    private SecretKeyService secretKeyService;

    @Resource
    public void setEtherApiService(EtherApiService etherApiService) {
        this.etherApiService = etherApiService;
    }
    @Resource
    public void setSecretKeyService(SecretKeyService secretKeyService) {
        this.secretKeyService = secretKeyService;
    }

    @Override
    public SignMessageRsp signMessage(SignMessageReq req) {
        SecretKey secretKey = secretKeyService.getSecretKeyByAddress(req.getFromAddress());
        String password = secretKeyService.decryptPassword(secretKey.getPassword());

        com.sharingif.blockchain.crypto.api.ether.entity.SignMessageReq signMessageReq = new com.sharingif.blockchain.crypto.api.ether.entity.SignMessageReq();
        signMessageReq.setNonce(req.getNonce());
        signMessageReq.setToAddress(req.getToAddress());
        signMessageReq.setAmount(req.getAmount());
        signMessageReq.setGasPrice(req.getGasPrice());
        signMessageReq.setFromAddress(secretKey.getAddress());
        signMessageReq.setPassword(password);

        com.sharingif.blockchain.crypto.api.ether.entity.SignMessageRsp signMessageRsp = etherApiService.signMessage(signMessageReq);

        SignMessageRsp rsp = new SignMessageRsp();
        rsp.setHexValue(signMessageRsp.getHexValue());

        return rsp;
    }

    @Override
    public Erc20SignMessageRsp erc20SignMessage(Erc20SignMessageReq req) {
        SecretKey secretKey = secretKeyService.getSecretKeyByAddress(req.getFromAddress());
        String password = secretKeyService.decryptPassword(secretKey.getPassword());

        com.sharingif.blockchain.crypto.api.ether.entity.Erc20SignMessageReq erc20SignMessageReq = new com.sharingif.blockchain.crypto.api.ether.entity.Erc20SignMessageReq();
        erc20SignMessageReq.setNonce(req.getNonce());
        erc20SignMessageReq.setToAddress(req.getToAddress());
        erc20SignMessageReq.setContractAddress(req.getContractAddress());
        erc20SignMessageReq.setAmount(req.getAmount());
        erc20SignMessageReq.setGasPrice(req.getGasPrice());
        erc20SignMessageReq.setGasLimit(req.getGasLimit());
        erc20SignMessageReq.setFromAddress(secretKey.getAddress());
        erc20SignMessageReq.setPassword(password);

        com.sharingif.blockchain.crypto.api.ether.entity.Erc20SignMessageRsp erc20SignMessageRsp = etherApiService.erc20SignMessage(erc20SignMessageReq);

        Erc20SignMessageRsp rsp = new Erc20SignMessageRsp();
        rsp.setHexValue(erc20SignMessageRsp.getHexValue());

        return rsp;
    }

}
