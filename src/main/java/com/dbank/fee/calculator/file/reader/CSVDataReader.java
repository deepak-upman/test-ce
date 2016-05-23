/**
 *
 */
package com.dbank.fee.calculator.file.reader;

import com.dbank.fee.calculator.common.Transaction;
import com.dbank.fee.calculator.common.exception.InvalidDataException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;

/**
 * CSV file reader, to read the CSV transaction file.
 *
 * @author Deepak Kumar Sharma
 */
public class CSVDataReader extends AbstractDataReader {

    @Override
    public List<Transaction> readFile(String fileName)
            throws IOException, InvalidDataException {
        List<Transaction> transactionList = new ArrayList<>();

        try (InputStream tranFileAsStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(tranFileAsStream))) {
            String input;
            int rowNumber = 0;
            while ((input = br.readLine()) != null && input.trim().length() > 0) {
                if (++rowNumber != 1) {
                    String[] transactionRow = input.split(",");
                    if (transactionRow.length != 7) {
                        throw new InvalidDataException(
                                "Invalid number of columns at row number: "
                                        + String.valueOf(rowNumber));
                    }
                    Transaction transaction = (new Transaction.Builder())
                            .extTranId(transactionRow[0]).clientId(
                                    transactionRow[1])
                            .securityId(transactionRow[2]).transactionType(
                                    transactionRow[3]).transactionDate(
                                    transactionRow[4]).marketValue(
                                    transactionRow[5]).priorityFlag(
                                    transactionRow[6]).build();
                    transactionList.add(transaction);
                } else {
                    System.out.println("Skipping the headers!!!");
                }
            }
        }
        return transactionList;
    }
}
