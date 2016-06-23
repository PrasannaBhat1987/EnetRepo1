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

import com.pras.counts.UserCount;
import com.pras.dao.UserDao;
import com.pras.daoimpl.UserDaoImpl;
import com.pras.dto.UserDetailsDto;
import com.pras.dto.UserDto;
import com.pras.exception.ErrorInfo;
import com.pras.model.User;
import com.pras.util.AuthUtil;

@Path("/representative")
public class UserController {

	ErrorInfo info = new ErrorInfo(); 
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/add")
    public Response add(@HeaderParam("Auth") String auth, UserDto user){
		if (AuthUtil.isValid(auth)) {
			UserDao dao = new UserDaoImpl();
			int returnCode = dao.addUser(user);
			if(returnCode == 1) {
				info.setStatus(500);
				info.setMessage("This User with provided Email ID already Exist in the database. Previously someone deleted this user from the system. Send email to the Admin to revive this user.");
				return Response.status(500)
						.entity(info).build();
			} else {
			return Response.status(200)
					.entity("This user is added successfully").build();
			}
		} else {
			return getErrorInfo();
		}
    }
	
	@DELETE
	@Path("/{id}")
	public Response delete(@HeaderParam("Auth") String auth,
			@PathParam("id") long id) {

		if (AuthUtil.isValid(auth)) {
			UserDao dao = new UserDaoImpl();
			dao.removeUserById(id);
			return Response.status(200)
					.entity("This user is removed successfully").build();
		} else {
			return getErrorInfo();
		}

	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@HeaderParam("Auth") String auth, @PathParam("id") long id,
			UserDto dto) {

		System.out.println("Received User is :" + dto.getName());
		if (AuthUtil.isValid(auth) || (AuthUtil.getLoggedInUser(auth).getId() == id)) {
			UserDao dao = new UserDaoImpl();
			int returnVal = dao.editUser(id, dto);
			if(returnVal == 1) {
				return Response.status(400)
						.entity("Password is not correct").build();
			} else {
				return Response.status(200)
						.entity("This user is updated successfully").build();
			}
			
		} else {
			return getErrorInfo();
		}

	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@HeaderParam("Auth") String auth, @PathParam("id") long id) {
		UserDto user = new UserDto();
		if (AuthUtil.isValidForAll(auth)) {
			UserDao dao = new UserDaoImpl();
			try {
				user = dao.getUser(id);
			} catch (Exception e) {
				ErrorInfo info = new ErrorInfo(500, "Representative does not exist. Check the Representative ID provided.");
				return Response.status(500).entity(info).build();
			}
			
			return Response.status(200).entity(user).build();
		}
		return getErrorInfo();
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers(@HeaderParam("Auth") String auth) {
		UserDao dao = new UserDaoImpl();
		List<UserDto> list = new ArrayList<UserDto>();
		if (AuthUtil.isValidForAll(auth)) {
			list.addAll(dao.getUsers());
			return Response.status(200).entity(list).build();
		}
		
		return getErrorInfo();
	}
	
	@GET
	@Path("/getNumbers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNumbers(@HeaderParam("Auth") String auth) {
		if (AuthUtil.isValidForAll(auth)) {
			UserDao dao = new UserDaoImpl();
			UserCount ct = new UserCount();
			ct.setAdmins(dao.getUserType("Admin"));
			ct.setManagers(dao.getUserType("Manager"));
			ct.setReps(dao.getUserType("Representative"));
			return Response.status(200).entity(ct).build();
		}
		return getErrorInfo();
	}
	
	@GET
	@Path("/{id}/details")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDetails(@HeaderParam("Auth") String auth, @PathParam("id") long id) {
		UserDto user = new UserDto();
		if (AuthUtil.isValidForAll(auth)) {
			UserDao dao = new UserDaoImpl();
			UserDetailsDto dto = new UserDetailsDto();
			dto = dao.getUserDetails(id);
			return Response.status(200).entity(dto).build();
		}
		return getErrorInfo();
	}
	
	private Response getErrorInfo() {
		info.setStatus(400);
		info.setMessage("You are not authorized to perform this action");
		return Response.status(500)
				.entity(info).build();
	}
}
