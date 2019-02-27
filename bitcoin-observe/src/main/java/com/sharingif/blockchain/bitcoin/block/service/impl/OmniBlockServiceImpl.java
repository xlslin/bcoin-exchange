package com.sharingif.blockchain.bitcoin.block.service.impl;

import com.sharingif.blockchain.bitcoin.block.service.OmniBlockService;
import org.omnilayer.api.rawtransactions.entity.Transaction;
import org.omnilayer.api.rawtransactions.service.OmniRawTransactionsApiService;
import org.omnilayer.api.wallet.entity.OmniBalance;
import org.omnilayer.api.wallet.service.OmniWalletApiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;

@Service
public class OmniBlockServiceImpl implements OmniBlockService {

    private OmniRawTransactionsApiService omniRawTransactionsApiService;
    private OmniWalletApiService omniWalletApiService;

    @Resource
    public void setOmniRawTransactionsApiService(OmniRawTransactionsApiService omniRawTransactionsApiService) {
        this.omniRawTransactionsApiService = omniRawTransactionsApiService;
    }
    @Resource
    public void setOmniWalletApiService(OmniWalletApiService omniWalletApiService) {
        this.omniWalletApiService = omniWalletApiService;
    }

    @Override
    public Transaction decodeTransaction(String rawTx) {
        return omniRawTransactionsApiService.decodeTransaction(rawTx, null, null);
    }

    @Override
    public BigInteger getUsdtBalance(String address) {
        OmniBalance omniBalance = omniWalletApiService.getBalance(address, 31);
        return new BigInteger(omniBalance.getBalance());
    }
}
