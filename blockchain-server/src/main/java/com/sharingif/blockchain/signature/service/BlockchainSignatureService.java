package com.sharingif.blockchain.signature.service;

public interface BlockchainSignatureService {

    /**
     * 签名数据
     * @param signData
     * @return
     */
    String signature(byte[] signData);

}
