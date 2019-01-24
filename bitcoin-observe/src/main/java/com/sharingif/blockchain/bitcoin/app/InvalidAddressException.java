package com.sharingif.blockchain.bitcoin.app;

import com.sharingif.cube.core.exception.validation.ValidationCubeException;

public class InvalidAddressException extends ValidationCubeException {

    public InvalidAddressException() {
        super("invalid address");
    }
}
