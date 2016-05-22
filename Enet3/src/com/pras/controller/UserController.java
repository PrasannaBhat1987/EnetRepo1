package com.pras.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.pras.dao.UserDao;
import com.pras.daoimpl.UserDaoImpl;
import com.pras.dto.UserDto;
import com.pras.model.User;
import com.pras.util.AuthUtil;

@Path("/representative")
public class UserController {

	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/add")
    public Response add(@HeaderParam("Auth") String auth, UserDto user){
         
		if (isValid(auth)) {
			UserDao dao = new UserDaoImpl();
			dao.addUser(user);
			return Response.status(200)
					.entity("This user is added successfully").build();
		} else {
			return Response.status(400).entity("You are not authorized")
					.build();
		}
    }
	
	private boolean isValid(String auth) {
		if(auth != null && AuthUtil.getRole(auth) != null) {
			return true;
		} 
		return false;
	}
	
	@DELETE
	@Path("/{id}/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delete(@HeaderParam("Auth") String auth,
			@PathParam("id") long id) {

		if (isValid(auth)) {
			UserDao dao = new UserDaoImpl();
			dao.removeUserById(id);
			return Response.status(200)
					.entity("This user is removed successfully").build();
		} else {
			return Response.status(400).entity("You are not authorized")
					.build();
		}

	}
	
	@PUT
	@Path("/{id}/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@HeaderParam("Auth") String auth, @PathParam("id") long id,
			User user) {

		System.out.println("Received User is :" + user.getName());
		if (isValid(auth)) {
			UserDao dao = new UserDaoImpl();
			dao.editUser(id, user);
			return Response.status(200)
					.entity("This user is removed successfully").build();
		} else {
			return Response.status(400).entity("You are not authorized")
					.build();
		}

	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@HeaderParam("Auth") String auth, @PathParam("id") long id) {
		UserDto user = new UserDto();
		if (isValid(auth)) {
			UserDao dao = new UserDaoImpl();
			user = dao.getUser(id);
		}
		return Response.status(200).entity(user).build();
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers(@HeaderParam("Auth") String auth) {
		UserDao dao = new UserDaoImpl();
		List<UserDto> list = new ArrayList<UserDto>();
		if (isValid(auth)) {
			list.addAll(dao.getUsers());
		}
		return Response.status(200).entity(list).build();
	}
}
