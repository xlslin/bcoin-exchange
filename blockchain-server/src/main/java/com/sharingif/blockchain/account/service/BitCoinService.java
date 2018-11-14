package com.sharingif.blockchain.account.service;


import com.sharingif.blockchain.account.model.entity.BitCoin;
import com.sharingif.blockchain.api.account.entity.BitCoinAddReq;
import com.sharingif.cube.support.service.base.IBaseService;


public interface BitCoinService extends IBaseService<BitCoin, java.lang.String> {

    /**
     * 添加币种
     * @param req
     */
    void add(BitCoinAddReq req);

    /**
     * 根据币种获取币种详细信息
     * @param coinType
     * @return
     */
    BitCoin getBitCoinByCoinType(String coinType);

    /**
     * 根据BIP44币种获取币种区块类型
     * @param bip44CoinType
     * @return
     */
    String getBlockTypeByBip44CoinType(String bip44CoinType);

    /**
     * 根据IP44币种获取币种类型
     * @param bip44CoinType
     * @return
     */
    String getCoinTypeByBip44CoinType(String bip44CoinType);

}
