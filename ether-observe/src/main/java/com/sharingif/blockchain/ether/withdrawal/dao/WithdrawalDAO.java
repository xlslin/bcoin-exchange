package com.sharingif.blockchain.ether.withdrawal.dao;


import com.sharingif.blockchain.ether.app.dao.BaseDAO;
import com.sharingif.blockchain.ether.withdrawal.model.entity.Withdrawal;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;


public interface WithdrawalDAO extends BaseDAO<Withdrawal, String> {

}
