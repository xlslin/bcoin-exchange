package com.sharingif.blockchain.bitcoin.block.service.impl;

import com.sharingif.blockchain.bitcoin.app.constants.Constants;
import com.sharingif.blockchain.bitcoin.block.service.BitCoinBlockService;
import com.sharingif.cube.core.exception.CubeRuntimeException;
import org.bitcoincore.api.blockchain.entity.Block;
import org.bitcoincore.api.rawtransactions.entity.Transaction;
import org.bitcoincore.api.blockchain.service.BlockChainApiService;
import org.bitcoincore.api.rawtransactions.service.RawTransactionsApiService;
import org.bitcoincore.api.wallet.entity.Unspent;
import org.bitcoincore.api.wallet.service.WalletApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@Service
public class BitCoinBlockServiceImpl implements BitCoinBlockService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private BlockChainApiService blockChainApiService;
    private RawTransactionsApiService rawTransactionsApiService;
    private WalletApiService walletApiService;

    @Resource
    public void setBlockChainApiService(BlockChainApiService blockChainApiService) {
        this.blockChainApiService = blockChainApiService;
    }
    @Resource
    public void setRawTransactionsApiService(RawTransactionsApiService rawTransactionsApiService) {
        this.rawTransactionsApiService = rawTransactionsApiService;
    }
    @Resource
    public void setWalletApiService(WalletApiService walletApiService) {
        this.walletApiService = walletApiService;
    }

    @Override
    public BigInteger getBlockNumber() {
        return blockChainApiService.getBlockCount();
    }

    @Override
    public String getBlockHash(BigInteger blockNumber) {
        return blockChainApiService.getBlockHash(blockNumber);
    }

    @Override
    public Block<List<String>> getBlock(String blockHash) {
        return blockChainApiService.getBlock(blockHash, 1);
    }

    @Override
    public Block<List<String>> getBlock(BigInteger blockNumber) {
        return getBlock(getBlockHash(blockNumber));
    }

    @Override
    public Block<List<Transaction>> getBlockFullTransaction(String blockHash) {
        return blockChainApiService.getBlockFullTransaction(blockHash, 2);
    }

    @Override
    public Transaction getFullRawTransaction(String txid) {
        return rawTransactionsApiService.getFullRawTransaction(txid, true, null);
    }

    @Override
    public void importAddress(String address, String label) {
        walletApiService.importAddress(address, label, false, false);
    }

    @Override
    public BigInteger getBalanceByAddress(String address) {
        BigInteger balance = BigInteger.ZERO;

        List<Unspent> unspentList = null;
        try {
            unspentList = walletApiService.listUnspent(2,9999999, Arrays.asList(address), true, null);
        } catch (Exception e) {
            logger.error("get unspent list error", e);
            throw new CubeRuntimeException(e);
        }
        if(unspentList == null) {
            return balance;
        }
        for(Unspent unspent : unspentList) {
            balance = balance.add(unspent.getAmount().multiply(Constants.BTC_UNIT).toBigInteger());
        }
        return balance;
    }

}
