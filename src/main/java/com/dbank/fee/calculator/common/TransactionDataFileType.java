/**
 *
 */
package com.dbank.fee.calculator.common;

/**
 * This enum represents the supported input file formats for transactions data
 * file. Currently we support CSV type only.
 *
 * @author Deepak Kumar Sharma
 */
public enum TransactionDataFileType {

    CSV;

    public static TransactionDataFileType fromString(String value) {
        TransactionDataFileType resultDataFileType = null;
        if (value != null) {
            for (TransactionDataFileType fileType : TransactionDataFileType
                    .values()) {
                if (value.trim().equalsIgnoreCase(fileType.name())) {
                    resultDataFileType = fileType;
                    break;
                }
            }
        }
        return resultDataFileType;
    }
}
