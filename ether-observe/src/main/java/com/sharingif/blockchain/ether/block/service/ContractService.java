package com.sharingif.blockchain.ether.block.service;


import com.sharingif.blockchain.ether.block.model.entity.Contract;
import com.sharingif.cube.support.service.base.IBaseService;


public interface ContractService extends IBaseService<Contract, java.lang.String> {

    /**
     * 根据合约地址查询合约信息,如果不存在就插入合约信息
     * @param contractAddress
     * @return
     */
    Contract getContractAndAdd(String contractAddress);

}
