package com.sharingif.blockchain.crypto.bitcoin.service;

import com.sharingif.blockchain.crypto.api.bitcoin.entity.OmniSimpleSendSignMessageReq;
import com.sharingif.blockchain.crypto.api.bitcoin.entity.SignMessageReq;
import com.sharingif.blockchain.crypto.api.bitcoin.entity.SignMessageRsp;
import org.bitcoinj.core.NetworkParameters;

public interface BitCoinService {

    /**
     * 根据币种获取btc网络类型
     * @param coinType
     * @return
     */
    NetworkParameters getNetworkParameters(int coinType);

    /**
     * 转账签名
     * @param req
     * @return
     */
    SignMessageRsp signMessage(SignMessageReq req);

    /**
     * omni转账签名
     * @param req
     * @return
     */
    SignMessageRsp omniSimpleSendSignMessage(OmniSimpleSendSignMessageReq req);

}
