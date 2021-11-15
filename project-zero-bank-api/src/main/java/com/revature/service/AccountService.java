package com.revature.service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.AccountDAO;
import com.revature.dao.ClientDAO;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.InvalidParameterException;
import com.revature.exceptions.OverdraftException;
import com.revature.model.Account;
import com.revature.model.Client;

import io.javalin.http.Context;

public class AccountService {

	private Logger logger = LoggerFactory.getLogger(AccountService.class);
	
	private AccountDAO accountDao;
	private ClientDAO clientDao;
	
	// constructor
	public AccountService() {
		this.accountDao = new AccountDAO();
		this.clientDao = new ClientDAO();
	}
	
	// constructor for mock AccountDAO object
	public AccountService(AccountDAO accountDao) {
		this.accountDao = accountDao;
	}
	
	/* *********
	 * -- GET --
	 * *********
	 */
	
	// getAllAccountsByClientId
	public List<Account> getAllAccountsByClientId(String clientId) throws SQLException {
		
		logger.info("AccountService.getAllAccountsByClientId() invoked.");
		
			int clId = Integer.parseInt(clientId);
			
			List<Account> accounts = this.accountDao.getAllAccountsByClientId(clId);	
			
			return accounts;
	}
		
	// getAccountByAccountId
	public Account getAccountByAccountId(String clientId, String accountId) throws SQLException {
		
		logger.info("AccountService.getAccountByAccountId() invoked.");
		
		int clId = Integer.getInteger(clientId);
		int alId = Integer.getInteger(accountId);
		
		Account account = this.accountDao.getAccountByAccountId(clId, alId);
		
		return account; 
	}
	
	/* **********
	 * -- POST --
	 * **********
	 */
	
	// openNewAccount
	public Account openNewAccount(Account account, String clientId) throws SQLException, InvalidParameterException {
		
		logger.info("AccountService.openNewAccount() invoked.");
		
//		int clId = Integer.getInteger(clientId);
		
		/*
		 *  Possibilities
		 *  1. clientdId is blank
		 *  2. accountType is blank
		 *  3. wrong accountType
		 *  4. amount is blank or less than 0
		 */
		
		// 1.
		if (account.getClientId() == null) {
			
			logger.info("Client ID is blank.");
			
			throw new InvalidParameterException("Client ID cannot be blank.");
		}
		
		// 2.
		if (account.getAccountType().trim().equals("")) {
			
			logger.info("Account type is blank.");
			
			throw new InvalidParameterException("Account type cannot be blank.");
		}
		
		// 3. -- possible account type
		Set<String>	validAccountType = new HashSet<>();
		validAccountType.add("Checking");
		validAccountType.add("Saving");
		
		if (!validAccountType.contains(account.getAccountType()) ) {
			
			logger.info("Account type is not valid.");
			
			throw new InvalidParameterException("Account type entered is valid. Please, enter 'Checking' or 'Saving'.");
		}
				
		// 4.
		if (account.getAmount() == null) {
			
			logger.info("Amount is blank or less than 0.");
			logger.debug("Current account {}",account);
			
			throw new InvalidParameterException("Amount cannot be blank.");
		} else if (account.getAmount() < 0) {
			
			logger.info("Amount is lessthan 0");
			
			throw new InvalidParameterException("Amount cannot be lessthan 0.");
		}
		
		Account accountAdded = this.accountDao.openNewAccount(account, clientId);
		
		return accountAdded;
	
	}
	
	/* *********
	 * -- PUT --
	 * *********
	 */
	// updateAmount
	public Account updateAmount(String clientId, String accountId, String deposit, String withdraw) throws SQLException, InvalidParameterException, OverdraftException {
		
		logger.info("AccountService.updateAmount() invoked().");
		
		int clId = Integer.parseInt(clientId);
		int acId = Integer.parseInt(accountId);
		
		// get info on the account
		Account account = this.accountDao.getAccountByAccountId(clId, acId);
		
		/*
		 *  Possibilites
		 *  1. both deposit and withdraw are checked
		 *  2. deposit checked = add money
		 *  3. withdraw checked = subtract money but cant be less than
		 *  4. did not choose deposit nor withdraw
		 */
		
		// 1.
		if (deposit != null && withdraw != null) {
			
			logger.info("deposit and withdraw are checked");
			
			throw new InvalidParameterException("Cannot make deposit and withdraw at the same time. Please, choose to make deposit or withdraw.");
		}
		
		// 2.
		if (deposit != null) {
			
			logger.info("deposit is checked");
			
			double depositAmount = Double.parseDouble(deposit);
			
			double totalAmount = account.getAmount() + depositAmount;
			
			logger.info("totalAmount = account.getAmount() + depositAmount: {}{}{}", totalAmount, account.getAmount(), depositAmount);
			
			account.setAmount(totalAmount);
		}
		
		// 3.
		if (withdraw != null) {
			
			logger.info("withdraw is checked");

			double withdrawAmount = Double.parseDouble(withdraw);
			
			if (withdrawAmount > account.getAmount()) {
				throw new OverdraftException("Unable to withdraw " + withdrawAmount + " due to not enough funds in the account.");
			}
			
			double totalAmount = account.getAmount() - withdrawAmount;
			account.setAmount(totalAmount);
		}
		
		// 4.
		if (deposit == null && withdraw == null) {
			
			logger.info("neither deposit nor withdaw was checked");
			
			throw new InvalidParameterException("Neither deposit nor withdraw was checked. Please, choose to make deposit or withdraw.");
		}
		
		accountDao.updateAmount(account);
		
		return account; 
	}
	
	
	/* ************
	 * -- DELETE --
	 * ************
	 */
	
	// deleteAccountByAccountId
	public void deleteAccountByAccountId(String clientId, String accountId) throws SQLException {
		
		logger.info("AccountService.deleteAccountByAccountId() invoked.");
		
		int clId = Integer.parseInt(clientId);
		int alId = Integer.parseInt(accountId);
		
		this.accountDao.getAccountByAccountId(clId, alId);
		
		this.accountDao.deleteAccountByAccountId(alId);
	}
	
	
	/* ****************
	 * -- VERIFY IDs --
	 * ****************
	 */

	// verifyClientIdAccountId
	public void verifyClientIdAccountId (String clientId, String accountId) 
			throws SQLException, ClientNotFoundException, AccountNotFoundException, InvalidParameterException {
		
		logger.info("AccountService.verifyClientIdAccountId() invoked.");
		
		try {
			
			int clId = Integer.parseInt(clientId);
			int alId = Integer.parseInt(accountId);

			Client client = this.clientDao.getClientByClientId(clId);
			Account account = this.accountDao.getAccountByAccountId(clId, alId);
			
			/*
			 * Possibilities
			 *   1. client exist, but no account with the account ID
			 *   2. client exist and account exist
			 */
			
			// 1.
			if (client != null && account == null) {
				
				logger.info("client exist, but no account with the account ID.");
				
				throw new AccountNotFoundException("Client with ID of " + clientId + " does not have an account with ID of " + accountId + ".");
			
			// 2.
			} else {
				
				logger.info("client exist and account exist");
								
			}
			
		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Client and/or account ID provided is not an int convertable value.");
		}
	}
}