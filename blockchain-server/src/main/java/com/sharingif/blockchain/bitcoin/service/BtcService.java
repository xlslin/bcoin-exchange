package com.sharingif.blockchain.bitcoin.service;

import com.sharingif.blockchain.api.bitcoin.entity.DepositWithdrawalNoticeReq;
import com.sharingif.blockchain.api.bitcoin.entity.SignMessageReq;
import com.sharingif.blockchain.api.bitcoin.entity.SignMessageRsp;

public interface BtcService {

    /**
     * 转账签名
     * @param req
     * @return
     */
    SignMessageRsp signMessage(SignMessageReq req);

    /**
     * 充值提现通知
     * @param req
     */
    void depositWithdrawalNotice(DepositWithdrawalNoticeReq req);

}
