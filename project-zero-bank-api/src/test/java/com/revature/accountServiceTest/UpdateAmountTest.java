package com.revature.accountServiceTest;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revature.dao.AccountDAO;
import com.revature.exceptions.InvalidParameterException;
import com.revature.exceptions.OverdraftException;
import com.revature.model.Account;
import com.revature.service.AccountService;

public class UpdateAmountTest {

	AccountDAO mockAccountDao = mock(AccountDAO.class);
	AccountService mockAccountService = new AccountService(mockAccountDao);

	Account testAccount = new Account() {
		{
			setAccountId(1);
			setClientId(96);
			setAmount(500.0);
		}

	};

	/* ********************
	 * -- POSITIVE TESTS --
	 * ********************
	 */

	// deposit
	@Test
	public void testUpdateAmountDepositPositive() throws SQLException, InvalidParameterException, OverdraftException {

		when(mockAccountDao.getAccountByAccountId(eq(96), eq(1))).thenReturn(new Account(1, 96, "Checking", 500.));

		Account actual = mockAccountService.updateAmount("96", "1", "100", null);

		Account expected = new Account(1, 96, "Checking", 600.);
		Assertions.assertEquals(expected, actual);
	}

	// withdraw
	@Test
	public void testUpdateAmountWithdrawPositive() throws SQLException, InvalidParameterException, OverdraftException {

		when(mockAccountDao.getAccountByAccountId(eq(96), eq(1))).thenReturn(new Account(1, 96, "Checking", 500.));

		Account actual = mockAccountService.updateAmount("96", "1", null, "300");

		Account expected = new Account(1, 96, "Checking", 200.);
		Assertions.assertEquals(expected, actual);
	}

	/* ********************
	 * -- NEGATIVE TESTS --
	 * ********************
	 */
	
	// Deposit exception occurs
	@Test
	public void testUpdateAmountDepositExceptionOccursNegative() throws SQLException {

		when(mockAccountDao.getAccountByAccountId(eq(96), eq(1))).thenThrow(SQLException.class);

		Assertions.assertThrows(SQLException.class, () -> {
			mockAccountService.updateAmount("96", "1", "600", null);
		});
	}

	// Withdraw exception occurs
	@Test
	public void testUpdateAmountWithdrawExceptionOccursNegative() throws SQLException {

		when(mockAccountDao.getAccountByAccountId(eq(96), eq(1))).thenThrow(SQLException.class);

		Assertions.assertThrows(SQLException.class, () -> {
			mockAccountService.updateAmount("96", "1", null, "200");
		});
	}

	// deposit and withdraw are not null
	@Test
	public void testUpdateAmountDepositAndWithdrawNotNullNegative() throws SQLException {

		when(mockAccountDao.getAccountByAccountId(eq(96), eq(1))).thenReturn(testAccount);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockAccountService.updateAmount("96", "1", "500", "200");
		});
	}

	// deposit and withdraw are null
	@Test
	public void testUpdateAmountDepositAndWithdrawAreNullNegative() throws SQLException {

		when(mockAccountDao.getAccountByAccountId(eq(96), eq(1))).thenReturn(testAccount);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockAccountService.updateAmount("96", "1", null, null);
		});
	}

	// depositAmount is blank
	@Test
	public void testUpdateAmountDepositAmountBlankNegative() throws SQLException {

		when(mockAccountDao.getAccountByAccountId(eq(96), eq(1))).thenReturn(testAccount);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockAccountService.updateAmount("96", "1", "", null);
		});
	}

	// withDrawAmount is blank
	@Test
	public void testUpdateAmountWithdrawAmountBlankNegative() throws SQLException {

		when(mockAccountDao.getAccountByAccountId(eq(96), eq(1))).thenReturn(testAccount);

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockAccountService.updateAmount("96", "1", null, "");
		});
	}
}
