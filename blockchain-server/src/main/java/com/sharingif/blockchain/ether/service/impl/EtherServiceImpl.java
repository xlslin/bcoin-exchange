package com.sharingif.blockchain.ether.service.impl;

import com.sharingif.blockchain.crypto.api.ether.entity.Erc20TransferReq;
import com.sharingif.blockchain.crypto.api.ether.entity.Erc20TransferRsp;
import com.sharingif.blockchain.crypto.api.ether.entity.EtherTransferReq;
import com.sharingif.blockchain.crypto.api.ether.entity.EtherTransferRsp;
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
    public EtherTransferRsp transfer(EtherTransferReq req) {
        return etherApiService.transfer(req);
    }

    @Override
    public Erc20TransferRsp erc20Transfer(Erc20TransferReq req) {

        return etherApiService.erc20Transfer(req);
    }

}
