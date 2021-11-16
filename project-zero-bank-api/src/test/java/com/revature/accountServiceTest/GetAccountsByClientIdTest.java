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
import com.revature.exceptions.InvalidParameterException;
import com.revature.model.Account;
import com.revature.service.AccountService;

public class GetAccountsByClientIdTest {

	/*
	 * ARRANGE
	 */
	AccountDAO mockAccountDao = mock(AccountDAO.class);
	AccountService mockAccountService = new AccountService(mockAccountDao);
	
	/* ********************
	 * -- POSITIVE TESTS --
	 * ********************
	 */
	@Test
	public void testGetAllAccountsByClientIdPositive() throws SQLException, InvalidParameterException {
		
		/*
		 * ARRANGE
		 */
		List<Account> accountList = new ArrayList<Account>();
		accountList.add(new Account(1, 1, "checking", 5000.00));
		accountList.add(new Account(2, 1, "saving", 5001.00));
		accountList.add(new Account(3, 1, "saving", 5050.00));
		accountList.add(new Account(4, 1, "saving", 6000.00));
		
		when(mockAccountDao.getAllAccountsByClientId(eq(1)))
		.thenReturn(accountList);
		
		List<Account> actual = mockAccountService.getAllAccountsByClientId("1", null, null);
		
		/*
		 * ASSERT
		 */
		Assertions.assertEquals(accountList, actual);
	}
	
	/* ********************
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
}
