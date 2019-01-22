package com.sharingif.blockchain.bitcoin.app.constants;

public enum ScriptTypes {

    PUB_KEY("pubkey"),
    PUB_KEY_HASH("pubkeyhash"),
    SCRIPT_HASH("scripthash"),
    MULTISIG("multisig"),
    NULL_DATA("nulldata"),
    NONSTANDARD("nonstandard");

    private final String name;

    ScriptTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
