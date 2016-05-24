/**
 *
 */
package com.dbank.fee.calculator.file.reader;

import com.dbank.fee.calculator.common.Transaction;
import com.dbank.fee.calculator.common.TransactionEntry;
import com.dbank.fee.calculator.common.exception.InvalidDataException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Deepak Kumar Sharma
 */
public abstract class AbstractDataReader {

    public abstract Map<TransactionEntry, List<Transaction>> readFile(String absoluteFilePath)
            throws IOException, InvalidDataException;

}
