/**
 *
 */
package com.dbank.fee.calculator;

import com.dbank.fee.calculator.common.Transaction;
import com.dbank.fee.calculator.common.TransactionEntry;

import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.*;

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

    public void calculateFee(Map<TransactionEntry, List<Transaction>> transactionsMap, PrintStream out) {
        // Number of worker threads to be executing, to calculate the fee.
        int nWorkerThreads = 8;
        ExecutorService pool = Executors.newFixedThreadPool(nWorkerThreads);
        Set<Future<String>> futures = new HashSet<>();
        try {
            CountDownLatch countDownLatch = new CountDownLatch(nWorkerThreads);
            Queue<TransactionEntry> transactionEntryQueue = new ConcurrentLinkedQueue<>(transactionsMap.keySet());
            for (int i = 0; i < nWorkerThreads; i++) {
                Callable<String> task = new FeeCalculatorWorker(transactionEntryQueue, transactionsMap, countDownLatch);
                Future<String> future = pool.submit(task);
                futures.add(future);
            }
            countDownLatch.await();
            // Just to ensure that we have result in future object. Result is returned after we decrement the countDownLatch
            Thread.sleep(100);
        } catch (InterruptedException e) {
            out.println("Exception occurred, while waiting for worker threads to finish! " + e.getMessage());
        } finally {
            pool.shutdown();
        }
        out.println("Printing Future results!");
        for (Future<String> future : futures) {
            try {
                String result = future.get();
                if (result != null) {
                    out.println(result);
                } else {
                    out.println("Future should not be NULL.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
