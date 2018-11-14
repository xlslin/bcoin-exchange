package com.sharingif.blockchain.crypto.app.autoconfigure.components;

import com.sharingif.blockchain.crypto.app.components.Keystore;
import com.sharingif.cube.security.binary.Base64Coder;
import com.sharingif.cube.security.confidentiality.encrypt.TextEncryptor;
import com.sharingif.cube.security.confidentiality.encrypt.aes.AESECBEncryptor;
import com.sharingif.cube.security.confidentiality.encrypt.digest.SHA256Encryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;

@Configuration
public class ComponentsAutoconfigure {

    @Bean(name="keystore")
    public Keystore createKeystore(@Value("${key.root.path}")String keyRootPath, SHA256Encryptor sha256Encryptor, Base64Coder base64Coder) {
        Keystore keystore = new Keystore(keyRootPath, sha256Encryptor, base64Coder);

        return keystore;
    }

    @Bean("propertyTextEncryptor")
    public TextEncryptor createPropertyTextEncryptor(@Value("${property.key}") String key, Base64Coder base64Coder) throws UnsupportedEncodingException {
        byte[] keysByte = base64Coder.decode(key);
        AESECBEncryptor encryptor = new AESECBEncryptor(keysByte, base64Coder);

        return encryptor;
    }

}
