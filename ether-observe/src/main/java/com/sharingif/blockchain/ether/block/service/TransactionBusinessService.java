package com.sharingif.blockchain.ether.block.service;


import com.sharingif.blockchain.ether.block.model.entity.TransactionBusiness;
import com.sharingif.cube.support.service.base.IBaseService;


public interface TransactionBusinessService extends IBaseService<TransactionBusiness, java.lang.String> {

    /**
     * 修改状态为初始化通知中
     * @param id
     * @return
     */
    int updateStatusToInitNotice(String id);

    /**
     * 修改状态为初始化通知成功
     * @param id
     * @return
     */
    int updateStatusToInitNoticed(String id);

}
