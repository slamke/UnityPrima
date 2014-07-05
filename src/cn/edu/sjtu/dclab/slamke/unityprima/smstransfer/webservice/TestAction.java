package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/test")
public class TestAction {
	@GET
	@Path("/login")
	@Produces({"application/json","text/html"})
	public String login(@QueryParam("username") String username,
			@QueryParam("password") String password) {
		System.out.println("username:" + username+"  password:" + password);
		return "login successfully!";
	}

}