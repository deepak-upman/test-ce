/**
 *
 */
package com.dbank.fee.calculator.file.reader;

import com.dbank.fee.calculator.common.TransactionDataFileType;
import com.dbank.fee.calculator.common.exception.InvalidDataFileTypeException;

/**
 * A factory class for AbstractDataReader based on input file type.
 *
 * @author Deepak Kumar Sharma
 */
public class DataReaderFactory {

    private static final DataReaderFactory dataReaderFactory = new DataReaderFactory();

    private DataReaderFactory() {
    }

    public static DataReaderFactory getInstance() {
        return dataReaderFactory;
    }

    public AbstractDataReader getReader(
            TransactionDataFileType transactionDataFileType)
            throws InvalidDataFileTypeException {

        AbstractDataReader dataReader = null;
        if (transactionDataFileType == null) {
            throw new InvalidDataFileTypeException(
                    "Error in getting reader for NULL transaction data file type.");
        }
        switch (transactionDataFileType) {
            case CSV: {
                dataReader = new CSVDataReader();
                break;
            }
            default: {
                System.err
                        .println("Non-supported transaction data file type. Don't know how to read these files :(");
                throw new InvalidDataFileTypeException(
                        "Unknown file type extension. No reader is available for this extension.");
            }
        }
        return dataReader;
    }
}
