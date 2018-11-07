package com.sharingif.blockchain.sys.service;


import com.sharingif.blockchain.api.sys.entity.CurrentChangeExtendedKeyReq;
import com.sharingif.blockchain.sys.model.entity.SysPrm;
import com.sharingif.cube.support.service.base.IBaseService;


public interface SysPrmService extends IBaseService<SysPrm, java.lang.String> {

    /**
     * 根据币种查询当前生成地址ExtendedKey
     * @param coinType
     * @return
     */
    String getCurrentChangeExtendedKeyByCoinType(String coinType);

    /**
     * 设置当前生成地址ExtendedKey
     * @param req
     */
    void setCurrentChangeExtendedKey(CurrentChangeExtendedKeyReq req);

}
