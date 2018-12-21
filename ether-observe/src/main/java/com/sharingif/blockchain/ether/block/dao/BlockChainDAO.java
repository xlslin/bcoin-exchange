package com.sharingif.blockchain.ether.block.dao;


import com.sharingif.blockchain.ether.app.dao.BaseDAO;
import com.sharingif.blockchain.ether.block.model.entity.BlockChain;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;

import java.math.BigInteger;


public interface BlockChainDAO extends BaseDAO<BlockChain, String> {

    PaginationRepertory<BlockChain> queryPaginationListOrderByBlockNumberAsc(PaginationCondition<BlockChain> paginationCondition);

    PaginationRepertory<BlockChain> queryPaginationListByBlockNumberStatus(PaginationCondition<BlockChain> paginationCondition);

}
