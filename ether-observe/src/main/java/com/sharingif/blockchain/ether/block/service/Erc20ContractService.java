package com.sharingif.blockchain.ether.block.service;

import org.web3j.abi.datatypes.Type;

import java.math.BigInteger;
import java.util.List;

public interface Erc20ContractService {

    /**
     *
     * @param txInput
     * @return
     */
    List<Type> getTransfer(String txInput);

    /**
     * 根据合约地址查询合约名称
     * @param contractAddress
     * @return
     */
    String name(String contractAddress);

    /**
     * 根据合约地址查询合约符号
     * @param contractAddress
     * @return
     */
    String symbol(String contractAddress);

    /**
     * 根据合约地址查询合约精度
     * @param contractAddress
     * @return
     */
    BigInteger decimals(String contractAddress);

    /**
     * 根据合约地址查询合约发行总量
     * @param contractAddress
     * @return
     */
    BigInteger totalSupply(String contractAddress);

}
