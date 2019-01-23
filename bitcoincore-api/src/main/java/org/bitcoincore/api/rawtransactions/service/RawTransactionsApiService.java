package org.bitcoincore.api.rawtransactions.service;

import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.bitcoincore.api.rawtransactions.entity.Transaction;

@RequestMapping(value="/")
public interface RawTransactionsApiService {

    /**
     * 返回交易信息
     * If verbose is 'true', returns an Object with information about 'txid'.
     * If verbose is 'false' or omitted, returns a string that is serialized, hex-encoded data for 'txid'.
     * @param txid
     * @param verbose
     * @param blockhash
     */
    @RequestMapping(value="getrawtransaction", method= RequestMethod.POST)
    Transaction getFullRawTransaction(String txid, boolean verbose, String blockhash);

}
