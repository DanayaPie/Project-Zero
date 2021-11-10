package com.revature.app;

import com.revature.controller.ClientController;
import com.revature.controller.ExceptionMappingController;

import io.javalin.Javalin;

public class Application {

	public static void main(String args[]) {
		
		Javalin app = Javalin.create();
		
		ClientController controller = new ClientController();
		controller.registerEndpoints(app);
		
		ExceptionMappingController exceptionController = new ExceptionMappingController();
		exceptionController.mapExceptions(app);
		
		app.start();
	}
}
