/**
 * 
 */
package com.dbank.fee.calculator.file.reader;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.dbank.fee.calculator.common.TransactionDataFileType;
import com.dbank.fee.calculator.common.exception.InvalidDataFileTypeException;

/**
 * A test class for factory class for {@link DataReaderFactory} .
 * 
 * @author Deepak Kumar Sharma
 * 
 */
public class DataReaderFactoryTest {

	DataReaderFactory dataReaderFactory;

	@Before
	public void setUp() {
		dataReaderFactory = DataReaderFactory.getInstance();
	}

	@Test(expected = InvalidDataFileTypeException.class)
	public void testGetReaderForNullTransactionDataFileType() throws Exception {
		dataReaderFactory.getReader(null);

	}

	@Test
	public void testGetReaderForTransactionDataFileTypeAsCSV() throws Exception {
		AbstractDataReader reader = dataReaderFactory
				.getReader(TransactionDataFileType.CSV);
		assertTrue(reader instanceof CSVDataReader);

	}
}
