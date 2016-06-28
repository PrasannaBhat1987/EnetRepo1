package com.pras.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;

import com.pras.util.HibernateMain;

@Path("/setup")
public class SetupController {

	final static Logger logger = Logger.getLogger(SetupController.class);
	
	@GET
	@Path("/init")
	public void doSetup() {
		System.out.println("Setup started");
		System.out.println("Setup done");
		logger.debug("Setup started");
		try {
			HibernateMain.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		System.out.println("Sample entries inserted");
		logger.debug("Sample entries inserted");
	}
}
