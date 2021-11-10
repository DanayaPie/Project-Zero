package com.revature.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.revature.dao.ClientDAO;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.InvalidParameterException;
import com.revature.model.Client;

public class ClientService {

	private ClientDAO clientDao;

	public ClientService() {
		this.clientDao = new ClientDAO();
	}

	// getAllClients
	public List<Client> getAllClients() throws SQLException {

		List<Client> clients = this.clientDao.getAllClients();

		return clients;
	}

	// getClientById
	public Client getClientById(String clientId)
			throws SQLException, ClientNotFoundException, InvalidParameterException {

		try {
			int id = Integer.parseInt(clientId);

			Client c = this.clientDao.getClientById(id);

			if (c == null) {
				throw new ClientNotFoundException(
						"Client with ID of " + clientId + " was not found. Please re-enter client ID.");
			}

			return c;

		} catch (NumberFormatException e) {
			throw new InvalidParameterException(
					"ID provided is not an int convertable value. Please re-enter client ID");
		}
	}

	// addClient
	public Client addClient(Client client) throws SQLException, InvalidParameterException {

		// first and last name possibility
		if (client.getFirstName().trim().equals("") || client.getLastName().trim().equals("")) {
			throw new InvalidParameterException("First name and/or last anme cannot be blank");
		}

		// trim whitespaces in names
		client.setFirstName(client.getFirstName().trim());
		client.setLastName(client.getLastName().trim());

//		// birthdate from JSON object
//		Object json;
//		String birthdate = ((Object) json).getString("birthdate");
//		DateFormat df = new SimpleDateFormat("yyy-MM-dd");

		Client insertedClient = this.clientDao.addClient(client);
		return insertedClient;
	}

	public Client updateClient(int clientId, String changedFirstName, String changedLastName,
			String changedPhoneNumber, String changedEmail)
			throws SQLException, ClientNotFoundException, InvalidParameterException {

			Client clientToEdit = this.clientDao.getClientById(clientId);

			if (clientToEdit == null) {
				throw new ClientNotFoundException("Client with an ID of " + clientId + " was not found.");
			}

			if (changedFirstName != null) {
				clientToEdit.setFirstName(changedFirstName);
			}

			if (changedLastName != null) {
				clientToEdit.setLastName(changedLastName);
			}

			if (changedPhoneNumber != null) {
				clientToEdit.setPhoneNumber(changedPhoneNumber);
			}

			if (changedEmail != null) {
				clientToEdit.setEmail(changedEmail);
			}

			clientDao.updateClient(clientToEdit);

			return clientToEdit;
	}
}
