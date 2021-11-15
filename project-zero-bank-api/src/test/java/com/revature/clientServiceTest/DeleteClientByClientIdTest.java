package com.revature.clientServiceTest;

import static org.mockito.Mockito.mock;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.revature.dao.ClientDAO;
import com.revature.model.Client;
import com.revature.service.ClientService;

public class DeleteClientByClientIdTest {

	/*
	 * ARRANGE
	 */
	ClientDAO mockClientDao = mock(ClientDAO.class);
	ClientService mockClientService = new ClientService(mockClientDao);
	
	/* ********************
	 * -- POSITIVE TESTS --
	 * ********************
	 */
	
	@Test
	public void testDeleteClientByClientId() throws SQLException {
		
		/*
		 * ARRANGE
		 */
		Client client = new Client(96, "Jane", "Doe", "05162000");
		
		
		
		
	}
	
}
