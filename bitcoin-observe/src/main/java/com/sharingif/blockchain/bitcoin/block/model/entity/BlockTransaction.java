package com.sharingif.blockchain.bitcoin.block.model.entity;

import org.bitcoincore.api.rawtransactions.entity.Transaction;

public class BlockTransaction {

    private BlockChain blockChain;
    private Transaction transaction;

    public BlockChain getBlockChain() {
        return blockChain;
    }

    public void setBlockChain(BlockChain blockChain) {
        this.blockChain = blockChain;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BlockTransaction{");
        sb.append("blockChain=").append(blockChain);
        sb.append(", transaction=").append(transaction);
        sb.append('}');
        return sb.toString();
    }
}
