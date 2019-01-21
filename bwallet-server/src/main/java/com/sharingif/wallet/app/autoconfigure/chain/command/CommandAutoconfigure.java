package com.sharingif.wallet.app.autoconfigure.chain.command;

import com.sharingif.cube.security.confidentiality.encrypt.TextEncryptor;
import com.sharingif.wallet.app.chain.command.HttpRequestPasswordEncryptorCommand;
import com.sharingif.wallet.app.chain.command.OleMerchantVerifySignCommand;
import com.sharingif.wallet.app.chain.command.UserLoginRetunObjectCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandAutoconfigure {

    @Bean("httpRequestPasswordEncryptorCommand")
    public HttpRequestPasswordEncryptorCommand createHttpRequestPasswordEncryptorCommand(TextEncryptor bcryptTextEncryptor) {
        HttpRequestPasswordEncryptorCommand httpRequestPasswordEncryptorCommand = new HttpRequestPasswordEncryptorCommand();
        httpRequestPasswordEncryptorCommand.setTextEncryptor(bcryptTextEncryptor);

        return httpRequestPasswordEncryptorCommand;
    }

    @Bean("userLoginRetunObjectCommand")
    public UserLoginRetunObjectCommand createUserLoginRetunObjectCommand() {
        return new UserLoginRetunObjectCommand();
    }

}
