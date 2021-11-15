package com.revature.clientServiceTest;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
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

public class UpdateClientTest {

	ClientDAO mockClientDao = mock(ClientDAO.class);
	ClientService mockClientService = new ClientService(mockClientDao);;
	
	/* ********************
	 * -- POSITIVE TESTS --
	 * ********************
	 */
	
	// change firstName and lastName
	@ Test
	public void testUpdateClientChangeFirstNameLastNamePositive() throws SQLException, InvalidParameterException {
		
		when(mockClientDao.getClientByClientId(eq(96))).thenReturn(new Client(96, "Jane", "Doe", "05162000"));
			
		Client actual = mockClientService.updateClient("96", "Jenny", "Dao");
		
		Client expected = new Client(96, "Jenny", "Dao", "05162000");
		Assertions.assertEquals(expected, actual);	
	}
	
	// change firstName only
	@Test
	public void testUpdateClientChangeFirstNamePositive() throws SQLException, InvalidParameterException {
		
		when(mockClientDao.getClientByClientId(eq(96))).thenReturn(new Client(96, "Jane", "Doe", "05162000"));
		
		Client actual = mockClientService.updateClient("96", "Jenny", null);
		
		Client expected = new Client(96, "Jenny", "Doe", "05162000");
		Assertions.assertEquals(expected, actual);
	}
	
	// change lastName only
	@Test
	public void testUpdateClientChangeLastNamePositive() throws SQLException, InvalidParameterException {
		
		when(mockClientDao.getClientByClientId(eq(96))).thenReturn(new Client(96, "Jane", "Doe", "05162000"));
		
		Client actual = mockClientService.updateClient("96", null, "Dao");
		
		Client expected = new Client(96, "Jane", "Dao", "05162000");
		Assertions.assertEquals(expected, actual);
	}
	
	/* ********************
	 * -- NEGATIVE TESTS --
	 * ********************
	 */
	
	// neither of firstName nor lastName boxes was checked
	@Test
	public void testUpdateClientFirstNameLastNameUncheckedNegative() throws SQLException, InvalidParameterException {
		
		/*
		 * ACT and ASSERT
		 */
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			mockClientService.updateClient("96", null, null);
		});
	}
	
	
}