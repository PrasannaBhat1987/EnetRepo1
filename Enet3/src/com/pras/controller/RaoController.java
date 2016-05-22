package com.pras.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.pras.dao.RaoDao;
import com.pras.daoimpl.RaoDaoImpl;
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
			dao.addRao(rao);
			return Response.status(200)
					.entity("This rao is added successfully").build();
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
	@Path("{id}/delete")
	@Consumes(MediaType.APPLICATION_JSON)
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
	@Path("{id}/update")
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
}
