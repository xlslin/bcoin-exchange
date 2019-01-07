package com.sharingif.blockchain.ether.app.exception;

import com.sharingif.cube.core.exception.validation.ValidationCubeException;

public class InvalidAddressException extends ValidationCubeException {

    public InvalidAddressException() {
        super("invalid address");
    }
}
