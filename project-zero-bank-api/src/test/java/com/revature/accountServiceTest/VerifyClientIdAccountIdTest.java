package com.revature.accountServiceTest;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revature.dao.AccountDAO;
import com.revature.dao.ClientDAO;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.InvalidParameterException;
import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.service.AccountService;

public class VerifyClientIdAccountIdTest {

	ClientDAO mockClientDao = mock(ClientDAO.class);

	AccountDAO mockAccountDao = mock(AccountDAO.class);
	AccountService mockAccountService = new AccountService(mockAccountDao, mockClientDao);

	/*- ********************
	 * -- POSITIVE TESTS --
	 * ********************
	 */

	@Test
	public void testVerifyClientIdAccountIdPositive() throws SQLException {

		when(mockClientDao.getClientByClientId(eq(96))).thenReturn(new Client(96, "Jane", "Doe", "12345678"));

		when(mockAccountDao.getAccountByAccountId(eq(96), eq(1))).thenReturn(new Account(1, 96, "Checking", 90000.));

		Account actual = mockAccountService.getAccountByAccountId("96", "1");

		Account expected = new Account(1, 96, "Checking", 90000.);
		Assertions.assertEquals(expected, actual);
	}

	/*- ********************
	 * -- NEGATIVE TESTS --
	 * ********************
	 */

	// Exception occurs
	@Test
	public void testVerifyClientIdAccountIdExceptionOccursNegative() throws SQLException {

		when(mockAccountDao.getAccountByAccountId(eq(96), eq(1))).thenThrow(SQLException.class);

		Assertions.assertThrows(SQLException.class, () -> {
			mockAccountService.getAccountByAccountId("96", "1");
		});
	}

	// AccountNotFoundException
	@Test
	public void testVerifyClientIdAccountIdAccountNotFoundExceptionNegative() throws SQLException {

		when(mockClientDao.getClientByClientId(eq(96))).thenReturn(new Client(96, "Jane", "Doe", "12345678"));

		when(mockAccountDao.getAccountByAccountId(eq(96), eq(1))).thenReturn(null);

		Assertions.assertThrows(AccountNotFoundException.class, () -> {
			mockAccountService.verifyClientIdAccountId("96", "96");
		});
	}

	// InvalidParameterException for alphabetical account ID
	@Test
	public void testVerifyClientIdAccountIdAccountIdAlphabeticalNegative() throws SQLException {

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockAccountService.verifyClientIdAccountId("96", "abc");
		});
	}
	
	// InvalidParameterException for decimal account ID
	@Test
	public void testVerifyClientIdAccountIdAccountIdDecimalNegative() throws SQLException {

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockAccountService.verifyClientIdAccountId("96", "9.6");
		});
	}
}