package com.sharingif.blockchain.bitcoin.block.service.impl;

import com.sharingif.blockchain.bitcoin.app.constants.Constants;
import com.sharingif.blockchain.bitcoin.block.service.OmniBlockService;
import org.omnilayer.api.rawtransactions.entity.Transaction;
import org.omnilayer.api.rawtransactions.service.OmniRawTransactionsApiService;
import org.omnilayer.api.wallet.entity.OmniBalance;
import org.omnilayer.api.wallet.service.OmniWalletApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class OmniBlockServiceImpl implements OmniBlockService {

    private int omniUsdtProperty;
    private OmniRawTransactionsApiService omniRawTransactionsApiService;
    private OmniWalletApiService omniWalletApiService;

    @Value("${omni.usdt.property}")
    public void setOmniUsdtProperty(int omniUsdtProperty) {
        this.omniUsdtProperty = omniUsdtProperty;
    }
    @Resource
    public void setOmniRawTransactionsApiService(OmniRawTransactionsApiService omniRawTransactionsApiService) {
        this.omniRawTransactionsApiService = omniRawTransactionsApiService;
    }
    @Resource
    public void setOmniWalletApiService(OmniWalletApiService omniWalletApiService) {
        this.omniWalletApiService = omniWalletApiService;
    }

    @Override
    public Transaction getTransaction(String txId) {
        return omniRawTransactionsApiService.getTransaction(txId);
    }

    @Override
    public void importAddress(String address, String label) {
        omniWalletApiService.importAddress(address, label, false, false);
    }

    @Override
    public BigInteger getUsdtBalance(String address) {
        OmniBalance omniBalance = omniWalletApiService.getBalance(address, omniUsdtProperty);
        return new BigDecimal(omniBalance.getBalance()).multiply(Constants.BTC_UNIT).toBigInteger();
    }
}
