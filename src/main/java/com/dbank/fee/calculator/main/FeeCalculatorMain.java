/**
 *
 */
package com.dbank.fee.calculator.main;

import com.dbank.fee.calculator.common.TransactionDataFileType;
import com.dbank.fee.calculator.file.processor.TransactionFileProcessor;

/**
 * @author Deepak Kumar Sharma
 */
public class FeeCalculatorMain {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String... args) throws Exception {
        if (args.length != 1) {
            System.err
                    .println("Please try running the program again with one command line argument for transaction data file name with extension (Assuming the file is available on classpath)!!! ");
            System.exit(-1);
        }
        String fileName = args[0];
        int fileExtIndex = fileName.lastIndexOf(".");
        if (fileExtIndex == -1) {
            System.err.println("File with no extension can't be read!!! ");
            System.exit(-1);
        }
        String fileExtension = fileName.substring(fileExtIndex + 1);
        TransactionDataFileType transactionDataFileType = TransactionDataFileType
                .fromString(fileExtension);
        if (transactionDataFileType == null) {
            System.err
                    .println("Not supported file extension. File can't be read!!! ");
            System.exit(-1);
        }
        System.out.println("fName: " + fileName + " and ext is: "
                + fileExtension + " enum: " + transactionDataFileType);
        TransactionFileProcessor transactionFileProcessor = new TransactionFileProcessor();
        transactionFileProcessor.processFile(transactionDataFileType, fileName, System.out);

    }


}
