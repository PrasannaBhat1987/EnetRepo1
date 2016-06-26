package com.pras.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.pras.util.HibernateMain;

@Path("/setup")
public class SetupController {

	@GET
	@Path("/init")
	public void doSetup() {
		System.out.println("Setup started");
		System.out.println("Setup done");
		HibernateMain.init();
		System.out.println("Sample entries inserted");
	}
}
