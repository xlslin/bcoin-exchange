package org.bitcoincore.api.blockchain.service;


import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;

import java.math.BigInteger;

@RequestMapping(value="/")
public interface BlockChainApiService {

    /**
     * Returns the number of blocks in the longest blockchain.
     * @return
     */
    @RequestMapping(value="getblockcount", method= RequestMethod.POST)
    BigInteger getblockcount();


}
