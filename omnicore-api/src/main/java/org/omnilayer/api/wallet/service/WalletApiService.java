package org.omnilayer.api.wallet.service;

import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import org.omnilayer.api.wallet.entity.OmniBalance;

@RequestMapping(value="/")
public interface WalletApiService {

    /**
     * Returns the token balance for a given address and property.
     * @param address
     * @param propertyId
     */
    @RequestMapping(value="omni_getbalance", method= RequestMethod.POST)
    OmniBalance getBalance(String address, int propertyId);

}
