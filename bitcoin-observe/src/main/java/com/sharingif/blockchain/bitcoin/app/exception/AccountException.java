package com.sharingif.blockchain.bitcoin.app.exception;

import com.sharingif.cube.core.exception.validation.ValidationCubeException;

public class AccountException extends ValidationCubeException {

    public AccountException() {
        super("account exception");
    }

}
