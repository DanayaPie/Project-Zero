package com.revature.clientServiceTest;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revature.dao.ClientDAO;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.InvalidParameterException;
import com.revature.model.Client;
import com.revature.service.ClientService;

public class VerifyClientIdTest {

	/*-
	 * ARRANGE
	 */
	ClientDAO mockClientDao = mock(ClientDAO.class);
	ClientService mockClientService = new ClientService(mockClientDao);

	/*- ********************
	 * -- POSITIVE TESTS --
	 * ********************
	 */

	@Test
	public void testVerifyClientIdPositive() throws SQLException {

		when(mockClientDao.getClientByClientId(eq(1))).thenReturn(new Client(1, "Jane", "Dao", "03051970"));

		Client actual = mockClientService.getClientByClientId("1");

		Client expected = new Client(1, "Jane", "Dao", "03051970");
		Assertions.assertEquals(expected, actual);
	}

	/*- ********************
	 * -- NEGATIVE TESTS --
	 * 
	 * ********************
	 */

	// Exception Occurs 
	@Test
	public void testVerifyClientIdExceptionOccursNegative() throws SQLException {
	
		when(mockClientDao.getClientByClientId(1)).thenThrow(SQLException.class);
		
		Assertions.assertThrows(SQLException.class, () -> {
			mockClientService.getClientByClientId("1");
		});
	}

	// ClientNotFoundException
	@Test
	public void testVerifyClientIdClientNotFoundExceptionNegative() throws SQLException {

		Assertions.assertThrows(ClientNotFoundException.class, () -> {
			mockClientService.verifyClientId("96");
		});
	}

	// InvalidParameterException for alphabetical ID
	@Test
	public void testVerifyClientIdAlphabeticalIdNegative() throws SQLException {

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockClientService.verifyClientId("abc");
		});
	}

	// InvalidParameterException for decimal ID
	@Test
	public void testVerifyClientIdDecimalIdNegative() throws SQLException {

		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockClientService.verifyClientId("9.6");
		});
	}
}
