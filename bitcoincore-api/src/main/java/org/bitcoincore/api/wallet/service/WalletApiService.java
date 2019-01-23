package org.bitcoincore.api.wallet.service;

import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.bitcoincore.api.wallet.entity.ListUnspentQueryOptions;
import org.bitcoincore.api.wallet.entity.Unspent;

import java.util.List;

@RequestMapping(value="/")
public interface WalletApiService {

    /**
     * Adds a script (in hex) or address that can be watched as if it were in your wallet but cannot be used to spend. Requires a new wallet backup.
     * @param script
     * @param label
     * @param rescan
     * @param p2sh
     */
    @RequestMapping(value="importaddress", method= RequestMethod.POST)
    void importAddress(String script, String label, boolean rescan, boolean p2sh);

    /**
     * Returns array of unspent transaction outputs
     * with between minconf and maxconf (inclusive) confirmations.
     * Optionally filter to only include txouts paid to specified addresses.
     * @param minconf
     * @param maxconf
     * @param addresses
     * @param includeUnsafe
     * @param queryOptions
     */
    @RequestMapping(value="listunspent", method= RequestMethod.POST)
    List<Unspent> listUnspent(int minconf, int maxconf, List<String> addresses, boolean includeUnsafe, ListUnspentQueryOptions queryOptions);

}
