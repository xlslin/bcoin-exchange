package com.sharingif.blockchain.signature.service.impl;

import com.sharingif.blockchain.signature.service.BlockchainSignatureService;
import com.sharingif.cube.core.exception.UnknownCubeException;
import com.sharingif.cube.security.binary.Base64Coder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.Signature;

@Service
public class BlockchainSignatureServiceImpl implements BlockchainSignatureService {

    private Signature blockchainSignature;
    private Base64Coder base64Coder;

    @Resource
    public void setBlockchainSignature(Signature blockchainSignature) {
        this.blockchainSignature = blockchainSignature;
    }
    @Resource
    public void setBase64Coder(Base64Coder base64Coder) {
        this.base64Coder = base64Coder;
    }

    @Override
    public String signature(byte[] signData) {
        try {
            blockchainSignature.update(signData);
            byte[] result = blockchainSignature.sign();
            return base64Coder.encode(result);
        } catch (Exception e) {
            throw new UnknownCubeException(e);
        }
    }
}
