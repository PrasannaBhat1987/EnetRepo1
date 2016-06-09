package com.pras.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.pras.dto.LoginDto;
import com.pras.dto.LoginResponseDto;
import com.pras.model.User;
import com.pras.service.LoginService;
import com.pras.serviceimpl.LoginServiceImpl;
import com.pras.util.AuthUtil;
import com.sun.jersey.core.util.Base64;

@Path("/user")
public class LoginController {

	User u;
	
	@POST
	@Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
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
        
        if(ifValid(cred, Auth)) {
        	LoginResponseDto dto = new LoginResponseDto();
        	dto.setAddress(u.getAddress());
        	dto.setContact(u.getContact());
        	dto.setEmail(u.getEmail());
        	dto.setId(u.getId());
        	dto.setAuth(Auth);
        	dto.setName(u.getName());
        	dto.setRole(u.getRole());
        	return Response.status(200).entity(dto).build();
        }
        return Response.status(400).entity("Unable to authenticate user. Try again").build();
    }

	private boolean ifValid(LoginDto user, String Auth) {
		// TODO Auto-generated method stub
		LoginService service = new LoginServiceImpl();
		u = service.validUser(user);
		if (u == null) {
			return false;
		}
		AuthUtil.addAuth(Auth, u);
		return true;
	}
	
	@GET
	@Path("/logout")
    public Response logoutUser(@HeaderParam("Auth") String auth) {
		if(auth != null && AuthUtil.getLoggedInUser(auth) != null) {
			AuthUtil.logoutUser(auth);
			return Response.status(200).entity("Logout successful !!").build();
		} else {
			return Response.status(200).entity("User session not found").build();
		}
	}
}
