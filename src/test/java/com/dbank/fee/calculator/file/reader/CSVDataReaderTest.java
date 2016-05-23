/**
 * 
 */
package com.dbank.fee.calculator.file.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.dbank.fee.calculator.common.Transaction;

/**
 * A test class for {@link CSVDataReader} .
 * 
 * @author Deepak Kumar Sharma
 * 
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

		List<Transaction> transactionList = csvDataReader
				.readFile("TransactionsSampleData.csv");
		assertNotNull("transactionList should be Not NULL", transactionList);
		assertEquals(
				"transactionList should have 20 transactions provided in the sample data file for test: ",
				20, transactionList.size());
		String trnsIdFixedPart = "DEEEXTXN";
		int rowNumber = 0;
		for (Transaction transaction : transactionList) {
			rowNumber++;
			String tranId = trnsIdFixedPart + rowNumber;
			assertEquals("TransactionId should be " + tranId, tranId,
					transaction.getExtTranId());
		}

	}
}
