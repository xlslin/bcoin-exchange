package org.omnilayer.api.wallet.service;

import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.omnilayer.api.wallet.entity.OmniBalance;

@RequestMapping(value="/")
public interface OmniWalletApiService {

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
     * Returns the token balance for a given address and property.
     * @param address
     * @param propertyId
     */
    @RequestMapping(value="omni_getbalance", method= RequestMethod.POST)
    OmniBalance getBalance(String address, int propertyId);

}
