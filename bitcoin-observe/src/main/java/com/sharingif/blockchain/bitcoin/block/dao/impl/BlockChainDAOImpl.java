package com.sharingif.blockchain.bitcoin.block.dao.impl;


import com.sharingif.blockchain.bitcoin.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.bitcoin.block.dao.BlockChainDAO;
import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import org.springframework.stereotype.Repository;


@Repository
public class BlockChainDAOImpl extends BaseDAOImpl<BlockChain, String> implements BlockChainDAO {

    @Override
    public PaginationRepertory<BlockChain> queryPaginationListOrderByBlockNumberAsc(PaginationCondition<BlockChain> paginationCondition) {
        return queryPagination("queryPaginationListOrderByBlockNumberAsc", paginationCondition);
    }

    @Override
    public PaginationRepertory<BlockChain> queryPaginationListByBlockNumberStatus(PaginationCondition<BlockChain> paginationCondition) {
        return queryPagination("queryPaginationListByBlockNumberStatus", paginationCondition);
    }
	
}
