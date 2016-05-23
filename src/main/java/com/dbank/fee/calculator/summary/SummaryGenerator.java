/**
 *
 */
package com.dbank.fee.calculator.summary;

import com.dbank.fee.calculator.common.Transaction;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This SummaryGenerator, will generate the summary report grouped by client Id, transaction type, Transaction date,
 * & priority flag. Report should further be sorted by the
 * Client Id, Transaction Type, Transaction Date and Priority.
 *
 * @author Deepak Kumar Sharma
 */
public class SummaryGenerator {

    private static final SummaryGenerator summaryGenerator = new SummaryGenerator();

    private SummaryGenerator() {
    }

    public static SummaryGenerator getInstance() {
        return summaryGenerator;
    }

    public Map<SummaryDTO, Double> generateReport(List<Transaction> transactionList) {
        Map<SummaryDTO, Double> summaryReport = new TreeMap<>();
        for (Transaction transaction : transactionList) {
            SummaryDTO summaryDTOForTransaction = getSummaryDTOForTransaction(transaction);
            Double processingFee = summaryReport.get(summaryDTOForTransaction);
            if (processingFee != null) {
                summaryReport.put(summaryDTOForTransaction, processingFee
                        + summaryDTOForTransaction.getProcessingFee());
            } else {
                summaryReport.put(summaryDTOForTransaction,
                        summaryDTOForTransaction.getProcessingFee());
            }
        }
        return summaryReport;
    }

    private SummaryDTO getSummaryDTOForTransaction(Transaction transaction) {

        SummaryDTO summaryDTO = new SummaryDTO();
        summaryDTO.setClientId(transaction.getClientId());
        summaryDTO.setPriorityFlag(transaction.isPriorityFlag());
        summaryDTO.setProcessingFee(transaction.getCalculatedFee());
        summaryDTO.setTransactionType(transaction.getTransactionType());
        summaryDTO.setTransactionDate(transaction.getTransactionDate());
        return summaryDTO;
    }

}
