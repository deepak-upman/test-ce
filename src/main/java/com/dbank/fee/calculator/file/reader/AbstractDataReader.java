/**
 *
 */
package com.dbank.fee.calculator.file.reader;

import com.dbank.fee.calculator.common.Transaction;
import com.dbank.fee.calculator.common.exception.InvalidDataException;

import java.io.IOException;
import java.util.List;

/**
 * @author Deepak Kumar Sharma
 */
public abstract class AbstractDataReader {

    public abstract List<Transaction> readFile(String absoluteFilePath)
            throws IOException, InvalidDataException;

}
