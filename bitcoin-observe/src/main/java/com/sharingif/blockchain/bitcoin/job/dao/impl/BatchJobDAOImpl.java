package com.sharingif.blockchain.bitcoin.job.dao.impl;

import com.sharingif.blockchain.bitcoin.app.dao.impl.BaseDAOImpl;
import com.sharingif.blockchain.bitcoin.job.dao.BatchJobDAO;
import com.sharingif.blockchain.bitcoin.job.model.entity.BatchJob;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * JobDAOImpl
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/29 上午11:42
 */
@Repository
public class BatchJobDAOImpl extends BaseDAOImpl<BatchJob, String> implements BatchJobDAO {

    @Override
    public PaginationRepertory<BatchJob> queryPaginationListByPlanExecuteTimeStatus(PaginationCondition<BatchJob> paginationCondition) {
        return queryPagination("queryPaginationListByPlanExecuteTimeStatus", paginationCondition);
    }

    @Override
    public int updateStatusByStatus(String currentStatus, String updateStatus) {
        Map<String,String> queryParameter = new HashMap<String,String>();
        queryParameter.put("currentStatus", currentStatus);
        queryParameter.put("updateStatus", updateStatus);

        return super.update("updateStatusByStatus", queryParameter);
    }

}
