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

import com.revature.model.Account;
import com.revature.util.JDBCUtility;

public class AccountDAO {

	private Logger logger = LoggerFactory.getLogger(AccountDAO.class);

	/*- *********
	 * -- GET --
	 * *********
	 */

	// getAccountsByClientId 
	public List<Account> getAllAccountsByClientId(int clientId) throws SQLException {

		logger.info("AccountDAO.getAllAcocuntsByClientId() invoked.");

		List<Account> listOfAccounts = new ArrayList<>();

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT * FROM accounts " + "WHERE client_id = ? ";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, clientId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				int accountId = rs.getInt("account_id");
				int id = rs.getInt("client_id");
				String accountType = rs.getString("account_type");
				double amount = rs.getDouble("amount");

				Account a = new Account(accountId, id, accountType, amount);

				listOfAccounts.add(a);
			}
		}

		return listOfAccounts;
	}

	// getAccountsByClientId BTW VALUES
	public List<Account> getAllAccountsByClientId(int clientId, int greaterThan, int lessThan) throws SQLException {

		logger.info("AccountDAO.getAllAcocuntsByClientId() invoked.");

		List<Account> listOfAccounts = new ArrayList<>();

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT * FROM accounts " + "WHERE client_id = ? " + "AND amount > ? " + "AND amount < ?;";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, clientId);
			pstmt.setInt(2, greaterThan);
			pstmt.setInt(3, lessThan);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				int accountId = rs.getInt("account_id");
				int id = rs.getInt("client_id");
				String accountType = rs.getString("account_type");
				double amount = rs.getDouble("amount");

				Account a = new Account(accountId, id, accountType, amount);

				listOfAccounts.add(a);
			}
		}

		return listOfAccounts;
	}

	// getAccountByAccountId
	public Account getAccountByAccountId(int clientId, int accountId) throws SQLException {

		logger.info("AccountDAO.getAccountByAccountId() invoked.");

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT * FROM accounts " + " WHERE client_id = ?" + " AND account_id = ?;";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, clientId);
			pstmt.setInt(2, accountId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return new Account(rs.getInt("account_id"), rs.getInt("client_id"), rs.getString("account_type"),
						rs.getDouble("amount"));

			} else {
				return null;
			}
		}
	}

	/*- **********
	 * -- POST --
	 * **********
	 */

	// openNewAccount
	public Account openNewAccount(Account account, String clientId) throws SQLException {

		logger.info("AccountDAO.openNewAccount() invoked.");

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "INSERT INTO accounts (client_id, account_type, amount) " + " VALUES (?, ?, ?);";
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, account.getClientId());
			pstmt.setString(2, account.getAccountType());
			pstmt.setDouble(3, account.getAmount());

			int numberOfAccountAdded = pstmt.executeUpdate();

			if (numberOfAccountAdded != 1) {
				throw new SQLException("Open a new account was unsuccessful.");
			}

			ResultSet rs = pstmt.getGeneratedKeys();

			rs.next();
			int automaticallyGeneratedId = rs.getInt(1);

			logger.debug("automaticallyGeneratedId {}", automaticallyGeneratedId);

			account.setAccountId(automaticallyGeneratedId);

			return account;
		}
	}

	/*- *********
	 * -- PUT --
	 * *********
	 */

	// updateAmount
	public Account updateAmount(Account account) throws SQLException {

		logger.info("AccountDAO.updateAmount() invoked.");

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "UPDATE accounts" + " SET amount = ?" + " WHERE client_id = ?" + " AND account_id = ?;";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setDouble(1, account.getAmount());
			pstmt.setInt(2, account.getClientId());
			pstmt.setInt(3, account.getAccountId());

			int numberOfAccountUpdated = pstmt.executeUpdate();

			if (numberOfAccountUpdated != 1) {
				throw new SQLException("Failed to deposit/withdraw $" + account.getAmount()
						+ " to/from account with ID of " + account.getAccountId() + ".");
			}

			return account;
		}
	}

	/*- ************
	 * -- DELETE --
	 * ************ 
	 */

	// deleteAccountByAccountId
	public void deleteAccountByAccountId(int accountId) throws SQLException {

		logger.info("AccountDAO.deleteAccountByAccountIdAndClientId() invoked.");

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "DELETE FROM accounts " + " WHERE account_id = ?;";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, accountId);

			pstmt.executeUpdate();
		}
	}

}
