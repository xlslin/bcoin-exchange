package org.bitcoincore.api.wallet.entity;

public class ListUnspentQueryOptions {

    private Integer minimumAmount;
    private Integer maximumAmount;
    private Integer maximumCount;
    private Integer minimumSumAmount;

    public Integer getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(Integer minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public Integer getMaximumAmount() {
        return maximumAmount;
    }

    public void setMaximumAmount(Integer maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    public Integer getMaximumCount() {
        return maximumCount;
    }

    public void setMaximumCount(Integer maximumCount) {
        this.maximumCount = maximumCount;
    }

    public Integer getMinimumSumAmount() {
        return minimumSumAmount;
    }

    public void setMinimumSumAmount(Integer minimumSumAmount) {
        this.minimumSumAmount = minimumSumAmount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ListUnspentQueryOptions{");
        sb.append("minimumAmount=").append(minimumAmount);
        sb.append(", maximumAmount=").append(maximumAmount);
        sb.append(", maximumCount=").append(maximumCount);
        sb.append(", minimumSumAmount=").append(minimumSumAmount);
        sb.append('}');
        return sb.toString();
    }
}
