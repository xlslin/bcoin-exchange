package org.omnilayer.api.rawtransactions.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {

    @JsonProperty("txid")
    private String txId;                    // (string) the hex-encoded hash of the transaction
    private String fee;                     // (string) the transaction fee in bitcoins
    @JsonProperty("sendingaddress")
    private String sendingAddress;          // (string) the Bitcoin address of the sender
    @JsonProperty("referenceaddress")
    private String referenceAddress;        // (string) a Bitcoin address used as reference (if any)
    @JsonProperty("ismine")
    private Boolean isMine;                 // (boolean) whether the transaction involes an address in the wallet
    private int version;                    // (number) the transaction version
    @JsonProperty("type_int")
    private int typeInt;                    // (number) the transaction type as number
    private String type;                    // (string) the transaction type as string

}
