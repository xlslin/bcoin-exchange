package com.sharingif.wallet.app.autoconfigure.chain.command;

import com.sharingif.wallet.app.chain.command.UserLoginRetunObjectCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandAutoconfigure {

    @Bean("userLoginRetunObjectCommand")
    public UserLoginRetunObjectCommand createUserLoginRetunObjectCommand() {
        return new UserLoginRetunObjectCommand();
    }

}
