package com.mycompany.app;

import org.apache.log4j.Logger;

public class LoggerExceptionExample{
	
	final static Logger logger = Logger.getLogger(LoggerExceptionExample.class);
	
	public static void main(String[] args) {

		LoggerExceptionExample obj = new LoggerExceptionExample();
		
		try{
			obj.divide();
		}catch(ArithmeticException ex){
			logger.error("Sorry, something wrong!", ex);
		}
		
		
	}
	
	private void divide(){
		
		int i = 10/0;

	}
	
}