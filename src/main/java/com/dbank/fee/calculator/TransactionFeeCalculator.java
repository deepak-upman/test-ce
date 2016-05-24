/**
 *
 */
package com.dbank.fee.calculator;

import com.dbank.fee.calculator.common.Transaction;
import com.dbank.fee.calculator.common.TransactionType;

import java.util.List;

/**
 * Calculate the fee as per business logic, in the same input list.
 *
 * @author Deepak Kumar Sharma
 */
public class TransactionFeeCalculator {

    private static final TransactionFeeCalculator transactionFeeCalculator = new TransactionFeeCalculator();

    private TransactionFeeCalculator() {
    }

    public static TransactionFeeCalculator getInstance() {
        return transactionFeeCalculator;
    }

    public void calculateFee(List<Transaction> transactionList) {
        for (int transactionIndex = 0; transactionIndex < transactionList.size(); transactionIndex++) {

            Transaction transaction = transactionList.get(transactionIndex);
            if (!transaction.isCalculated()) {
                int intraDayTransactionIndex;
                if (TransactionType.isValidForIntraDay(transaction.getTransactionType()) && (intraDayTransactionIndex = isIntraDayTransaction(
                        transactionList, transactionIndex)) != -1) {
                    // This is intraDay transaction, and the returned index is
                    // that
                    // paired transaction
                    transaction.setCalculatedFee(10);
                    transactionList.get(intraDayTransactionIndex).setCalculatedFee(
                            10);
                    transactionList.get(intraDayTransactionIndex)
                            .setCalculated(true);
                } else if (transaction.isPriorityFlag()) {
                    // For non -intra day and high priority transaction , we
                    // calculate $500
                    transaction.setCalculatedFee(500);
                } else {
                    // For non -intra day and low priority transaction , we
                    // calculate $100
                    transaction.setCalculatedFee(100);
                }
                transaction.setCalculated(true);
            }
        }
    }


    private int isIntraDayTransaction(List<Transaction> transactionList,
                                      int indexFor) {
        int resultIndex = -1;
        Transaction transaction = transactionList.get(indexFor);
        for (int i = indexFor + 1; i < transactionList.size(); i++) {
            Transaction currentTransaction = transactionList.get(i);
            if (transaction.getClientId().equalsIgnoreCase(
                    currentTransaction.getClientId())
                    && transaction.getSecurityId().equalsIgnoreCase(
                    currentTransaction.getSecurityId())
                    && Transaction.dateFormat.format(
                    transaction.getTransactionDate()).equalsIgnoreCase(
                    Transaction.dateFormat.format(currentTransaction
                            .getTransactionDate()))
                    && (transaction.getTransactionType() == TransactionType.SELL
                    && currentTransaction.getTransactionType() == TransactionType.BUY || transaction
                    .getTransactionType() == TransactionType.BUY
                    && currentTransaction.getTransactionType() == TransactionType.SELL)) {
                resultIndex = i;
                break;
            }
        }

        return resultIndex;
    }
}
