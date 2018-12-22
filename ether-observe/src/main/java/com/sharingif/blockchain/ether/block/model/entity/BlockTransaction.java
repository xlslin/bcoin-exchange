package com.sharingif.blockchain.ether.block.model.entity;


import com.sharingif.cube.batch.core.request.JobRequest;
import org.web3j.protocol.core.methods.response.Transaction;

import java.util.Objects;

public class BlockTransaction {

    /**
     * 块信息
     */
    private BlockChain blockChain;
    /**
     * 块交易信息
     */
    private Transaction Transaction;

    public BlockChain getBlockChain() {
        return blockChain;
    }

    public void setBlockChain(BlockChain blockChain) {
        this.blockChain = blockChain;
    }

    public org.web3j.protocol.core.methods.response.Transaction getTransaction() {
        return Transaction;
    }

    public void setTransaction(org.web3j.protocol.core.methods.response.Transaction transaction) {
        Transaction = transaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockTransaction that = (BlockTransaction) o;
        return Objects.equals(blockChain.getId(), that.blockChain.getId()) &&
                Objects.equals(Transaction.getHash(), that.Transaction.getHash());
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockChain.getId(), Transaction.getHash());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BlockTransaction{");
        sb.append("blockChain=").append(blockChain);
        sb.append(", Transaction=").append(Transaction);
        sb.append('}');
        return sb.toString();
    }
}
