/**
 *
 */
package com.dbank.fee.calculator.file.processor;

import com.dbank.fee.calculator.TransactionFeeCalculator;
import com.dbank.fee.calculator.common.Transaction;
import com.dbank.fee.calculator.common.TransactionDataFileType;
import com.dbank.fee.calculator.common.exception.InvalidDataException;
import com.dbank.fee.calculator.common.exception.InvalidDataFileTypeException;
import com.dbank.fee.calculator.file.reader.AbstractDataReader;
import com.dbank.fee.calculator.file.reader.DataReaderFactory;
import com.dbank.fee.calculator.summary.SummaryDTO;
import com.dbank.fee.calculator.summary.SummaryGenerator;
import com.dbank.fee.calculator.summary.SummaryWriter;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

/**
 * This will read the file with given name, assuming it to exist on classpath, and invoke the fee calculator,
 * to calculate the fee for transaction, and finally print the output on given PrintStream.
 * <p>
 * It will be invoked, by both FeeCalculatorMain, and index.jsp file, to execute the program, in both mode,
 * standalone application, and web application.
 *
 * @author Deepak Kumar Sharma
 */
public class TransactionFileProcessor {

    public void processFile(TransactionDataFileType transactionDataFileType, String fileName, PrintStream out)
            throws InvalidDataFileTypeException, IOException, InvalidDataException {
        AbstractDataReader datafileReader = DataReaderFactory.getInstance()
                .getReader(transactionDataFileType);
        List<Transaction> transactionList = datafileReader.readFile(fileName);
        out.println("************ START File read OUTPUT *********");
        out.println(transactionList);
        out.println("************ END File read OUTPUT *********");
        TransactionFeeCalculator.getInstance().calculateFee(transactionList);
        // For debugging
        out
                .println("************ START **After Fee calculation List status *********");
        out.println(transactionList);
        out
                .println("************ END **After Fee calculation List status *********");

        // For debugging
        out.println("************ START **Before Report *********");
        Map<SummaryDTO, Double> report = SummaryGenerator.getInstance().generateReport(transactionList);
        SummaryWriter.getInstance().writeReport(report, out);
        out.println("************ END **After Report *********");
    }

}
