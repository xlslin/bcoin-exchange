package org.bitcoincore.api.rawtransactions.entity;

public class SignRawTransaction {

    private String hex;
    private Boolean complete;
    private SignRawTransactionErrors errors;

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public SignRawTransactionErrors getErrors() {
        return errors;
    }

    public void setErrors(SignRawTransactionErrors errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SignRawTransaction{");
        sb.append("hex='").append(hex).append('\'');
        sb.append(", complete=").append(complete);
        sb.append(", errors=").append(errors);
        sb.append('}');
        return sb.toString();
    }
}
