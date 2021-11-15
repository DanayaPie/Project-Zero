package com.revature.clientServiceTest;

import static org.mockito.Mockito.mock;

import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revature.dao.ClientDAO;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.InvalidParameterException;
import com.revature.service.ClientService;

public class VerifyClientIdTest {

	/*
	 * ARRANGE
	 */
	ClientDAO clientDao = mock(ClientDAO.class);
	ClientService mockClientService = new ClientService(clientDao);
	
	/* ********************
	 * -- POSITIVE TESTS --
	 * ********************
	 */
	
	// Positive test is the same as GetClientByClientIdTest.testGetClientByClientIdPositive()
	
	/* ********************
	 * -- NEGATIVE TESTS --
	 * ********************
	 */
	
	// SQLException
	/*
	 *  ExceptionOccurNegative test is the same as 
	 *  GetClientByClientIdTest.testGetClientByClientIdExceptionOccurNegative()
	 */

	// ClientNotFoundException
	@Test
	public void testVerifyClientIdClientNotFoundExceptionNegative() throws SQLException {
		
		Assertions.assertThrows(ClientNotFoundException.class, () -> {
			mockClientService.verifyClientId("96");
		});
	}
	
	// InvalidParameterException for alphabetical
	@Test
	public void testVerifyClientIdAlphabeticalIdNegative() throws SQLException {
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockClientService.verifyClientId("abc");
		});
	}
	
	// InvalidParameterException for decimal
	@Test
	public void testVerifyClientIdDecimalIdNegative() throws SQLException {
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockClientService.verifyClientId("9.6");
		});
	}
}
