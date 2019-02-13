package com.sharingif.blockchain.bitcoin.block.model.entity;

import org.bitcoincore.api.rawtransactions.entity.Transaction;

import java.math.BigInteger;

public class BlockTransaction {

    private BlockChain blockChain;
    private BigInteger txIndex;
    private Transaction transaction;

    public BlockChain getBlockChain() {
        return blockChain;
    }

    public void setBlockChain(BlockChain blockChain) {
        this.blockChain = blockChain;
    }

    public BigInteger getTxIndex() {
        return txIndex;
    }

    public void setTxIndex(BigInteger txIndex) {
        this.txIndex = txIndex;
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
        sb.append(", txIndex=").append(txIndex);
        sb.append(", transaction=").append(transaction);
        sb.append('}');
        return sb.toString();
    }
}
