package com.revature.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.model.Client;
import com.revature.service.ClientService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ClientController {
	
	private Logger logger = LoggerFactory.getLogger(ClientController.class);

	private  ClientService clientService;

	// reginterEndpoints
	public void registerEndpoints(Javalin app) {
		
		app.get("/clients/", getAllClients);
		app.get("/clients/{clientId}", getClientByClientId);
		
		app.post("/clients", addClient);
		
		app.put("/clients/{clientId}/update", updateClient);
		
		app.delete("/clients/{clientId}", deleteClientByClientId);
	}
	
	// constructor
	public ClientController() {
		this.clientService = new ClientService();
		
	}
	
	
	/* *********
	 * -- GET --
	 * *********
	 */ 

	// getAllClient
	private Handler getAllClients = (ctx) -> {
		
		logger.info("ClientController.getAllClients() invoked.");
		
		List<Client> clients = this.clientService.getAllClients();
		
		ctx.json(clients);
	};
	
	// getClientByClientId
	private Handler getClientByClientId = (ctx) -> {
		
		logger.info("ClientController.getClientByClientId() invoked.");
		
		String clientId = ctx.pathParam("clientId");
		
		this.clientService.verifyClientId(clientId);
		
		Client c = this.clientService.getClientByClientId(clientId);
		
		ctx.json(c);
	};
	
	
	/* **********
	 * -- POST --
	 * **********
	 */
	
	// addClient
	private Handler addClient = (ctx) -> {
		
		logger.info("ClientController.addClient() invoked.");
		
		Client client = ctx.bodyAsClass(Client.class);
		
		Client c = this.clientService.addClient(client);
		
		ctx.json(c);
	};
	
	
	/* *********
	 * -- PUT -- 
	 * *********
	 */
	// updateClient
	private Handler updateClient = (ctx) -> {
		
		logger.info("ClientController.updateClient() invoked.");
		
		String clientId = ctx.pathParam("clientId");
		
		logger.debug("clientId {}", clientId);
		
		this.clientService.verifyClientId(clientId);
		 
		 String firstName = ctx.queryParam("firstName");
		 String lastName = ctx.queryParam("lastName");
		
		Client c = this.clientService.updateClient(clientId, firstName, lastName);
		
		ctx.json(c);
	};
	
	// updateClient -- with Context ctx in Service Layer
//	private Handler updateClient = (ctx) -> {
//		
//		logger.info("ClientController.updateClient() invoked.");
//		
//		String clientId = ctx.pathParam("clientId");
//		
//		logger.debug("clientId {}", clientId);
//		
//		this.clientService.verifyClientId(clientId);
//		
//		Client c = this.clientService.updateClient(clientId, ctx);
//		
//		ctx.json(c);
//	};
	
	/* ************
	 * -- DELETE --
	 * ************
	 */
	
	// deleteClientByClientId
	private Handler deleteClientByClientId = (ctx) -> {
		
		logger.info("ClientController.deleteClientByClientId() invoked.");
		
		String clientId = ctx.pathParam("clientId");
		
		this.clientService.verifyClientId(clientId);
		this.clientService.deleteClientByClientId(clientId);
		
		ctx.json("The client with ID of " + clientId + " has been deleted.");
	};
}