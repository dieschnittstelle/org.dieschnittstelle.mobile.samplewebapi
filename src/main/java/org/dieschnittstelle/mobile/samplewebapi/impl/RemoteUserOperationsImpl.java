package org.dieschnittstelle.mobile.samplewebapi.impl;

import org.apache.log4j.Logger;
import org.dieschnittstelle.mobile.samplewebapi.IUserOperations;
import org.dieschnittstelle.mobile.samplewebapi.User;

public class RemoteUserOperationsImpl implements IUserOperations {

	protected static Logger logger = Logger.getLogger(RemoteUserOperationsImpl.class);
	
	/**
	 * a kind of highly simplistic authentication implementation
	 */
	@Override
	public boolean authenticateUser(User user) {
		logger.info("authenticateUser(): " + user);
		return "s@bht.de".equals(user.getEmail()) && "000000".equals(user.getPwd());
	}

}
