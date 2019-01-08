package com.sharingif.blockchain.withdrawal.service;


import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherReq;
import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherRsp;

public interface WithdrawalService {

    /**
     * 添加ether取现
     * @param req
     * @return
     */
    WithdrawalEtherRsp ether(WithdrawalEtherReq req);

}
