package com.revature.accountServiceTest;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.util.thread.strategy.ExecuteProduceConsume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revature.dao.AccountDAO;
import com.revature.exceptions.AmountDoesNotExistException;
import com.revature.exceptions.InvalidParameterException;
import com.revature.model.Account;
import com.revature.service.AccountService;

public class GetAccountsByClientIdTest {

	/*--
	 * ARRANGE
	 */
	AccountDAO mockAccountDao = mock(AccountDAO.class);
	AccountService mockAccountService = new AccountService(mockAccountDao);

	/*-- ********************
	 * -- POSITIVE TESTS --
	 * ********************
	 */

	// get all accounts by cliend Id
	@Test
	public void testGetAllAccountsByClientIdOnlyPositive()
			throws SQLException, InvalidParameterException, AmountDoesNotExistException {

		/*-
		 * ARRANGE
		 */
		List<Account> accountList = new ArrayList<>();
		accountList.add(new Account(1, 1, "checking", 5000.00));
		accountList.add(new Account(2, 1, "saving", 6000.00));
		accountList.add(new Account(3, 1, "saving", 7000.00));
		accountList.add(new Account(4, 1, "saving", 8000.00));

		when(mockAccountDao.getAllAccountsByClientId(eq(1))).thenReturn(accountList);

		List<Account> actual = mockAccountService.getAllAccountsByClientId("1", null, null);

		/*-
		 * ASSERT
		 */
		Assertions.assertEquals(accountList, actual);
	}

	// get account between greaterThan and lessThan
	@Test
	public void testGetAllAccountsByClientIdBetweenGreaterThanLessThanPositive()
			throws SQLException, InvalidParameterException, AmountDoesNotExistException {

		List<Account> accountList = new ArrayList<>();
		accountList.add(new Account(1, 1, "checking", 5000.00));
		accountList.add(new Account(2, 1, "saving", 6000.00));

		when(mockAccountDao.getAllAccountsByClientId(eq(1), eq(4000.0), eq(6500.0))).thenReturn(accountList);

		List<Account> actualList = mockAccountService.getAllAccountsByClientId("1", "4000", "6500");
		
		List<Account> expectedList = new ArrayList<>();
		expectedList.add(new Account(1, 1, "checking", 5000.00));
		expectedList.add(new Account(2, 1, "saving", 6000.00));
		
		Assertions.assertEquals(expectedList, actualList);

	}
	
	// get account greater than only
	@Test
	public void testGetAllAccountsByClientIdGreaterThanOnlyPositive()
			throws SQLException, InvalidParameterException, AmountDoesNotExistException {

		List<Account> accountList = new ArrayList<>();
		accountList.add(new Account(1, 1, "checking", 5000.00));
		accountList.add(new Account(2, 1, "saving", 6000.00));

		when(mockAccountDao.getAllAccountsByClientId(eq(1), eq(4000.0), eq(Integer.MAX_VALUE))).thenReturn(accountList);

		List<Account> actualList = mockAccountService.getAllAccountsByClientId("1", "4000", null);
		
		List<Account> expectedList = new ArrayList<>();
		expectedList.add(new Account(1, 1, "checking", 5000.00));
		expectedList.add(new Account(2, 1, "saving", 6000.00));
		
		Assertions.assertEquals(expectedList, actualList);

	}
	
	// get account less than only
	@Test
	public void testGetAllAccountsByClientIdLessThanOnlyPositive()
			throws SQLException, InvalidParameterException, AmountDoesNotExistException {

		List<Account> accountList = new ArrayList<>();
		accountList.add(new Account(1, 1, "checking", 5000.00));
		accountList.add(new Account(2, 1, "saving", 6000.00));

		when(mockAccountDao.getAllAccountsByClientId(eq(1), eq(Integer.MIN_VALUE), eq(6500.0))).thenReturn(accountList);

		List<Account> actualList = mockAccountService.getAllAccountsByClientId("1", null, "6500");
		
		List<Account> expectedList = new ArrayList<>();
		expectedList.add(new Account(1, 1, "checking", 5000.00));
		expectedList.add(new Account(2, 1, "saving", 6000.00));
		
		Assertions.assertEquals(expectedList, actualList);

	}

	/*- ********************
	 * -- NEGATIVE TESTS --
	 * ********************
	 */

	// Exception Occur
	@Test
	public void testGetAllAccountsByClientIdExceptionOccurNegative() throws SQLException {

		when(mockAccountDao.getAllAccountsByClientId(1)).thenThrow(SQLException.class);

		Assertions.assertThrows(SQLException.class, () -> {
			mockAccountService.getAllAccountsByClientId("1", null, null);
		});
	}

	// depositAmount is checked but blank
	@Test
	public void testGetAllAccountsByClientIdDepositAmountBlankNegative() throws SQLException {

		when(mockAccountDao.getAccountByAccountId(eq(96), eq(1))).thenReturn(new Account(1, 96, "Checking", 500.));

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockAccountService.getAllAccountsByClientId("96", "", null);
		});
	}

	// withdrawAmount is checked but blank
	@Test
	public void testGetAllAccountsByClientIdWithdrawAmountBlankNegative() throws SQLException {

		when(mockAccountDao.getAccountByAccountId(eq(96), eq(1))).thenReturn(new Account(1, 96, "Checking", 500.));

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockAccountService.getAllAccountsByClientId("96", null, "");
		});
	}

	// both depositAmount and withdrawAmount are checked but blank
	@Test
	public void testGetAllAccountsByClientIdDepositAndWithdrawAmountBlankNegative() throws SQLException {

		when(mockAccountDao.getAccountByAccountId(eq(96), eq(1))).thenReturn(new Account(1, 96, "Checking", 500.));

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockAccountService.getAllAccountsByClientId("96", "", "");
		});
	}
}
