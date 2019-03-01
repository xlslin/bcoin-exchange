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
    @JsonProperty("propertyid")
    private int propertyId;
    private Boolean divisible;
    private String amount;
    private Boolean valid;
    @JsonProperty("blockhash")
    private String blockHash;
    @JsonProperty("blocktime")
    private Long blockTime;
    @JsonProperty("positioninblock")
    private int positioninBlock;
    private Long block;
    private int confirmations;

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getSendingAddress() {
        return sendingAddress;
    }

    public void setSendingAddress(String sendingAddress) {
        this.sendingAddress = sendingAddress;
    }

    public String getReferenceAddress() {
        return referenceAddress;
    }

    public void setReferenceAddress(String referenceAddress) {
        this.referenceAddress = referenceAddress;
    }

    public Boolean getMine() {
        return isMine;
    }

    public void setMine(Boolean mine) {
        isMine = mine;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getTypeInt() {
        return typeInt;
    }

    public void setTypeInt(int typeInt) {
        this.typeInt = typeInt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public Boolean getDivisible() {
        return divisible;
    }

    public void setDivisible(Boolean divisible) {
        this.divisible = divisible;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public Long getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(Long blockTime) {
        this.blockTime = blockTime;
    }

    public int getPositioninBlock() {
        return positioninBlock;
    }

    public void setPositioninBlock(int positioninBlock) {
        this.positioninBlock = positioninBlock;
    }

    public Long getBlock() {
        return block;
    }

    public void setBlock(Long block) {
        this.block = block;
    }

    public int getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(int confirmations) {
        this.confirmations = confirmations;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("txId='").append(txId).append('\'');
        sb.append(", fee='").append(fee).append('\'');
        sb.append(", sendingAddress='").append(sendingAddress).append('\'');
        sb.append(", referenceAddress='").append(referenceAddress).append('\'');
        sb.append(", isMine=").append(isMine);
        sb.append(", version=").append(version);
        sb.append(", typeInt=").append(typeInt);
        sb.append(", type='").append(type).append('\'');
        sb.append(", propertyId=").append(propertyId);
        sb.append(", divisible=").append(divisible);
        sb.append(", amount='").append(amount).append('\'');
        sb.append(", valid=").append(valid);
        sb.append(", blockHash='").append(blockHash).append('\'');
        sb.append(", blockTime=").append(blockTime);
        sb.append(", positioninBlock=").append(positioninBlock);
        sb.append(", block=").append(block);
        sb.append(", confirmations=").append(confirmations);
        sb.append('}');
        return sb.toString();
    }
}
