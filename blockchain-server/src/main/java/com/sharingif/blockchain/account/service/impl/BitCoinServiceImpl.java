package com.sharingif.blockchain.account.service.impl;


import javax.annotation.Resource;

import com.sharingif.blockchain.api.account.entity.BitCoinAddReq;
import org.springframework.stereotype.Service;

import com.sharingif.blockchain.account.model.entity.BitCoin;
import com.sharingif.blockchain.account.dao.BitCoinDAO;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import com.sharingif.blockchain.account.service.BitCoinService;

import java.util.List;

@Service
public class BitCoinServiceImpl extends BaseServiceImpl<BitCoin, java.lang.String> implements BitCoinService {
	
	private BitCoinDAO bitCoinDAO;

	public BitCoinDAO getBitCoinDAO() {
		return bitCoinDAO;
	}
	@Resource
	public void setBitCoinDAO(BitCoinDAO bitCoinDAO) {
		super.setBaseDAO(bitCoinDAO);
		this.bitCoinDAO = bitCoinDAO;
	}


	@Override
	public void add(BitCoinAddReq req) {
		BitCoin bitCoin = new BitCoin();
		bitCoin.setBlockType(req.getBlockType());
		bitCoin.setCoinType(req.getCoinType());
		bitCoin.setBip44CoinType(req.getBip44CoinType());
		bitCoin.setDecimals(req.getDecimals());

		bitCoinDAO.insert(bitCoin);
	}

	@Override
	public BitCoin getBitCoinByCoinType(String coinType) {
		BitCoin bitCoin = new BitCoin();
		bitCoin.setCoinType(coinType);

		return bitCoinDAO.query(bitCoin);
	}

	@Override
	public String getBlockTypeByBip44CoinType(String bip44CoinType) {
		BitCoin bitCoin = new BitCoin();
		bitCoin.setBip44CoinType(bip44CoinType);

		List<BitCoin> bitCoinList = bitCoinDAO.queryList(bitCoin);

		return bitCoinList.get(0).getBlockType();
	}

	@Override
	public BitCoin getBitCoinByAddress(String address) {
		return null;
	}
}
