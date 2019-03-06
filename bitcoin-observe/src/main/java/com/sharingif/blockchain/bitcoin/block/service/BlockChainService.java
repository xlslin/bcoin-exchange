package com.sharingif.blockchain.bitcoin.block.service;


import com.sharingif.blockchain.bitcoin.block.model.entity.BlockChain;
import com.sharingif.blockchain.bitcoin.block.model.entity.BlockTransaction;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import com.sharingif.cube.support.service.base.IBaseService;
import org.bitcoincore.api.rawtransactions.entity.Transaction;

import java.math.BigInteger;
import java.util.List;
import java.util.Queue;


public interface BlockChainService extends IBaseService<BlockChain, java.lang.String> {

    /**
     * 当前块交易队列
     * @return
     */
    Queue<Transaction> getBlockTransactionQueue();

    /**
     * 添加未处理状态区块数据
     * @param blockNumber
     * @param blockHash
     * @param blockCreateTime
     */
    void addUntreatedStatus(BigInteger blockNumber, String blockHash, BigInteger blockCreateTime);

    /**
     * 修改状态为未验证
     * @param id
     */
    void updateStatusToUnverified(String id);

    /**
     * 修改状态为区块验证中
     * @param id
     */
    void updateStatusToVerifying(String id);

    /**
     * 修改状态为区块验证有效
     * @param id
     * @param verifyBlockNumber
     */
    void updateStatusToVerifyValid(String id, BigInteger verifyBlockNumber);

    /**
     * 修改状态为区块验证无效
     * @param id
     * @param verifyBlockNumber
     */
    void updateStatusToVerifyInvalid(String id, BigInteger verifyBlockNumber);

    /**
     * 根据条件块正序排序查询
     * @param paginationCondition
     * @return
     */
    PaginationRepertory<BlockChain> getPaginationListOrderByBlockNumberAsc(PaginationCondition<BlockChain> paginationCondition);

    /**
     * 根据小于等于块数、状态，块正序排序查询
     * @param paginationCondition
     * @return
     */
    PaginationRepertory<BlockChain> getPaginationListByBlockNumberStatus(PaginationCondition<BlockChain> paginationCondition);

    /**
     * 准备块数据同步
     * @param blockChainList
     */
    void readySyncData(List<BlockChain> blockChainList);

    /**
     * 块数据同步中
     * @param blockTransaction
     */
    void synchingData(BlockTransaction blockTransaction);

    /**
     * 准备验证块是否有效
     * @param blockChainList
     */
    void readyValidateBolck(List<BlockChain> blockChainList);

    /**
     * 验证块是否有效
     * @param blockChainId
     */
    void validateBolck(String blockChainId);

}
