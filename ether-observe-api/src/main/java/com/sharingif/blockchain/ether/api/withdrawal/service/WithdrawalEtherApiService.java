package com.sharingif.blockchain.ether.api.withdrawal.service;

import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherReq;
import com.sharingif.blockchain.ether.api.withdrawal.entity.WithdrawalEtherRsp;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;

@RequestMapping(value="withdrawal")
public interface WithdrawalEtherApiService {

    /**
     * eth取现申请
     * @param req
     * @return
     */
    @RequestMapping(value="ether", method= RequestMethod.POST)
    WithdrawalEtherRsp ether(WithdrawalEtherReq req);

}
