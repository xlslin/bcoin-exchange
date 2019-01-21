package com.sharingif.wallet.user.dao.impl;


import org.springframework.stereotype.Repository;


import com.sharingif.wallet.user.model.entity.User;
import com.sharingif.wallet.user.dao.UserDAO;
import com.sharingif.wallet.common.dao.impl.BaseDAOImpl;


@Repository
public class UserDAOImpl extends BaseDAOImpl<User,java.lang.String> implements UserDAO {
	
}
