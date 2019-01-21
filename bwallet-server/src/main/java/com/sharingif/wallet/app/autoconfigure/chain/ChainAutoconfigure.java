package com.sharingif.wallet.app.autoconfigure.chain;


import com.sharingif.cube.components.handler.chain.RequestLocalContextHolderChain;
import com.sharingif.cube.components.sequence.UUIDSequenceGenerator;
import com.sharingif.cube.core.chain.ChainImpl;
import com.sharingif.cube.core.chain.command.Command;
import com.sharingif.cube.core.handler.chain.*;
import com.sharingif.cube.security.handler.chain.command.password.PasswordEncryptorCommand;
import com.sharingif.cube.security.web.handler.chain.command.authentication.SessionConcurrentWebCommand;
import com.sharingif.cube.security.web.handler.chain.command.user.CoreUserHttpSessionManageWebCommand;
import com.sharingif.cube.security.web.handler.chain.command.user.InvalidateHttpSessionWebCommand;
import com.sharingif.cube.security.web.handler.chain.session.SessionExpireChain;
import com.sharingif.cube.security.web.spring.handler.chain.command.session.SessionRegistryCommand;
import com.sharingif.wallet.app.chain.AppFlowChain;
import com.sharingif.wallet.app.chain.NoUserChain;
import com.sharingif.wallet.app.chain.command.UserLoginRetunObjectCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ChainAutoconfigure {

    @Bean(name="requestLocalContextHolderChain")
    public RequestLocalContextHolderChain createRequestLocalContextHolderChain(UUIDSequenceGenerator uuidSequenceGenerator) {
        RequestLocalContextHolderChain requestLocalContextHolderChain = new RequestLocalContextHolderChain();
        requestLocalContextHolderChain.setSequenceGenerator(uuidSequenceGenerator);

        return requestLocalContextHolderChain;
    }

    @Bean("registerChain")
    public ChainImpl<HandlerMethodContent> createRegisterChain(PasswordEncryptorCommand httpRequestPasswordEncryptorCommand) {
        List<Command<? super HandlerMethodContent>> commands = new ArrayList<Command<? super HandlerMethodContent>>();
        commands.add(httpRequestPasswordEncryptorCommand);

        ChainImpl loginChain = new ChainImpl();
        loginChain.setCommands(commands);

        return loginChain;
    }

    @Bean("loginChain")
    public ChainImpl<HandlerMethodContent> createLoginPassChain(
            SessionConcurrentWebCommand sessionConcurrentWebCommand
            , InvalidateHttpSessionWebCommand invalidateHttpSessionWebCommand
            , CoreUserHttpSessionManageWebCommand coreUserHttpSessionManageWebCommand
            , SessionRegistryCommand sessionRegistryCommand
            , UserLoginRetunObjectCommand userLoginRetunObjectCommand
    ) {
        List<String> copyAttributeNames = new ArrayList<String>();
        copyAttributeNames.add(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
        invalidateHttpSessionWebCommand.setCopyAttributeNames(copyAttributeNames);

        List<Command<? super HandlerMethodContent>> commands = new ArrayList<Command<? super HandlerMethodContent>>();
        commands.add(sessionConcurrentWebCommand);
        commands.add(invalidateHttpSessionWebCommand);
        commands.add(coreUserHttpSessionManageWebCommand);
        commands.add(sessionRegistryCommand);
        commands.add(userLoginRetunObjectCommand);

        ChainImpl loginChain = new ChainImpl();
        loginChain.setCommands(commands);

        return loginChain;
    }

    @Bean("noUserChain")
    public NoUserChain createNoUserChain() {

        List<String> excludeMethods = new ArrayList<String>();

        excludeMethods.add(null);

        
        NoUserChain noUserChain = new NoUserChain();
        noUserChain.setExcludeMethods(excludeMethods);

        return noUserChain;
    }

    @Bean(name="springMCVChains")
    public MultiHandlerMethodChain createSpringMCVChains(
            MonitorPerformanceChain controllerMonitorPerformanceChain
            , SessionExpireChain sessionExpireChain
            , NoUserChain noUserChain
            , AppFlowChain appFlowChain
            , AnnotationHandlerMethodChain annotationHandlerMethodChain
    ) {

        List<HandlerMethodChain> chains = new ArrayList<HandlerMethodChain>(3);
        chains.add(controllerMonitorPerformanceChain);
        chains.add(sessionExpireChain);
        chains.add(noUserChain);
        chains.add(appFlowChain);
        chains.add(annotationHandlerMethodChain);

        MultiHandlerMethodChain springMVCHandlerMethodContent = new MultiHandlerMethodChain();
        springMVCHandlerMethodContent.setChains(chains);

        return  springMVCHandlerMethodContent;
    }

}
