package com.pras.controller;

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

import com.pras.dao.WebsiteDao;
import com.pras.daoimpl.WebsiteDaoImpl;
import com.pras.dto.WebsiteDto;
import com.pras.model.Order;
import com.pras.model.Website;
import com.pras.util.AuthUtil;

@Path("/website")
public class WebsiteController {

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(@HeaderParam("Auth") String auth,
			WebsiteDto website) {

		System.out.println("Received Website is :" + website.getName());
		if (isValid(auth)) {
			WebsiteDao dao = new WebsiteDaoImpl();
			dao.addWebsite(website);
			return Response.status(200)
					.entity("This website is added successfully").build();
		} else {
			return Response.status(400).entity("You are not authorized")
					.build();
		}

	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@HeaderParam("Auth") String auth, @PathParam("id") long id) {

		if (isValid(auth)) {
			WebsiteDao dao = new WebsiteDaoImpl();
			dao.removeWebsite(id);
			return Response.status(200)
					.entity("This website is removed successfully").build();
		} else {
			return Response.status(400).entity("You are not authorized")
					.build();
		}

	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response edit(@HeaderParam("Auth") String auth, @PathParam("id") long id, WebsiteDto website) {
		if (isValid(auth)) {
			WebsiteDao dao = new WebsiteDaoImpl();
			dao.editWebsite(id, website);
			return Response.status(200)
					.entity("This website is added successfully").build();
		} else {
			return Response.status(400).entity("You are not authorized")
					.build();
		}
		
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@HeaderParam("Auth") String auth) {

		if (isValid(auth)) {
			WebsiteDao dao = new WebsiteDaoImpl();
			List<WebsiteDto> websites = dao.getWebsites();
			return Response.status(200)
					.entity(websites).build();
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

}
