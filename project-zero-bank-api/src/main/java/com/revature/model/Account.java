package com.revature.model;

import java.math.BigDecimal;

public class Account {
	
	private int accountId;
	private int clientId;
	private String accountType;
	private BigDecimal amount;
	private float interest;
	
	public Account() {
		
	}
	
	public Account(int accountId, int clientId, String accouontType, BigDecimal amount, float interest) {
		this.accountId = accountId;
		this.clientId = clientId;
		this.accountType = accountType;
		this.amount = amount;
		this.interest = interest;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public float getInterest() {
		return interest;
	}

	public void setInterest(float interest) {
		this.interest = interest;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountId;
		result = prime * result + ((accountType == null) ? 0 : accountType.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + clientId;
		result = prime * result + Float.floatToIntBits(interest);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountId != other.accountId)
			return false;
		if (accountType == null) {
			if (other.accountType != null)
				return false;
		} else if (!accountType.equals(other.accountType))
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (clientId != other.clientId)
			return false;
		if (Float.floatToIntBits(interest) != Float.floatToIntBits(other.interest))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", clientId=" + clientId + ", accountType=" + accountType + ", amount=" + amount
				+ ", interest=" + interest + "]";
	}
	
}
