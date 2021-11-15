package com.revature.accountServiceTest;

import static org.mockito.Mockito.mock;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.revature.dao.AccountDAO;
import com.revature.model.Account;
import com.revature.service.AccountService;

public class GetAccountsByClientIdTest {

	AccountDAO mockAccountDao = mock(AccountDAO.class);
	AccountService mockAccountService = new AccountService(mockAccountDao);
	
	/* ********************
	 * -- POSITIVE TESTS --
	 * ********************
	 */
	@Test
	public void testGetAccountsByClientIdPositive() throws SQLException {
		
	}
}
