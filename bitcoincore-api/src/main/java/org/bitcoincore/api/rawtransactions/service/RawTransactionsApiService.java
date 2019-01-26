package org.bitcoincore.api.rawtransactions.service;

import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.bitcoincore.api.rawtransactions.entity.SignRawTransaction;
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

    /**
     * 签名交易
     * @param hexstring
     * @param prevtxs
     * @param privkeys
     * @param sighashtype
     */
    @RequestMapping(value="signrawtransaction", method= RequestMethod.POST)
    SignRawTransaction signRawTransaction(String hexstring, String prevtxs, String privkeys, String sighashtype);

    /**
     * Submits raw transaction (serialized, hex-encoded) to local node and network.
     * Also see createrawtransaction and signrawtransaction calls.
     * @param hexstring
     * @param allowhighfees
     * @return
     */
    @RequestMapping(value="sendrawtransaction", method= RequestMethod.POST)
    String sendRawTransaction(String hexstring, boolean allowhighfees);

}
