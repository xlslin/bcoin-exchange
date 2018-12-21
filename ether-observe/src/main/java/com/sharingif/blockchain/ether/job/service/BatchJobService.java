package com.sharingif.blockchain.ether.job.service;


import com.sharingif.blockchain.ether.job.model.entity.BatchJob;
import com.sharingif.cube.support.service.base.IBaseService;

import java.util.List;

/**
 * BatchJobService
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/29 下午5:57
 */
public interface BatchJobService extends IBaseService<BatchJob, String> {

    /**
     * 查询待处理Job
     * @param queryJobSize
     * @return
     */
    List<BatchJob> getSuspendingStatus(int queryJobSize);

    /**
     * 修改job状态为队列中
     * @param id
     * @return
     */
    int updateStatusToInQueue(String id);

    /**
     * 修改job状态为处理中
     * @param id
     * @return
     */
    int updateStatusToHandling(String id);

    /**
     * 修改队列中状态为待处理
     * @return
     */
    int updateJobStatusInQueueToPending();

    /**
     * 修改区块同步job状态为待处理
     * @return
     */
    int updateBlockChainSyncDataHandlingToPending();

}
