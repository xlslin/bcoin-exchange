package com.sharingif.wallet.app.chain.command;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.wallet.user.model.entity.User;

/**
 * 用户登录返回对像
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/4/13 下午6:40
 */
public class UserLoginRetunObjectCommand extends AbstractHandlerMethodCommand {

    @Override
    public void execute(HandlerMethodContent content) throws CubeException {
        User user = (User)content.getReturnValue();

        user.setPassword(null);
		user.setPaymentPassword(null);

        content.setReturnValue(null);
    }

}
