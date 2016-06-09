package com.pras.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.pras.dao.BranchDao;
import com.pras.dao.UserDao;
import com.pras.daoimpl.BranchDaoImpl;
import com.pras.daoimpl.UserDaoImpl;
import com.pras.dto.BranchDto;
import com.pras.dto.UserDto;
import com.pras.model.Branch;
import com.pras.util.AuthUtil;

@Path("/branch")
public class BranchController {

	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/add")
    public Response add(@HeaderParam("Auth") String auth, BranchDto branch){
         
		if (isValid(auth)) {
			BranchDao dao = new BranchDaoImpl();
			dao.addBranch(branch);
			return Response.status(200)
					.entity("This branch is added successfully").build();
		} else {
			return Response.status(400).entity("You are not authorized")
					.build();
		}
    }
	
	private boolean isValid(String auth) {
		// TODO Auto-generated method stub
		if(AuthUtil.getLoggedInUser(auth) != null) {
			return true;
		}
		return true;
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@HeaderParam("Auth") String auth, @PathParam("id") long id) {

		if (isValid(auth)) {
			BranchDao dao = new BranchDaoImpl();
			dao.removeBranch(id);
			return Response.status(200)
					.entity("This branch is removed successfully").build();
		} else {
			return Response.status(400).entity("You are not authorized")
					.build();
		}

	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@HeaderParam("Auth") String auth, @PathParam("id") long id,
			BranchDto branch) {

		if (isValid(auth)) {
			BranchDao dao = new BranchDaoImpl();
			dao.editBranch(id, branch);
			return Response.status(200)
					.entity("This branch is updated successfully").build();
		} else {
			return Response.status(400).entity("You are not authorized")
					.build();
		}

	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response allBranches(@HeaderParam("Auth") String auth) {

		BranchDao dao = new BranchDaoImpl();
		List<BranchDto> list = new ArrayList<BranchDto>();
		if (isValid(auth)) {
			list.addAll(dao.getBranches());
			return Response.status(200).entity(list).build();
		}
		return Response.status(400).entity("You are not authorized")
				.build();

	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@HeaderParam("Auth") String auth, @PathParam("id") long id) {

		BranchDao dao = new BranchDaoImpl();
		BranchDto dto = new BranchDto();
		if (isValid(auth)) {
			dto = dao.getBranch(id);
			return Response.status(200).entity(dto).build();
		}
		
		return Response.status(400).entity("You are not authorized")
				.build();
	}
}
