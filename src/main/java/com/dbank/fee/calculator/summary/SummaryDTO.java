package com.dbank.fee.calculator.summary;

import java.util.Date;

import com.dbank.fee.calculator.common.Transaction;
import com.dbank.fee.calculator.common.TransactionType;

public class SummaryDTO implements Comparable<SummaryDTO> {

	private String clientId;
	private TransactionType transactionType;
	private Date transactionDate;
	// TRUE says high priority and FALSE says low priority
	private boolean priorityFlag;
	private double processingFee;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public boolean isPriorityFlag() {
		return priorityFlag;
	}

	public void setPriorityFlag(boolean priorityFlag) {
		this.priorityFlag = priorityFlag;
	}

	public double getProcessingFee() {
		return processingFee;
	}

	public void setProcessingFee(double processingFee) {
		this.processingFee = processingFee;
	}

	@Override
	public int compareTo(SummaryDTO other) {
		int result = this.getClientId().compareTo(other.getClientId());
		if (result == 0) {
			// If client ID same go based on TranType
			result = this.getTransactionType().compareTo(
					other.getTransactionType());
			if (result == 0) {
				// If TranType same then go based on TranDate
				result = this.getTransactionDate().compareTo(
						other.getTransactionDate());
				if (result == 0) {
					result = Boolean.valueOf(this.isPriorityFlag()).compareTo(
							Boolean.valueOf(other.isPriorityFlag()));
				}
			}
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append("[").append(clientId).append(",").append(
				transactionType).append(",").append(
				Transaction.dateFormat.format(transactionDate)).append(",")
				.append(String.valueOf(processingFee)).append("]").append("\n")
				.toString();
	}

	@Override
	public boolean equals(Object otherObj) {
		boolean result = false;
		if (otherObj instanceof SummaryDTO) {
			SummaryDTO other = (SummaryDTO) otherObj;
			if (this.getClientId().equals(other.getClientId())
					&& this.getTransactionType().equals(
							other.getTransactionType())
					&& this.getTransactionDate().equals(
							other.getTransactionDate())
					&& Boolean.valueOf(this.isPriorityFlag()).equals(
							Boolean.valueOf(other.isPriorityFlag()))

			) {
				result = true;
			}
		}
		return result;
	}
}
