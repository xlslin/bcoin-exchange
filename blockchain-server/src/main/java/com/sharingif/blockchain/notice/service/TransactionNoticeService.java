package com.sharingif.blockchain.notice.service;


import com.sharingif.blockchain.api.notice.entity.TransactionNoticeAddReq;
import com.sharingif.blockchain.notice.model.entity.TransactionNotice;
import com.sharingif.cube.support.service.base.IBaseService;


public interface TransactionNoticeService extends IBaseService<TransactionNotice, java.lang.String> {

    /**
     * 添加通知监听
     * @return
     */
    void add(TransactionNoticeAddReq req);

}
