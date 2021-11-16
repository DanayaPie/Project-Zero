package com.revature.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.AccountDAO;
import com.revature.dao.ClientDAO;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.AmountDoesNotExistException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.InvalidParameterException;
import com.revature.exceptions.OverdraftException;
import com.revature.model.Account;
import com.revature.model.Client;

public class AccountService {

	private Logger logger = LoggerFactory.getLogger(AccountService.class);

	private AccountDAO accountDao;
	private ClientDAO clientDao;
	private Account account;

	// constructor
	public AccountService() {
		this.accountDao = new AccountDAO();
		this.clientDao = new ClientDAO();
	}

	// constructor for mock AccountDAO object
	public AccountService(AccountDAO accountDao) {
		this.accountDao = accountDao;
	}

	// constructor for mock AccountDAO and ClientDAO objects
	public AccountService(AccountDAO accountDao, ClientDAO clientDao) {
		this.accountDao = accountDao;
		this.clientDao = clientDao;
	}

	/*-*********
	 * -- GET --
	 * *********
	 */

	// getAllAccountsByClientId
	public List<Account> getAllAccountsByClientId(String clientId, String greaterThan, String lessThan)
			throws SQLException, InvalidParameterException, AmountDoesNotExistException {

		logger.info("AccountService.getAllAccountsByClientId() invoked.");

		int clId = Integer.parseInt(clientId);

		List<Account> accounts = new ArrayList<>();

		/*-
		 *  Possibilites
		 *  1. greaterThan and lessthan not checked
		 *  2. both greatherThan and lessThan checked 
		 *  3. both checked but blanks
		 *  4. greaterThan check but blank
		 *  5. greaterThan exceed amount in bank ????
		 *  6. greatherThan check with amount
		 *  7. lessThan checked but blank
		 *  8. lessThan exceed amount in bank ?????
		 *  9. lessThan check with amount
		 */
		// 1.
		if (greaterThan == null && lessThan == null) {

			logger.info("GT and LT are not checked.");

			return accounts = this.accountDao.getAllAccountsByClientId(clId);
		}

		// 2.
		if (greaterThan != null && lessThan != null) {

			logger.info("GT and LT are are checked.");

			// 3. NOT REALLY NEED
			if (greaterThan.equals("") && lessThan.equals("")) {

				logger.info("GT and LT are blanks.");

				throw new InvalidParameterException("Greater than and less than amounts cannot be blank.");
			} else {

				logger.info("GT and LT have amounts.");

				double gtAmount = Double.parseDouble(greaterThan);
				double ltAmount = Double.parseDouble(lessThan);

				return accounts = this.accountDao.getAllAccountsByClientId(clId, gtAmount, ltAmount);
			}
		}

		// 4.
		if (greaterThan != null && lessThan == null) {

			logger.info("GT checked.");

			if (greaterThan.equals("")) {

				logger.info("GT is blank.");

				throw new InvalidParameterException("Greater than amounts cannot be blank.");

				// 6.
			} else {

//				logger.info("getAmount() {}", account.getAmount());
//				
				double gtAmount = Double.parseDouble(greaterThan);
//				
//				if (gtAmount > account.getAmount()) {
//				
//				logger.info("getAmount() {}", account.getAmount());
//				logger.info("GT amount is higherthan amount in bank.");
//				
//				throw new AmountDoesNotExistException("Amount requested exceed the funds in the accounts.");
//				
//				} else {

				logger.info("GT have amount.");

				return accounts = this.accountDao.getAllAccountsByClientId(clId, gtAmount, Integer.MAX_VALUE);

			}
		}

		// 7.
		if (greaterThan == null && lessThan != null) {

			logger.info("LT checked.");

			if (lessThan.equals("")) {

				logger.info("LT is blank.");

				throw new InvalidParameterException("Less than amounts cannot be blank.");

			// 9.
			} else {

				double ltAmount = Double.parseDouble(lessThan);

				logger.info("GT have amount.");

				return accounts = this.accountDao.getAllAccountsByClientId(clId, Integer.MIN_VALUE, ltAmount);
			}
		}

		return accounts;

//		// 4 + 5
//		if (greaterThan != null && lessThan == null && !greaterThan.equals("")) {
//
//			logger.info("GT checked and not blank");
//
//			int gtAmount = Integer.parseInt(greaterThan);
//			
//			logger.debug("gtAmmount {}", gtAmount);
//			
//			return accounts = this.accountDao.getAllAccountsByClientId(clId, gtAmount, Integer.MAX_VALUE);
//
//		// 6 + 7
//		} else if (greaterThan == null && lessThan != null && !lessThan.equals("")) {
//
//			logger.info("LT checked and not blank");
//
//			int ltAmount = Integer.parseInt(lessThan);
//
//			return accounts = this.accountDao.getAllAccountsByClientId(clId, Integer.MIN_VALUE, ltAmount);
//		}

//		return accounts;
	}

	// getAccountByAccountId
	public Account getAccountByAccountId(String clientId, String accountId) throws SQLException {

		logger.info("AccountService.getAccountByAccountId() invoked.");

		logger.info("clientId : {}", clientId);

		int clId = Integer.parseInt(clientId);
		int alId = Integer.parseInt(accountId);

		Account account = this.accountDao.getAccountByAccountId(clId, alId);

		return account;
	}

	/*- **********
	 * -- POST --
	 * **********
	 */

	// openNewAccount
	public Account openNewAccount(Account account, String clientId) throws SQLException, InvalidParameterException {

		logger.info("AccountService.openNewAccount() invoked.");

		/*-
		 *  Possibilities
		 *  1. accountType and amount are blank
		 *  2. accountType is blank
		 *  3. wrong accountType
		 *  4. amount is blank 
		 *  5. amount cannot less than 0
		 */

		// 1.
		if (account.getAccountType().trim().equals("") && account.getAmount() == null) {

			logger.info("Account type and amount are blank.");

			throw new InvalidParameterException("Account type and amount cannot be blank.");
		}

		// 2.
		if (account.getAccountType().trim().equals("")) {

			logger.info("Account type is blank.");

			throw new InvalidParameterException("Account type cannot be blank.");
		}

		// 3. -- possible account type
		Set<String> validAccountType = new HashSet<>();
		validAccountType.add("Checking");
		validAccountType.add("Saving");

		if (!validAccountType.contains(account.getAccountType())) {

			logger.info("Account type is not valid.");

			throw new InvalidParameterException("Account type entered is valid. Please, enter 'Checking' or 'Saving'.");
		}

		// 4.
		if (account.getAmount() == null) {

			logger.info("Amount is blank or less than 0.");
			logger.debug("Current account {}", account);

			throw new InvalidParameterException("Amount cannot be blank.");

		}

		// 5.
		logger.debug("getAmount() {}", account.getAmount());

		if (account.getAmount() <= 0) {

			logger.info("Amount is lessthan 0.0");

			throw new InvalidParameterException("Amount cannot be lessthan or equal to 0.");
		}

		Account accountAdded = this.accountDao.openNewAccount(account, clientId);

		return accountAdded;

	}

	/*- *********
	 * -- PUT --
	 * *********
	 */
	// updateAmount
	public Account updateAmount(String clientId, String accountId, String depositAmount, String withdrawAmount)
			throws SQLException, InvalidParameterException, OverdraftException {

		logger.info("AccountService.updateAmount() invoked().");

		int clId = Integer.parseInt(clientId);
		int acId = Integer.parseInt(accountId);

		// get info on the account
		Account account = this.accountDao.getAccountByAccountId(clId, acId);

		/*-
		 *  Possibilites
		 *  1. both deposit and withdraw are checked
		 *  2. did not choose deposit nor withdraw
		 *  3. deposit is checked but blank
		 *  4. deposit checked = add money
		 *  5. withdraw is checked but blank
		 *  6. withdraw checked = subtract money but cant be less than 
		 */

		// 1.
		if (depositAmount != null && withdrawAmount != null) {

			logger.info("deposit and withdraw are checked");

			throw new InvalidParameterException(
					"Cannot make deposit and withdraw at the same time. Please, choose to make deposit or withdraw.");
		}

		// 2.
		if (depositAmount == null && withdrawAmount == null) {

			logger.info("neither deposit nor withdaw was checked");

			throw new InvalidParameterException(
					"Neither deposit nor withdraw was checked. Please, choose to make deposit or withdraw.");
		}

		// 3.
		if (depositAmount != null) {

			if (depositAmount.equals("")) {

				logger.info("Deposit is checked but blank.");

				throw new InvalidParameterException("Deposit amount cannot be blank.");

			// 4.
			} else {

				logger.info("Deposit is checked");

				double amountDeposit = Double.parseDouble(depositAmount);
				double totalAmount = account.getAmount() + amountDeposit;

				logger.debug("totalAmount = account.getAmount() + depositAmount: {}{}{}", totalAmount,
						account.getAmount(), depositAmount);

				account.setAmount(totalAmount);
			}
		}

		// 5.
		if (withdrawAmount != null) {

			if (withdrawAmount.equals("")) {

				logger.info("Withdraw is checked but blank.");

				throw new InvalidParameterException("Withdraw amount cannot be blank.");

				// 6.
			} else {

				logger.info("withdraw is checked");

				double amountWithdraw = Double.parseDouble(withdrawAmount);

				if (amountWithdraw > account.getAmount()) {

					throw new OverdraftException(
							"Unable to withdraw " + withdrawAmount + " due to not enough funds in the account.");
				}

				double totalAmount = account.getAmount() - amountWithdraw;
				account.setAmount(totalAmount);
			}

		}

		accountDao.updateAmount(account);

		return account;
	}

	/*- ************
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

	/*- ****************
	 * -- VERIFY IDs --
	 * ****************
	 */

	// verifyClientIdAccountId
	public void verifyClientIdAccountId(String clientId, String accountId)
			throws SQLException, ClientNotFoundException, AccountNotFoundException, InvalidParameterException {

		logger.info("AccountService.verifyClientIdAccountId() invoked.");
		logger.info("Client Id {} Account Id {}", clientId, accountId);

		try {

			Integer clId = Integer.parseInt(clientId);
			int alId = Integer.parseInt(accountId);

			logger.info("clId {} alId {}", clId, alId);
			logger.info("clientDao {} alId {}", clientDao);

			Client client = this.clientDao.getClientByClientId(clId);
			logger.info("Client {}", client);

			Account account = this.accountDao.getAccountByAccountId(clId, alId);

			/*-
			 * Possibilities
			 *   1. client exist, but no account with the account ID
			 *   2. client exist and account exist
			 */

			// 1.
			if (client != null && account == null) {

				logger.info("client exist, but no account with the account ID.");

				throw new AccountNotFoundException(
						"Client with ID of " + clientId + " does not have an account with ID of " + accountId + ".");

				// 2.
			} else {

				logger.info("client exist and account exist");

			}

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Client and/or account ID provided is not an int convertable value.");
		}
	}
}