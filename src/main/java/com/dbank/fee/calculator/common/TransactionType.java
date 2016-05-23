/**
 *
 */
package com.dbank.fee.calculator.common;

/**
 * This enum represent the all possible transaction types supported in the
 * system (or we can expect those to appear in input file name).
 *
 * @author Deepak Kumar Sharma
 */
public enum TransactionType {
    BUY, SELL, DEPOSIT, WITHDRAW;

    public static TransactionType fromString(String value) {
        TransactionType resultTransactionType = null;
        if (value != null) {
            for (TransactionType fileType : TransactionType.values()) {
                if (value.trim().equalsIgnoreCase(fileType.name())) {
                    resultTransactionType = fileType;
                    break;
                }
            }
        }
        return resultTransactionType;
    }
}
