package com.revature.accountServiceTest;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revature.dao.AccountDAO;
import com.revature.exceptions.InvalidParameterException;
import com.revature.model.Account;
import com.revature.service.AccountService;

public class OpenNewAccountTest {

	AccountDAO mockAccountDao = mock(AccountDAO.class);
	AccountService mockAccountService = new AccountService(mockAccountDao);
	
	/* ********************
	 * -- POSITIVE TESTS --
	 * ********************-
	 */
	
	@Test
	public void testOpenNewAccountPositive() throws SQLException, InvalidParameterException {
		
		/*
		 * ARRANGE
		 */
		Account account = new Account(1, "Checking", 100.);
		when(mockAccountDao.openNewAccount(eq(account), eq("96"))).thenReturn(new Account(96, 1, "Checking", 100.00));
		
		/*
		 * ACT
		 */
		Account accountOpened = new Account(1, "Checking", 100.00);
		Account actual = mockAccountService.openNewAccount(accountOpened, "96");
		
		/*
		 * ASSERT
		 */
		Account expected = new Account(96, 1, "Checking", 100.00);
		Assertions.assertEquals(expected, actual);
	}
	
	/* ********************
	 * -- NEGATIVE TESTS --
	 * ********************
	 */
	
	// Exception Occur
	@Test
	public void testOpenNewAccountExceptionOccursNegative() throws SQLException {
		
		Account account = new Account(1, "Checking", 100.);
		when(mockAccountDao.openNewAccount(eq(account), eq("96"))).thenThrow(SQLException.class);
			
		Assertions.assertThrows(SQLException.class, () -> {
			mockAccountService.openNewAccount(account, "96");
		});
	}
	
	// Account type and amount are blank
	@Test
	public void testOpenNewAccountAccountTypeAndAmountAreBlankNegative() throws SQLException {
		
		Account account = new Account(1, "", null);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockAccountService.openNewAccount(account, "96");
		});
	}
	
	// Invalid account type but amount valid
	@Test
	public void testOpenNewAccountInvalidAccountTypeButAmountValidNegative() throws SQLException {
		
		Account account = new Account(1, "Credit", 100.);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockAccountService.openNewAccount(account, "96");
		});
	}
	
	// Account Type is blank but amount valid
	@Test
	public void testOpenNewAccountAccountTypeIsBlankButAmountValidNegative() throws SQLException {
		
		Account account = new Account(1, "", 100.);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockAccountService.openNewAccount(account, "96");
		});
	}
	
	// Amount is 0 but account type valid
	@Test
	public void testOpenNewAccountValidAccountTypeButAmountIsZeroNegative() throws SQLException {
		
		Account account = new Account(1, "Saving", 0.);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockAccountService.openNewAccount(account, "96");
		});
	}
	
	// Amount is negative but account type valid
	@Test
	public void testOpenNewAccountValidAccountTypeButAmountIsNegativeNegative() throws SQLException {
		
		Account account = new Account(1, "Saving", -500.);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockAccountService.openNewAccount(account, "96");
		});
	}
	
	// Valid account type but amount is blank
	@Test
	public void testOpenNewAccountValidAccountTypeButAmountIsBlankNegative() throws SQLException {
		
		Account account = new Account(1, "Saving", null);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockAccountService.openNewAccount(account, "96");
		});
	}
}
