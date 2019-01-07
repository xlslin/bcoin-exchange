package com.sharingif.blockchain.ether.service.impl;

import com.sharingif.blockchain.crypto.api.ether.entity.Erc20SignMessageReq;
import com.sharingif.blockchain.crypto.api.ether.entity.Erc20SignMessageRsp;
import com.sharingif.blockchain.crypto.api.ether.entity.SignMessageReq;
import com.sharingif.blockchain.crypto.api.ether.entity.SignMessageRsp;
import com.sharingif.blockchain.crypto.api.ether.service.EtherApiService;
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

    @Resource
    public void setEtherApiService(EtherApiService etherApiService) {
        this.etherApiService = etherApiService;
    }

    @Override
    public SignMessageRsp signMessage(SignMessageReq req) {
        return etherApiService.signMessage(req);
    }

    @Override
    public Erc20SignMessageRsp erc20SignMessage(Erc20SignMessageReq req) {

        return etherApiService.erc20SignMessage(req);
    }

}
