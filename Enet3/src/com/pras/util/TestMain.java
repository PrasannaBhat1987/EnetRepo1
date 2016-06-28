package com.pras.util;

import org.apache.log4j.Logger;

public class TestMain {

	final static Logger logger = Logger.getLogger(TestMain.class);
	
	public static void main(String[] args) {
		logger.debug("sample Log");
		System.out.println("Hello Pras");
		logger.debug("Main Exit");
	}

}
