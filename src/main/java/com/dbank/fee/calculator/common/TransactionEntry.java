package com.dbank.fee.calculator.common;

import java.util.Date;

/**
 * POJO that represent the key in HashMap that will have all transactions in file, grouped in buckets based on
 * clientId, securityId, and transactionDate;
 *
 * @author Deepak Kumar Sharma
 */
public class TransactionEntry {
    private String clientId;
    private String securityId;
    private Date transactionDate;

    public TransactionEntry(String clientId, String securityId, Date transactionDate) {
        this.clientId = clientId;
        this.securityId = securityId;
        this.transactionDate = transactionDate;
    }

    public String getClientId() {
        return clientId;
    }

    public String getSecurityId() {
        return securityId;
    }


    public Date getTransactionDate() {
        return transactionDate;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append("[").append(clientId)
                .append(",").append(securityId).append(",").append(
                        Transaction.dateFormat.format(transactionDate)).append("]").append("\n")
                .toString();
    }

    @Override
    public int hashCode() {
        int hash = (((this.getClientId().hashCode() ^ this.getSecurityId().hashCode())) ^ this.transactionDate.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object otherObj) {
        boolean result = false;
        if (otherObj instanceof TransactionEntry) {
            TransactionEntry other = (TransactionEntry) otherObj;
            if (this.getClientId().equals(other.getClientId())
                    && this.getSecurityId().equals(
                    other.getSecurityId())
                    && this.getTransactionDate().equals(
                    other.getTransactionDate())) {
                result = true;
            }
        }
        return result;
    }
}
