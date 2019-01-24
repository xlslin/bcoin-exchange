package com.sharingif.blockchain.bitcoin.api.withdrawal.entity;

/**
 * BitCoin区块取现申请响应
 */
public class ApplyWithdrawalBitCoinRsp {

    /**
     * id
     */
    private String id;
    /**
     * 取现唯一编号
     */
    private String withdrawalId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWithdrawalId() {
        return withdrawalId;
    }

    public void setWithdrawalId(String withdrawalId) {
        this.withdrawalId = withdrawalId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WithdrawalEtherRsp{");
        sb.append("id='").append(id).append('\'');
        sb.append(", withdrawalId='").append(withdrawalId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
