package org.omnilayer.api.rawtransactions.service;

import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import org.omnilayer.api.rawtransactions.entity.Transaction;

import java.math.BigInteger;

@RequestMapping(value="/")
public interface RawTransactionsApiService {

    /**
     * Decodes an Omni transaction.
     * If the inputs of the transaction are not in the chain, then they must be provided, because the transaction inputs are used to identify the sender of a transaction.
     * A block height can be provided, which is used to determine the parsing rules.
     * @param rawTx
     * @param prevtxs
     * @param height
     * @return
     */
    Transaction decodeTransaction(String rawTx, String prevtxs, BigInteger height);

}
