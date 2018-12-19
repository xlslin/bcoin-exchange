package com.sharingif.blockchain.ether.block.service;


import com.sharingif.blockchain.ether.block.model.entity.BlockChain;
import com.sharingif.cube.support.service.base.IBaseService;

import java.math.BigInteger;


public interface BlockChainService extends IBaseService<BlockChain, java.lang.String> {

    /**
     * 添加初始化区块数据
     * @param blockNumber
     * @param blockHash
     */
    void initializeBlockChain(BigInteger blockNumber, String blockHash);
	
}
