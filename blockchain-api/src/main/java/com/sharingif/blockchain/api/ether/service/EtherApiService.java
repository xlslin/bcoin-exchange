package com.sharingif.blockchain.api.ether.service;

import com.sharingif.blockchain.api.ether.entity.*;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;

/**
 * EthService
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/26 下午12:57
 */
@RequestMapping(value="ether")
public interface EtherApiService {

    /**
     * 转账签名
     * @param req
     * @return
     */
    @RequestMapping(value="signMessage", method= RequestMethod.POST)
    SignMessageRsp signMessage(SignMessageReq req);

    /**
     * Erc20转账签名
     * @param req
     * @return
     */
    @RequestMapping(value="erc20SignMessage", method= RequestMethod.POST)
    Erc20SignMessageRsp erc20SignMessage(Erc20SignMessageReq req);

    /**
     * 充值提现通知
     * @param req
     */
    @RequestMapping(value="depositWithdrawalNotice", method= RequestMethod.POST)
    void depositWithdrawalNotice(DepositWithdrawalNoticeReq req);

}
