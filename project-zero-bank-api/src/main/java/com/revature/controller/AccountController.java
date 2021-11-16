package com.revature.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.model.Account;
import com.revature.service.AccountService;
import com.revature.service.ClientService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AccountController {
	
	private Logger logger = LoggerFactory.getLogger(AccountController.class);

	private AccountService accountService;
	private ClientService clientService;
	
	// controller
	public AccountController() {
		this.accountService = new AccountService();
		this.clientService = new ClientService();
	}
	
	// registerEndpoints
	public void registerEndpoints(Javalin app) {
			
		app.get("/clients/{clientId}/accounts", getAllAccountsByClientId);
		app.get("/clients/{clientId}/accounts/{accountId}", getAccountByAccountId);

		app.post("/clients/{clientId}/accounts", openNewAccount);
		
		app.put("/clients/{clientId}/accounts/{accountId}", updateAmount);
		
		app.delete("/clients/{clientId}/accounts/{accountId}", deleteAccountByAccountId);
		
	}
	
	/*-- *********
	 * -- GET --
	 * *********
	 */
	
	// getAllAccountsByClientId
	private Handler getAllAccountsByClientId = (ctx) -> {
		
		logger.info("AccountController.getAllAccountsByClientId() incoked.");
		
		String clientId = ctx.pathParam("clientId");
		
		this.clientService.verifyClientId(clientId);
				
		String greaterThan = ctx.queryParam("greaterThan");
		String lessThan = ctx.queryParam("lessThan");
		
		List<Account> accounts = this.accountService.getAllAccountsByClientId(clientId, greaterThan, lessThan);
		
		ctx.json(accounts);
	};
	
	// getAccountByAccountId
	private Handler getAccountByAccountId = (ctx) -> {
		
		logger.info("AccountController.getAccountByAccountId() incoked.");
		
		String clientId = ctx.pathParam("clientId");
		String accountId = ctx.pathParam("accountId");
		
		this.clientService.verifyClientId(clientId);
		this.accountService.verifyClientIdAccountId(clientId, accountId);
		
		Account account = this.accountService.getAccountByAccountId(clientId, accountId);
		
		ctx.json(account);
		
	};
	
	
	/*- **********
	 * -- POST --
	 * **********
	 */
	
	// openNewAccount
	private Handler openNewAccount = (ctx) -> {
		
		logger.info("AccountController.openNewAccount() invoked.");
		
		String clientId = ctx.pathParam("clientId");
		
		this.clientService.verifyClientId(clientId);
		
		Account account = ctx.bodyAsClass(Account.class);
		
		Account a = this.accountService.openNewAccount(account, clientId);
		
		ctx.json(a);
	};
	
	
	/*- *********
	 * -- PUT --
	 * *********
	 */
	// updateAmount
	private Handler updateAmount = (ctx) -> {
		
		logger.info("AccountController.updateAmount() invoked.");
		
		String clientId = ctx.pathParam("clientId");
		String accountId = ctx.pathParam("accountId");
		
		this.clientService.verifyClientId(clientId);
		this.accountService.verifyClientIdAccountId(clientId, accountId);
		
		String deposit = ctx.queryParam("depositAmount");
		String withdraw = ctx.queryParam("withdrawAmount");
		
		Account a = this.accountService.updateAmount(clientId, accountId, deposit, withdraw);
		
		ctx.json(a);
	};
	
	/*- ************
	 * -- DELETE --
	 * ************
	 */

	// deleteAccountByAccountIdAndClientId
	private Handler deleteAccountByAccountId = (ctx) -> {
		
		logger.info("Account.Controller.deleteAccountByAccountId() invoked");
		
		String clientId = ctx.pathParam("clientId");
		String accountId = ctx.pathParam("accountId");
		
		logger.debug("clientId + accountId {}", clientId, accountId);
		this.accountService.verifyClientIdAccountId(clientId, accountId);
		this.accountService.deleteAccountByAccountId(clientId, accountId);
		
		ctx.json("Account with ID of " + accountId + " belonging to client with ID of " + clientId + " has been deleted.");
	};	
}
