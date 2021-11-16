package com.revature.clientServiceTest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revature.dao.ClientDAO;
import com.revature.model.Client;
import com.revature.service.ClientService;

public class GetAllClientsTest {

	ClientDAO mockClientDao = mock(ClientDAO.class); 
	ClientService mockClientService = new ClientService(mockClientDao);
	
	/* *******************
	 * -- POSITIVE TESTS --
	 * *******************
	 */
	
	@Test 
	public void testGetAllClientsPositive() throws SQLException {
		
		/*
		 *  ARRANGE
		 */	
		Client client1 = new Client(1, "Jane", "Doe", "01011956");
		Client client2 = new Client(215, "John", "Doe", "05251868");
		
		List<Client> clientsFromDao = new ArrayList<>();
		clientsFromDao.add(client1);
		clientsFromDao.add(client2);
		
		when(mockClientDao.getAllClients()).thenReturn(clientsFromDao);

		/*
		 *  ACT
		 */
		List<Client> actual = mockClientService.getAllClients();
		
		/*
		 *  ASSERT
		 */
		List<Client> expected = mockClientService.getAllClients();
		expected.add(new Client(1, "Jane", "Doe", "01011956"));
		expected.add(new Client(215, "John", "Doe", "05251868"));
		
		Assertions.assertEquals(expected, actual);
				
	}
	
	/* ********************
	 * -- NEGATIVE TESTS --
	 * ********************
	 */
	
	// Exception Occurs
	@Test
	public void testGetAllStudentsExecptionOccursNegative() throws SQLException {
		
		/*
		 * ARRANGE
		 */
		when(mockClientDao.getAllClients()).thenThrow(SQLException.class);
		
		/*
		 * ACT and ASSERT
		 */
		// ASSERT
		Assertions.assertThrows(SQLException.class, () -> {
			// ACT
			mockClientService.getAllClients();
		});
	}
}
