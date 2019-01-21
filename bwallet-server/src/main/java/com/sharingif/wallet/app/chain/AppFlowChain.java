package com.sharingif.wallet.app.chain;

import com.sharingif.cube.communication.http.HttpSession;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.exception.validation.ValidationCubeException;
import com.sharingif.cube.core.handler.chain.AbstractHandlerMethodChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.web.springmvc.request.SpringMVCHttpRequestContext;
import com.sharingif.wallet.app.constants.ErrorConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * app 流程管理
 * 2017/6/6 下午4:03
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class AppFlowChain extends AbstractHandlerMethodChain {

    private static final String CURRENT_TRANCODE = "currentTranCode";

    private Map<String,List<String>> stepMap;

    private Map<String, String> excludeMethods = new HashMap();


    public Map<String, List<String>> getStepMap() {
        return stepMap;
    }
    public void setStepMap(Map<String, List<String>> stepMap) {
        this.stepMap = stepMap;
    }

    public void setExcludeMethods(List<String> excludeMethods) {
        this.excludeMethods = new HashMap(excludeMethods.size());

        for(String excludeMethod : excludeMethods) {
            this.excludeMethods.put(excludeMethod, excludeMethod);
        }
    }

    @Override
    public void before(HandlerMethodContent springMVCHandlerMethodContent) throws CubeException {
        List<String> previousStepList = stepMap.get(getCurrentTranCode(springMVCHandlerMethodContent));

        if(previousStepList == null || previousStepList.size() == 0) {
            return;
        }

        String sessionPreviousStep = getPreviousStep(springMVCHandlerMethodContent);

        for(String previousStep : previousStepList) {
            if(previousStep.equals(sessionPreviousStep)) {
                return;
            }
        }

        throw new ValidationCubeException(ErrorConstants.APP_STEP_ERROR);
    }

    @Override
    public void after(HandlerMethodContent springMVCHandlerMethodContent) throws CubeException {
        if(null == this.excludeMethods.get(this.getCurrentTranCode(springMVCHandlerMethodContent))) {
            setPreviousStep(springMVCHandlerMethodContent);
        }
    }

    protected void setPreviousStep(HandlerMethodContent springMVCHandlerMethodContent) {
        SpringMVCHttpRequestContext requestContext = springMVCHandlerMethodContent.getRequestContext();
        HttpSession httpSession = requestContext.getRequest().getSession(true);
        if(httpSession == null) {
            return;
        }
        httpSession.setAttribute(CURRENT_TRANCODE,getCurrentTranCode(springMVCHandlerMethodContent));
    }

    protected String getPreviousStep(HandlerMethodContent springMVCHandlerMethodContent) {
        SpringMVCHttpRequestContext requestContext = springMVCHandlerMethodContent.getRequestContext();
        HttpSession httpSession = requestContext.getRequest().getSession();
        if(httpSession == null) {
            return null;
        }
        return (String) httpSession.getAttribute(CURRENT_TRANCODE);
    }

    protected String getCurrentTranCode(HandlerMethodContent content) {
        String authorityCode = new StringBuilder().append(content.getHandlerMethod().getBean().getClass().getName()).append(".").append(content.getHandlerMethod().getMethod().getName()).toString();

        return authorityCode;
    }

    protected void clearCurrentStep(HandlerMethodContent springMVCHandlerMethodContent) {
        SpringMVCHttpRequestContext requestContext = springMVCHandlerMethodContent.getRequestContext();
        HttpSession httpSession = requestContext.getRequest().getSession();
        if(httpSession == null) {
            return;
        }
        httpSession.removeAttribute(CURRENT_TRANCODE);
    }

}
