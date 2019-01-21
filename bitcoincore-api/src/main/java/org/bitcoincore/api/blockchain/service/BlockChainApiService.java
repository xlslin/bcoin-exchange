package org.bitcoincore.api.blockchain.service;


import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.bitcoincore.api.blockchain.entity.GetBlockRsp;

import java.math.BigInteger;

@RequestMapping(value="/")
public interface BlockChainApiService {

    /**
     * Returns the number of blocks in the longest blockchain.
     * @return
     */
    @RequestMapping(value="getblockcount", method= RequestMethod.POST)
    BigInteger getBlockCount();

    /**
     * 根据区块号查询区块hash
     * @param height
     * @return
     */
    @RequestMapping(value="getblockhash", method= RequestMethod.POST)
    String getBlockHash(BigInteger height);

    /**
     * 根据哈希查询区块信息
     * @param blockhash
     * @param verbosity
     */
    @RequestMapping(value="getblock", method= RequestMethod.POST)
    GetBlockRsp getBlock(String blockhash, int verbosity);

}
