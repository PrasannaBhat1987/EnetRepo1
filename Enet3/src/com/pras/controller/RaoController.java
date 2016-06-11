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

import com.pras.counts.RaoCount;
import com.pras.counts.UserCount;
import com.pras.dao.BranchDao;
import com.pras.dao.RaoDao;
import com.pras.dao.UserDao;
import com.pras.daoimpl.BranchDaoImpl;
import com.pras.daoimpl.RaoDaoImpl;
import com.pras.daoimpl.UserDaoImpl;
import com.pras.dto.BranchDto;
import com.pras.dto.RaoDto;
import com.pras.model.Rao;
import com.pras.util.AuthUtil;

@Path("/rao")
public class RaoController {

	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/add")
    public Response add(@HeaderParam("Auth") String auth, RaoDto rao){
         
		if (isValid(auth)) {
			RaoDao dao = new RaoDaoImpl();
			int id = dao.addRao(rao);
			return Response.status(200)
					.entity(id).build();
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
	public Response delete(@HeaderParam("Auth") String auth,
			@PathParam("id") long id) {

		if (isValid(auth)) {
			RaoDao dao = new RaoDaoImpl();
			dao.removeRao(id);
			return Response.status(200)
					.entity("This rao is removed successfully").build();
		} else {
			return Response.status(400).entity("You are not authorized")
					.build();
		}

	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@HeaderParam("Auth") String auth,
			@PathParam("id") long id) {

		if (isValid(auth)) {
			RaoDao dao = new RaoDaoImpl();
			dao.editRao(id, null);
			return Response.status(200)
					.entity("This rao is removed successfully").build();
		} else {
			return Response.status(400).entity("You are not authorized")
					.build();
		}

	}
	
	@GET
	@Path("/getNumbers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNumbers(@HeaderParam("Auth") String auth) {
		if (isValid(auth)) {
			RaoDao dao = new RaoDaoImpl();
			RaoCount ct = new RaoCount();
			ct.setCompleted(dao.getRaoType("Completed"));
			ct.setCreated(dao.getRaoType("Created"));
			ct.setInProgress(dao.getRaoType("In Progress"));
			return Response.status(200).entity(ct).build();
		}
		return Response.status(400).entity("You are not authorized")
				.build();
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response allBranches(@HeaderParam("Auth") String auth) {

		RaoDao dao = new RaoDaoImpl();
		List<RaoDto> list = new ArrayList<RaoDto>();
		if (isValid(auth)) {
			list.addAll(dao.getAllRaos());
			return Response.status(200).entity(list).build();
		}
		return Response.status(400).entity("You are not authorized")
				.build();

	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@HeaderParam("Auth") String auth, @PathParam("id") long id) {

		RaoDao dao = new RaoDaoImpl();
		RaoDto dto = new RaoDto();
		if (isValid(auth)) {
			dto = dao.getRao(id);
			return Response.status(200).entity(dto).build();
		}
		
		return Response.status(400).entity("You are not authorized")
				.build();
	}
}
