package com.revature.controller;

import java.util.List;

import com.revature.model.Client;
import com.revature.service.ClientService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ClientController {

	private  ClientService clientService;
	
	public void registerEndpoints(Javalin app) {
		
		app.get("/clients/", getAllClients);
		app.get("/clients/{clientId}", getClientById);
		app.post("/clients", addClient);
		app.put("/clients/{clientId}/update", updateClient);
//		app.delete("/clients/{clientId}", deleteClientById);
//		
//		app.post("/clients/{clientId}/accounts", addNewAccount);
//		app.get("/clients/{clientId}/accounts", getAllAcounts);
//		app.get("/clients/{clientId}/accounts?LessThan=?&amountGreaterThan=?", getAllAmoutBetween);
//		
//		app.get("/clients/{clientId}/accounts/{accountId}", getClientAccountByIds);
//		app.put("/clients/{clientId}/accounts/{accountId}", AddOrUpdateAccountDTO);
//		app.delete("/clients/{clientId/accounts/{accountId}", deleteAccountByIds);
		
	}
	
	public ClientController() {
		this.clientService = new ClientService();
		
	}

	// getAllClient
	private Handler getAllClients = (ctx) -> {
		
		List<Client> clients = this.clientService.getAllClients();
		
		ctx.json(clients);
	};
	
	// getClientById
	private Handler getClientById = (ctx) -> {
		
		String clientId = ctx.pathParam("clientId");
		
		Client c = this.clientService.getClientById(clientId);
		
		ctx.json(c);
	};
	
	// addClient
	private Handler addClient = (ctx) -> {
		
		Client client = ctx.bodyAsClass(Client.class);
		
		Client c = this.clientService.addClient(client);
		
		ctx.json(c);
	};
	
	// updateClient
	private Handler updateClient = (ctx) -> {
		
		Client client = ctx.bodyAsClass(Client.class);
		
		Client c = this.clientService.updateClient(client.getClientId(), client.getFirstName(), client.getLastName(), client.getPhoneNumber(), client.getEmail());
		
		ctx.json(c);
	};
	
	
}