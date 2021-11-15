package com.revature.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controller.AccountController;
import com.revature.controller.ClientController;
import com.revature.controller.ExceptionMappingController;

import io.javalin.Javalin;

public class BankApiApplication {

	public static void main(String args[]) {

		Javalin app = Javalin.create();

		Logger logger = LoggerFactory.getLogger(BankApiApplication.class);
		app.before(ctx -> {
			logger.info(ctx.method() + " request recieved to the " + ctx.path() + " endpoiint.");
		});
		
		ClientController clientController = new ClientController();
		clientController.registerEndpoints(app);
		
		AccountController accountController = new AccountController();
		accountController.registerEndpoints(app);

		ExceptionMappingController exceptionController = new ExceptionMappingController();
		exceptionController.mapExceptions(app);

		app.start();
	}

}
