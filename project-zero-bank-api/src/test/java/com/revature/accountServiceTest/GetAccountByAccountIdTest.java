package com.revature.accountServiceTest;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revature.dao.AccountDAO;
import com.revature.model.Account;
import com.revature.service.AccountService;

public class GetAccountByAccountIdTest {

	AccountDAO mockAccountDao = mock(AccountDAO.class);
	AccountService mockAccountService = new AccountService(mockAccountDao);
	
	/* ********************
	 * -- POSITIVE TESTS --
	 * ********************
	 */
	
	@Test
	public void testGetAccountByAccountIdPositive() throws SQLException {
		
		when(mockAccountDao.getAccountByAccountId(eq(96), eq(1)))
		.thenReturn(new Account(1, 96, "Saving", 100.00));
		
		Account actual = mockAccountService.getAccountByAccountId("96", "1");
		
		Account expected = new Account(1, 96, "Saving", 100.00);
		Assertions.assertEquals(expected, actual);
	}
	
	/* ********************
	 * -- NEGATIVE TESTS --
	 * ********************
	 */
	
	@Test
	public void testGetAccountByAccountIdExceptionOccurNegative() throws SQLException {
		
		when(mockAccountDao.getAccountByAccountId(eq(96), eq(1))).thenThrow(SQLException.class);
		
		Assertions.assertThrows(SQLException.class, () -> {
			mockAccountService.getAccountByAccountId("96", "1");
		});
	}
}
