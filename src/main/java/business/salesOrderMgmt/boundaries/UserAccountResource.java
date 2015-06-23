package business.salesOrderMgmt.boundaries;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import business.salesOrderMgmt.controls.UserAccountService;
import business.salesOrderMgmt.entity.UserAccount;

@Stateless
@Path("")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UserAccountResource {

	@Inject
	UserAccountService userAccountService;
			
	@GET
	@Path("userAccounts/{userAccountUid}")
	public UserAccount getUserAccount(@PathParam("userAccountUid") Integer userAccountUid, @QueryParam("callback") String callback) {
		System.out.println("Entered resource call to getUserAccount() for userAccountUID" + userAccountUid);
		UserAccount userAccount = null;
		if (userAccountUid != null) {
			userAccount = userAccountService.retrieveUserAccount(userAccountUid);			
		}
		if (userAccount != null) {
			System.out.println("UserAccount retrieved");
		} else {
			throw new WebApplicationException(404);
		}
		return userAccount;
	}
	
}
