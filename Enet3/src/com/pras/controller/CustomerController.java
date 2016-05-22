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
import com.pras.dao.CustomerDao;
import com.pras.daoimpl.BranchDaoImpl;
import com.pras.daoimpl.CustomerDaoImpl;
import com.pras.dto.BranchDto;
import com.pras.dto.CustomerDto;
import com.pras.util.AuthUtil;

@Path("/customer")
public class CustomerController {

	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/add")
    public Response add(@HeaderParam("Auth") String auth, CustomerDto cust){
         
		if (isValid(auth)) {
			CustomerDao dao = new CustomerDaoImpl();
			dao.addCustomer(cust);
			return Response.status(200)
					.entity("This Customer is added successfully").build();
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
	public Response delete(@HeaderParam("Auth") String auth, @PathParam("id") long id) {

		if (isValid(auth)) {
			CustomerDao dao = new CustomerDaoImpl();
			dao.removeCustomer(id);
			return Response.status(200)
					.entity("This customer is removed successfully").build();
		} else {
			return Response.status(400).entity("You are not authorized")
					.build();
		}

	}
	
	@PUT
	@Path("{id}/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@HeaderParam("Auth") String auth, @PathParam("id") long id,
			CustomerDto cust) {

		if (isValid(auth)) {
			CustomerDao dao = new CustomerDaoImpl();
			dao.editCustomer(id, cust);
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
	public Response allCustomers(@HeaderParam("Auth") String auth) {

		CustomerDao dao = new CustomerDaoImpl();
		List<CustomerDto> list = new ArrayList<CustomerDto>();
		if (isValid(auth)) {
			list.addAll(dao.getCustomers());
		}
		return Response.status(200).entity(list).build();

	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@HeaderParam("Auth") String auth, @PathParam("id") long id) {

		CustomerDao dao = new CustomerDaoImpl();
		CustomerDto dto = new CustomerDto();
		if (isValid(auth)) {
			dto = dao.getCustomer(id);
		}
		return Response.status(200).entity(dto).build();

	}
	
}
