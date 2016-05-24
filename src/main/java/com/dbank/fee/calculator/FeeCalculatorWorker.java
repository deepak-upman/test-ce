/**
 *
 */
package com.dbank.fee.calculator;

import com.dbank.fee.calculator.common.Transaction;
import com.dbank.fee.calculator.common.TransactionEntry;
import com.dbank.fee.calculator.common.TransactionType;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * Calculate the fee as per business logic, in the same input list.
 *
 * @author Deepak Kumar Sharma
 */
public class FeeCalculatorWorker implements Callable<String> {
    Queue<TransactionEntry> transEntryQueue;
    Map<TransactionEntry, List<Transaction>> transactionsMap;
    CountDownLatch countDownLatch;

    public FeeCalculatorWorker(Queue<TransactionEntry> transEntryQueue, Map<TransactionEntry, List<Transaction>> transactionsMap, CountDownLatch countDownLatch) {
        this.transEntryQueue = transEntryQueue;
        this.transactionsMap = transactionsMap;
        this.countDownLatch = countDownLatch;
    }

    private void calculateFee(List<Transaction> transactionList) {

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

    @Override
    public String call() throws Exception {
        String result = "Fee Calculator worker thread: " + Thread.currentThread().getName() + " is DONE !";
        TransactionEntry transactionEntry;
        try {
            while ((transactionEntry = transEntryQueue.poll()) != null) {
                System.out.println("Fee Calculator worker thread: " + Thread.currentThread().getName() + " is running !");
                calculateFee(transactionsMap.get(transactionEntry));
            }
        } finally {
            countDownLatch.countDown();
        }
        return result;
    }
}
