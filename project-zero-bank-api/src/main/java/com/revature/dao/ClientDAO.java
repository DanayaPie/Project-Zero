package com.revature.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Client;
import com.revature.util.JDBCUtility;

public class ClientDAO {

	// getAllClients
	public List<Client> getAllClients() throws SQLException {

		List<Client> listOfClients = new ArrayList<>();

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT * FROM clients;";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				int clientId = rs.getInt("client_id");
				String firstName = rs.getString("client_first_name");
				String lastName = rs.getString("client_last_name");
				Date birthdate = rs.getDate("client_birthdate");
				String phoneNumber = rs.getString("client_phone_number");
				String email = rs.getString("client_email");

				Client c = new Client(clientId, firstName, lastName, birthdate, phoneNumber, email);

				listOfClients.add(c);
			}
		}

		return listOfClients;
	}

	// getClientById
	public Client getClientById(int clientId) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT * FROM clients WHERE client_id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, clientId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return new Client(rs.getInt("client_id"), rs.getString("client_first_name"),
						rs.getString("client_last_name"), rs.getDate("client_birthdate"),
						rs.getString("client_phone_number"), rs.getString("client_email"));
			} else {
				return null;
			}
		}
	}

	// addClient
	public Client addClient(Client client) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "INSERT INTO clients(client_first_name, client_last_name, client_birthdate, client_phone_number, client_email)"
					+ "VALUES(?, ?, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, client.getFirstName());
			pstmt.setString(2, client.getLastName());
			pstmt.setString(3, client.getBirthdate());
			pstmt.setString(4, client.getPhoneNumber());
			pstmt.setString(5, client.getEmail());

			int numberOfrecordsInserted = pstmt.executeUpdate();

			if (numberOfrecordsInserted != 1) {
				throw new SQLException("Adding a new client was unsuccessful");
			}

			ResultSet rs = pstmt.getGeneratedKeys();

			rs.next();
			int automaticallyGeneratedId = rs.getInt(1);
			client.setClientId(automaticallyGeneratedId);
			System.out.println(client.getBirthdate());
			return client;
		}
	}

	// updateClient
	public Client updateClient(Client client) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "UPDATE clients "
					+ "SET client_first_name = ?, client_last_name = ?, client_phone_number = ?, client_email = ? "
					+ " WHERE client_id = ?;";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, client.getFirstName());
			pstmt.setString(2, client.getLastName());
			pstmt.setString(3, client.getPhoneNumber());
			pstmt.setString(4, client.getEmail());
			pstmt.setInt(5, client.getClientId());

			int numberOfRecordsUpdated = pstmt.executeUpdate();

			if (numberOfRecordsUpdated != 1) {
				throw new SQLException("Update client with ID of " + client.getClientId() + " failed!");
			}

			return client;
		}
	}
}
