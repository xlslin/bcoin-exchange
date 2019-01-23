package com.sharingif.blockchain.bitcoin.block.dao;


import com.sharingif.blockchain.bitcoin.app.dao.BaseDAO;
import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;


public interface BlockChainDAO extends BaseDAO<BlockChain, String> {

    /**
     * 根据条件块正序排序查询
     * @param paginationCondition
     * @return
     */
    PaginationRepertory<BlockChain> queryPaginationListOrderByBlockNumberAsc(PaginationCondition<BlockChain> paginationCondition);

    /**
     * 根据小于等于块数、状态，块正序排序查询
     * @param paginationCondition
     * @return
     */
    PaginationRepertory<BlockChain> queryPaginationListByBlockNumberStatus(PaginationCondition<BlockChain> paginationCondition);
}
