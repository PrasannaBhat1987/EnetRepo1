package com.pras.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.pras.dto.LoginDto;
import com.pras.model.User;
import com.pras.util.AuthUtil;
import com.sun.jersey.core.util.Base64;

@Path("/user")
public class LoginController {

	@POST
	@Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginDto cred){
         
        System.out.println("UserName :"+ cred.getUsername());
        System.out.println("Password: "+ cred.getPassword());
        String complete = cred.getUsername() +':' +cred.getPassword();

     // encode data on your side using BASE64
        byte[]  bytesEncoded = Base64.encode(complete.getBytes());
        
        String Auth = new String(bytesEncoded);
        System.out.println("ecncoded value is " + Auth);

        // Decode data on other side, by processing encoded data
        byte[] valueDecoded= Base64.decode(bytesEncoded);
        System.out.println("Decoded value is " + new String(valueDecoded));
        System.out.println(new String(Base64.decode(Auth)));
        
        if(ifValid(cred)) {
        	AuthUtil.addAuth(Auth, "Admin");
        }
        return Response.status(200).entity("Auth Token : "+Auth).build();
    }

	private boolean ifValid(LoginDto user) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@GET
	@Path("/logout")
    public Response logoutUser(@HeaderParam("Auth") String auth) {
		if(auth != null && AuthUtil.getRole(auth) != null) {
			AuthUtil.logoutUser(auth);
			return Response.status(200).entity("Logout successful !!").build();
		} else {
			return Response.status(200).entity("User session not found").build();
		}
	}
}
