package org.omnilayer.api.rawtransactions.service;

import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.omnilayer.api.rawtransactions.entity.Transaction;

import java.math.BigInteger;

@RequestMapping(value="/")
public interface OmniRawTransactionsApiService {

    /**
     * Get detailed information about an Omni transaction.
     * @param txId
     * @return
     */
    @RequestMapping(value="omni_gettransaction", method= RequestMethod.POST)
    Transaction getTransaction(String txId);

}
