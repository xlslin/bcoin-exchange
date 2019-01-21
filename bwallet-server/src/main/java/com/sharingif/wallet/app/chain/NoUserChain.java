package com.sharingif.wallet.app.chain;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.AbstractHandlerMethodChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.util.StringUtils;
import com.sharingif.cube.security.exception.validation.access.NoUserAccessDecisionCubeException;
import com.sharingif.cube.web.springmvc.request.SpringMVCHttpRequestContext;
import com.sharingif.cube.web.user.CoreUserHttpSessionManage;
import com.sharingif.cube.web.user.IWebUserManage;
import com.sharingif.wallet.user.model.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoUserChain extends AbstractHandlerMethodChain {

    public NoUserChain() {
        webUserManage = new CoreUserHttpSessionManage();
    }

    private IWebUserManage webUserManage;

    private String replaceContent;
    private Map<String,String> excludeMethods = new HashMap<String,String>();

    public String getReplaceContent() {
        return replaceContent;
    }

    public void setReplaceContent(String replaceContent) {
        this.replaceContent = replaceContent;
    }

    public Map<String, String> getExcludeMethods() {
        return excludeMethods;
    }

    public void setExcludeMethods(List<String> excludeMethods) {
        this.excludeMethods = new HashMap<String,String>(excludeMethods.size());
        for(String excludeMethod : excludeMethods) {
            this.excludeMethods.put(excludeMethod,excludeMethod);
        }
    }

    @Override
    public void before(HandlerMethodContent content) throws CubeException {
        if(null != excludeMethods.get(getAuthorityCode(content))) {
            return;
        }

        SpringMVCHttpRequestContext httpRequestContext = content.getRequestContext();
        User user = webUserManage.getUser(httpRequestContext.getRequest());

        if(user == null) {
            throw new NoUserAccessDecisionCubeException();
        }

    }

    @Override
    public void after(HandlerMethodContent handlerMethodContent) throws CubeException {

    }

    protected String getAuthorityCode(HandlerMethodContent content) {
        String authorityCode = new StringBuilder().append(content.getHandlerMethod().getBean().getClass().getName()).append(".").append(content.getHandlerMethod().getMethod().getName()).toString();

        if(getReplaceContent() == null) {
            return authorityCode;
        }

        return StringUtils.replace(authorityCode, replaceContent, "");
    }
}
