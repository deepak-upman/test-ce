/**
 *
 */
package com.dbank.fee.calculator.file.reader;

import com.dbank.fee.calculator.common.Transaction;
import com.dbank.fee.calculator.common.TransactionEntry;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * A test class for {@link CSVDataReader} .
 *
 * @author Deepak Kumar Sharma
 */
public class CSVDataReaderTest {

    CSVDataReader csvDataReader;

    @Before
    public void setUp() {
        csvDataReader = new CSVDataReader();
    }

    @Test(expected = NullPointerException.class)
    public void testGetReaderForNullTransactionDataFileType() throws Exception {

        csvDataReader.readFile("SomeUnknownFileName.CSV");
    }

    @Test
    public void testGetReaderForNullTransactionDataFileTypeAsCSV()
            throws Exception {

        Map<TransactionEntry, List<Transaction>> transactionsMap = csvDataReader
                .readFile("TransactionsSampleData.csv");
        assertNotNull("transactionList should be Not NULL", transactionsMap);
        assertTrue(
                "transactionsMap should have some transactions read from provided in the sample data file for test: ",
                transactionsMap.size() > 0);
    }
}
