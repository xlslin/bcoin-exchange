package com.sharingif.blockchain.crypto.service.impl;

import com.sharingif.blockchain.api.crypto.entity.BIP44AddressIndexReq;
import com.sharingif.blockchain.api.crypto.entity.BIP44AddressIndexRsp;
import com.sharingif.blockchain.crypto.dao.SecretKeyDAO;
import com.sharingif.blockchain.crypto.model.entity.SecretKey;
import com.sharingif.blockchain.crypto.service.SecretKeyService;
import com.sharingif.blockchain.sys.service.SysPrmService;
import com.sharingif.cube.core.util.StringUtils;
import com.sharingif.cube.support.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SecretKeyServiceImpl
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/7/25 下午7:28
 */
@Service
public class SecretKeyServiceImpl extends BaseServiceImpl<SecretKey, String> implements SecretKeyService {

    private SecretKeyDAO secretKeyDAO;

    private SysPrmService sysPrmService;

    @Resource
    public void setSecretKeyDAO(SecretKeyDAO secretKeyDAO) {
        super.setBaseDAO(secretKeyDAO);
        this.secretKeyDAO = secretKeyDAO;
    }

    @Resource
    public void setSysPrmService(SysPrmService sysPrmService) {
        this.sysPrmService = sysPrmService;
    }

    @Override
    public BIP44AddressIndexRsp addressIndex(BIP44AddressIndexReq req) {
        // 获取配置ExtendedKeyId，如果请求中没有就取数据库中配置的默认值
        String changeExtendedKeyId = req.getChangeExtendedKeyId();
        if(StringUtils.isTrimEmpty(changeExtendedKeyId)) {
            int coinType = req.getCoinType();
            changeExtendedKeyId = sysPrmService.getCurrentChangeExtendedKey(req.getCoinType());
        }

        return null;
    }
}
