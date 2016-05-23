/**
 *
 */
package com.dbank.fee.calculator.common.exception;

/**
 * This exception is thrown when there is invalid data in transaction input file
 * (when the data in file is not as per the contract defined for API/ problem
 * statement).
 *
 * @author Deepak Kumar Sharma
 */
public class InvalidDataException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1000000L;

    public InvalidDataException(String message) {
        super(message);
    }

}
