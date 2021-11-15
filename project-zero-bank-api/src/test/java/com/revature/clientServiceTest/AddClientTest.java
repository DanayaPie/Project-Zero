package com.revature.clientServiceTest;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revature.dao.ClientDAO;
import com.revature.exceptions.InvalidParameterException;
import com.revature.model.Client;
import com.revature.service.ClientService;

public class AddClientTest {

	/*
	 *  ARRANGE
	 */
	ClientDAO mockClientDao = mock(ClientDAO.class);
	ClientService mockClientService = new ClientService(mockClientDao);	
	
	/* ********************
	 * -- POSITIVE TESTS --
	 * ********************
	 */
	
	@Test
	public void testAddClientInfoCorrectPositive() throws SQLException, InvalidParameterException {
		
		/*
		 * ARRANGE
		 */
		Client client = new Client("Jane", "Doe", "05162000");
		when(mockClientDao.addClient(eq(client))).thenReturn(new Client(97, "Jane", "Doe", "05162000"));
				
		/*
		 * ACT
		 */
		Client clientAdded = new Client("Jane", "Doe", "05162000");
		Client actual = mockClientService.addClient(clientAdded);
		
		/*
		 * ASSERT
		 */
		Client expected = new Client(97, "Jane", "Doe", "05162000");
		Assertions.assertEquals(expected, actual);
	}
	
	/* ********************
	 * -- NEGATIVE TESTS --
	 * ********************
	 */
	
	// firstName blank but everthing else valid
	@Test
	public void testAddClientFirstNameBlankEverythingElseValidNegative() throws SQLException, InvalidParameterException {		
		
		/*
		 * ACT and ASSERT
		 */
		Client client = new Client("", "Doe", "05162000");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockClientService.addClient(client);
		});
	}
	
	// lastName blank but everything else valid
	@Test
	public void testAddClientLastNameBlankEveruthingElseValidNegative() throws SQLException, InvalidParameterException {
		
		/*
		 * ACT and ASSERT
		 */
		Client client = new Client("Jane", "", "05162000");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockClientService.addClient(client);
		});
	}
	
	// firstName and lastName blank but birthdate valid
	@Test
	public void testAddClientFirstNameLastNameBlankButBirthdateValidNegative() throws SQLException, InvalidParameterException {
		
		Client client = new Client("", "", "05162000");
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockClientService.addClient(client);
		});
	}
	
	// birthdate lessthan 8 characters but names valid
	@Test
	public void testAddClientBirthdateIsLessthanEightCharactersButNamesValidNegative() throws SQLException, InvalidParameterException {
			
		Client client = new Client("Jane", "Doe", "12345");
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockClientService.addClient(client);
		});
	}
	
	// birthdate morethan 8 characters but names valid
	@Test
	public void testAddClientBirthdateMorethanEightCharactersButNamesValidNegative() throws SQLException, InvalidParameterException {
			
		Client client = new Client("Jane", "Doe", "123456789");
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockClientService.addClient(client);
		});
	}
	
	// birthdate contain characters
	@Test
	public void testAddClientBirthdateContainCharactersButNamesValidNegative() throws SQLException {
		
		Client client = new Client("Jane", "Doe", "abcdefgh");
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockClientService.addClient(client);
		});
	}
}
