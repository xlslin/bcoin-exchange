package org.omnilayer.api.wallet.entity;

public class OmniBalance {

    private String balance;
    private String reserved;
    private String frozen;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getFrozen() {
        return frozen;
    }

    public void setFrozen(String frozen) {
        this.frozen = frozen;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OmniBalance{");
        sb.append("balance='").append(balance).append('\'');
        sb.append(", reserved='").append(reserved).append('\'');
        sb.append(", frozen='").append(frozen).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
