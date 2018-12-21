package com.sharingif.blockchain.ether.block.dao;


import com.sharingif.blockchain.ether.app.dao.BaseDAO;
import com.sharingif.blockchain.ether.block.model.entity.Contract;


public interface ContractDAO extends BaseDAO<Contract, String> {

    /**
     * 根据id加锁查询
     * @param id
     * @return
     */
    Contract queryByIdForUpdate(String id);

}
