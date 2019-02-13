package com.sharingif.blockchain.app.autoconfigure.components;

import com.sharingif.cube.security.binary.Base64Coder;
import com.sharingif.cube.security.confidentiality.encrypt.TextEncryptor;
import com.sharingif.cube.security.confidentiality.encrypt.aes.AESECBEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sun.security.rsa.RSAPrivateCrtKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Configuration
public class ComponentsAutoconfigure {

    @Bean("propertyTextEncryptor")
    public TextEncryptor createPropertyTextEncryptor(@Value("${property.key}") String key, Base64Coder base64Coder) throws UnsupportedEncodingException {
        byte[] keysByte = base64Coder.decode(key);
        AESECBEncryptor encryptor = new AESECBEncryptor(keysByte, base64Coder);

        return encryptor;
    }

    @Bean("passwordTextEncryptor")
    public TextEncryptor createPasswordTextEncryptor(@Value("${password.key}") String key, Base64Coder base64Coder) {
        byte[] keysByte = base64Coder.decode(key);
        AESECBEncryptor encryptor = new AESECBEncryptor(keysByte, base64Coder);

        return encryptor;
    }

    @Bean(name = "blockchainSignature")
    public Signature createBlockchainSignature(
            @Value("${blockchain.private.key}")String blockchainPrivateKey
            ,Base64Coder base64Coder
    ) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        RSAPrivateKey rsaPrivateKey = RSAPrivateCrtKeyImpl.newKey(base64Coder.decode(blockchainPrivateKey));

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(privateKey);

        return signature;
    }

}
