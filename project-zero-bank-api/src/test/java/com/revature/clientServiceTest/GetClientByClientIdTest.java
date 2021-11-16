package com.revature.clientServiceTest;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revature.dao.ClientDAO;
import com.revature.model.Client;
import com.revature.service.ClientService;

public class GetClientByClientIdTest {

	/*
	 * ARRANGE
	 */
	ClientDAO mockClientDao = mock(ClientDAO.class);
	ClientService mockClientService = new ClientService(mockClientDao);
	
	/* *******************
	 * -- POSITIVE TESTS --
	 * *******************
	 */
	
	@Test
	public void testGetClientByClientIdPositive() throws SQLException {
		
		/*
		 * ARRANGE
		 */
		when(mockClientDao.getClientByClientId(eq(1)))
		.thenReturn(new Client(1, "Jane", "Dao", "03051970"));

		/*
		 * ACT
		 */
		Client actual = mockClientService.getClientByClientId("1");
		
		/*
		 * ASSERT
		 */
		Client expected = new Client(1, "Jane", "Dao", "03051970");
		Assertions.assertEquals(expected, actual);
	}
	
	/* *******************
	 * -- NEGATIVE TESTS --
	 * *******************
	 */
	
	@Test 
	public void testGetClientByClientIdExceptionOccurNegative() throws SQLException {
		
		/*
		 * ARRANGE
		 */
		when(mockClientDao.getClientByClientId(1)).thenThrow(SQLException.class);
		
		/*
		 * ACT and ASSERT
		 */
		Assertions.assertThrows(SQLException.class, () -> {
			mockClientService.getClientByClientId("1");
		});
	}
}
