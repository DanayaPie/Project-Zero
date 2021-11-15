package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.model.Client;
import com.revature.util.JDBCUtility;

public class ClientDAO {

	private Logger logger = LoggerFactory.getLogger(ClientDAO.class);
	
	/* *********
	 * -- GET --
	 * *********
	 */
	// getAllClients
	public List<Client> getAllClients() throws SQLException {
		
		logger.info("ClientDAO.getAllClients() invoked.");

		List<Client> listOfClients = new ArrayList<>();

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT * FROM clients;";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				int clientId = rs.getInt("client_id");
				String firstName = rs.getString("client_first_name");
				String lastName = rs.getString("client_last_name");
				String birthdate = rs.getString("client_birthdate");

				Client c = new Client(clientId, firstName, lastName, birthdate);

				listOfClients.add(c);
			}
		}

		return listOfClients;
	}

	// getClientByClientId
	public Client getClientByClientId(int clientId) throws SQLException {
		
		logger.info("ClientDAO.getClientByClientId() invoked.");

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT * FROM clients"
					+ " WHERE client_id = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, clientId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return new Client(rs.getInt("client_id"), rs.getString("client_first_name"),
						rs.getString("client_last_name"), rs.getString("client_birthdate"));
			} else {
				return null;
			}
		}
	}

	
	/* **********
	 * -- POST --
	 * **********
	 */
	
	// addClient
	public Client addClient(Client client) throws SQLException {
		
		logger.info("ClientDAO.addClient() invoked.");

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "INSERT INTO clients(client_first_name, client_last_name, client_birthdate)"
					+ "VALUES(?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, client.getFirstName());
			pstmt.setString(2, client.getLastName());
			pstmt.setString(3, client.getBirthdate());

			int numberOfrecordsInserted = pstmt.executeUpdate();

			if (numberOfrecordsInserted != 1) {
				throw new SQLException("Adding a new client was unsuccessful");
			}

			ResultSet rs = pstmt.getGeneratedKeys();

			rs.next();
			Integer automaticallyGeneratedId = rs.getInt(1);
			client.setClientId(automaticallyGeneratedId);

			return client;
		}
	}

	
	/* *********
	 * -- PUT --
	 * *********
	 */
	
	// updateClient
	public Client updateClient(Client client) throws SQLException {
		
		logger.info("ClientDAO.updateClient() invoked.");

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "UPDATE clients SET "
					+ " client_first_name = ?,"
					+ " client_last_name = ? "
					+ " WHERE client_id = ?;";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, client.getFirstName());
			pstmt.setString(2, client.getLastName());
			pstmt.setInt(3, client.getClientId());

			int numberOfRecordsUpdated = pstmt.executeUpdate();

			if (numberOfRecordsUpdated != 1) {
				throw new SQLException("Update client with ID of " + client.getClientId() + " failed.");
			}

			return client;
		}
	}

	
	/* ************
	 * -- DELETE --
	 * ************
	 */
	// deleteClientByClientId
	public void deleteClientByClientId(int clientId) throws SQLException {
		
		logger.info("ClientDAO.deleteClientByClientId() invoked.");

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "DELETE FROM clients " + " WHERE client_id = ?;";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, clientId);

			int numberOfRecordsUpdate = pstmt.executeUpdate();

			if (numberOfRecordsUpdate != 1) {
				throw new SQLException("Unable to delete client with ID of " + clientId
						+ ". Please check if the client exist in the table.");
			}
		}
	}
}
