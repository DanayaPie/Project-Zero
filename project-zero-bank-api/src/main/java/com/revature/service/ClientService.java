package com.revature.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.ClientDAO;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.InvalidParameterException;
import com.revature.model.Client;

public class ClientService {

	private Logger logger = LoggerFactory.getLogger(ClientService.class);

	private ClientDAO clientDao;

	// constructor
	public ClientService() {
		this.clientDao = new ClientDAO();
	}

	// constructor for mock ClientDAO object
	public ClientService(ClientDAO clientDao) {
		this.clientDao = clientDao;
	}

	/*- *********
	 * -- GET --
	 * *********
	 */

	// getAllClients
	public List<Client> getAllClients() throws SQLException {

		logger.info("ClientService.getAllClients() invoked.");

		List<Client> clients = this.clientDao.getAllClients();

		return clients;
	}

	// getClientByClientId
	public Client getClientByClientId(String clientId) throws SQLException {

		logger.info("getClientById() invoked");

		int id = Integer.parseInt(clientId);

		Client c = this.clientDao.getClientByClientId(id);

		return c;
	}

	/*- **********
	 * -- POST --
	 * **********
	 */

	// addClient
	public Client addClient(Client client) throws SQLException, InvalidParameterException {

		logger.info("addClient() invoked");

		/*-
		 *  Possibilities 
		 *  	-> Client contain firstName, lastName, birthdate
		 *  1. firstName and/or lastName are blank
		 *  2. birthdate not contain characteres or symbols
		 *  3. birthdate length is not 8 characters
		 */

		// 1.
		if (client.getFirstName().trim().equals("") || client.getLastName().trim().equals("")) {

			logger.info("firstName and/or lastName are blank");

			throw new InvalidParameterException("First name and/or last name cannot be blank.");
		}

		client.setFirstName(client.getFirstName().trim());
		client.setLastName(client.getLastName().trim());

		// 2.
		String birthdate = client.getBirthdate();

		try {

			logger.info("parse birthdate to int.");

			Integer.parseInt(birthdate);

		} catch (NumberFormatException e) {
			throw new InvalidParameterException(
					"Birthdate cannot contain any characters or symbols. It MUST contain 8 numbers only and in MMDDYYYY format.");
		}

		// 3.
		if (birthdate.length() != 8) {

			logger.info("birthdate is not 8 characters ");

			throw new InvalidParameterException(
					"Invalid birthdate. Birthdate MUST contain 8 numbers only and in MMDDYYYY format.");
		}

		Client insertedClient = this.clientDao.addClient(client);
		return insertedClient;
	}

	/*- *********
	 * -- PUT --
	 * *********
	 */

	// updateClient
	public Client updateClient(String clientId, String firstName, String lastName)
			throws SQLException, InvalidParameterException {

		logger.info("updateClient() invoked");

		int id = Integer.parseInt(clientId);

		logger.debug("ClientId {}", id);

		Client clientToEdit = this.clientDao.getClientByClientId(id);
		logger.debug("ClientId {}", id);

		/*-
		 *  Possibilities
		 *  1. change first name and last name
		 *  	1.1 names cannot be blank
		 *  2. change first name only
		 *  	2.1 first name cannot be blank
		 *  3. change last name only
		 *  	3.1 last name cannot be blank
		 *  4. neither boxes was checked
		 */

		// 1.
		if (firstName != null && lastName != null) {

			logger.info("firstName and lastName update invoked.");

			clientToEdit.setFirstName(firstName);
			clientToEdit.setLastName(lastName);

			if (firstName.equals("") || lastName.equals("")) {

				logger.info("firstName and/or lastName are blank");

				throw new InvalidParameterException("First name and/or last name cannot be blank.");
			}

			// 2.
		} else if (firstName != null) {

			logger.debug("firstName update invoked.");

			if (firstName.equals("")) {

				logger.info("firstName is blank");

				throw new InvalidParameterException("First name cannot be blank");
			}

			clientToEdit.setFirstName(firstName);

			// 3.
		} else if (lastName != null) {

			logger.info("lastName update invoked.");

			if (lastName.equals("")) {

				logger.info("Last Name is blank");

				throw new InvalidParameterException("Last name cannot be blank");
			}

			clientToEdit.setLastName(lastName);

			// 4.
		} else {

			logger.info("no firstName nor lastName boxes was checked.");

			throw new InvalidParameterException("Neither of the firstName nor lastName boxes was checked.");
		}

		clientDao.updateClient(clientToEdit);

		return clientToEdit;
	}

//	// updateClient -- with Context ctx
//	public Client updateClient(String clientId, Context ctx) 
//			throws SQLException, ClientNotFoundException, InvalidParameterException {
//		
//		logger.info("updateClient() invoked");
//		
//		int id = Integer.parseInt(clientId);
//
//		logger.debug("ClientId {}", id); 
//		
//		Client clientToEdit = this.clientDao.getClientByClientId(id);
//		logger.debug("ClientId {}", id);
//			
//		
//		/*-
//		 *  Possibilities
//		 *  1. change first name and last name
//		 *  2. change first name only
//		 *  3. change last name only
//		 */
//		
//		// 1.
//		if (ctx.queryParam("firstName") != null && ctx.queryParam("lastName") != null) {
//				
//			logger.info("firstName and lastName update invoked.");
//				
//			String firstName = ctx.queryParam("firstName");
//			String lastName = ctx.queryParam("lastName");
//				
//			clientToEdit.setFirstName(firstName);
//			clientToEdit.setLastName(lastName);
//			
//		// 2.
//		} else if (ctx.queryParam("firstName") != null) {
//				
//			logger.debug("firstName update invoked.");
//				
//			String firstName = ctx.queryParam("firstName");
//				
//			clientToEdit.setFirstName(firstName);
//		
//		// 3.
//		} else if (ctx.queryParam("lastName") != null) {
//				
//			logger.info("lastName update invoked.");
//				
//			String lastName = ctx.queryParam("lastName");
//				
//			clientToEdit.setLastName(lastName);
//		}
//
//		mockClientDao.updateClient(clientToEdit);
//
//		return clientToEdit;
//	}

//	// upDateClient
//	public Client updateClient(Context ctx) throws SQLException, ClientNotFoundException, InvalidParameterException {
//		
//		logger.info("updateClient() invoked");
//
//		Client clientToEdit = this.clientDao.getClientByClientId(clientId);
//
//		if (ctx.queryParam(clientId)))
//		mockClientDao.updateClient(clientToEdit);
//
//		return clientToEdit;
//	}

	/*- ************
	 * -- DELETE --
	 * ************
	 */

	// deleteClientByClientId
	public void deleteClientByClientId(String clientId)
			throws SQLException, ClientNotFoundException, InvalidParameterException {

		logger.info("deleteStudentById() invoked");

		int id = Integer.parseInt(clientId);

		this.clientDao.deleteClientByClientId(id);
	}

//	// deleteClientByClientId
//	public void deleteClientByClientId(String clientId) 
//			throws SQLException, ClientNotFoundException, InvalidParameterException {
//		
//		logger.info("deleteStudentById() invoked");
//		
//		try {
//			int id = Integer.parseInt(clientId);
//			
//			Client c = this.clientDao.getClientByClientId(id);
//			
//			if (c == null) {
//				throw new ClientNotFoundException("Unable to delete client with ID of " + clientId + ". Please check if the client exist in the table.");
//			}
//			
//			this.clientDao.deleteClientByClientId(id);
//			
//		} catch (NumberFormatException e) {
//			throw new InvalidParameterException("ID provided is not an int convertable value");
//		}
//	}

	/*-**********************
	 * -- VERIFY CLIENT ID --
	 * **********************
	 */

	public void verifyClientId(String clientId)
			throws SQLException, ClientNotFoundException, InvalidParameterException {

		logger.info("AccountService.verifyClientIdAccountId() invoked.");

		try {

			int clId = Integer.parseInt(clientId);

			Client client = this.clientDao.getClientByClientId(clId);

			/*-
			 * Possibilities
			 *   1. client does not exist
			 *   2. client exist
			 */

			// 1.
			if (client == null) {

				logger.info("client is null");

				throw new ClientNotFoundException("Client with ID of " + clientId + " was not found.");

				// 2.
			} else {

				logger.info("client is not null");

			}

		} catch (NumberFormatException e) {

			logger.info("Client ID provided is not an int convertable value.");

			throw new InvalidParameterException("Client ID provided is not an int convertable value.");
		}
	}
}
