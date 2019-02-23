package com.sharingif.blockchain.bitcoin.block.service.impl;

import com.sharingif.blockchain.bitcoin.block.service.OmniBlockService;
import org.omnilayer.api.rawtransactions.entity.Transaction;
import org.omnilayer.api.rawtransactions.service.RawTransactionsApiService;
import org.omnilayer.api.wallet.entity.OmniBalance;
import org.omnilayer.api.wallet.service.WalletApiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;

@Service
public class OmniBlockServiceImpl implements OmniBlockService {

    private RawTransactionsApiService rawTransactionsApiService;
    private WalletApiService walletApiService;

    @Resource
    public void setRawTransactionsApiService(RawTransactionsApiService rawTransactionsApiService) {
        this.rawTransactionsApiService = rawTransactionsApiService;
    }
    @Resource
    public void setWalletApiService(WalletApiService walletApiService) {
        this.walletApiService = walletApiService;
    }

    @Override
    public Transaction decodeTransaction(String rawTx) {
        return rawTransactionsApiService.decodeTransaction(rawTx, null, null);
    }

    @Override
    public BigInteger getUsdtBalance(String address) {
        OmniBalance omniBalance = walletApiService.getBalance(address, 31);
        return new BigInteger(omniBalance.getBalance());
    }
}
