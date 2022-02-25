package org.dieschnittstelle.mobile.samplewebapi;

import javax.ws.rs.*;

@Path("/users")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface IUserOperations {
	
	@PUT
	@Path("/auth")
	public boolean authenticateUser(User user);

	@PUT
	@Path("/prepare")
	public boolean prepare(User user);

}
