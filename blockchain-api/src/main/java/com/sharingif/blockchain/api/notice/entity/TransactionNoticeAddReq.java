package com.sharingif.blockchain.api.notice.entity;

public class TransactionNoticeAddReq {

    /**
     * 区块类型(Bitcoin、Ether)
     */
    private String blockType;
    /**
     * 通知类型(DEPOSIT:充值、WITHDRAWAL:取现)
     */
    private String noticeType;
    /**
     * 通知地址
     */
    private String noticeAddress;


    public String getBlockType() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeAddress() {
        return noticeAddress;
    }

    public void setNoticeAddress(String noticeAddress) {
        this.noticeAddress = noticeAddress;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TransactionNoticeAddReq{");
        sb.append("blockType='").append(blockType).append('\'');
        sb.append(", noticeType='").append(noticeType).append('\'');
        sb.append(", noticeAddress='").append(noticeAddress).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
