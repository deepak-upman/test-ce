package com.dbank.fee.calculator.common;

import com.dbank.fee.calculator.common.exception.InvalidDataException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * POJO that represent a transaction in input file.
 *
 * @author Deepak Kumar Sharma
 */
public class Transaction {
    public final static SimpleDateFormat dateFormat = new SimpleDateFormat(
            "mm/dd/yyyy");
    private String extTranId;
    private String clientId;
    private String securityId;
    private TransactionType transactionType;
    private Date transactionDate;
    private double marketValue;
    // TRUE says high priority and FALSE says low priority
    private boolean priorityFlag;
    private double calculatedFee;
    private boolean isCalculated;

    public String getExtTranId() {
        return extTranId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getSecurityId() {
        return securityId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public boolean isPriorityFlag() {
        return priorityFlag;
    }

    public void setCalculatedFee(double calculatedFee) {
        this.calculatedFee = calculatedFee;
    }

    public double getCalculatedFee() {
        return calculatedFee;
    }

    public void setCalculated(boolean isCalculated) {
        this.isCalculated = isCalculated;
    }

    public boolean isCalculated() {
        return isCalculated;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append("[").append(extTranId).append(",").append(clientId)
                .append(",").append(securityId).append(",").append(
                        transactionType).append(",").append(
                        dateFormat.format(transactionDate)).append(",").append(
                        marketValue).append(",").append(
                        priorityFlag ? "Y" : "N").append(",").append(
                        isCalculated).append(",").append(
                        String.valueOf(calculatedFee)).append("]").append("\n")
                .toString();
    }

    public static class Builder {
        /**
         * This build the Transaction object, objective to keep this extra stuff
         * is, that we can later add the validation logic in respective methods
         * which returns 'this' reference.
         */
        private String extTranId;
        private String clientId;
        private String securityId;
        private TransactionType transactionType;
        private Date transactionDate;
        private double marketValue;
        private boolean priorityFlag;

        public Builder extTranId(String extTranId) {
            this.extTranId = extTranId;
            return this;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder securityId(String securityId) {
            this.securityId = securityId;
            return this;
        }

        public Builder transactionType(String transactionType)
                throws InvalidDataException {
            TransactionType type = TransactionType.fromString(transactionType);
            if (type == null) {
                throw new InvalidDataException(
                        "invalid transactionType value :" + transactionType
                                + "in file.");
            }
            this.transactionType = type;
            return this;
        }

        public Builder transactionDate(String transactionDate)
                throws InvalidDataException {

            try {
                this.transactionDate = dateFormat.parse(transactionDate);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new InvalidDataException(
                        "invalid transaction date value :" + transactionDate
                                + "in file.");
            }

            return this;
        }

        public Builder marketValue(String marketValue)
                throws InvalidDataException {
            try {

                this.marketValue = Double.valueOf(marketValue);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                throw new InvalidDataException(
                        "invalid transaction marketValue value :" + marketValue
                                + "in file.");
            }
            return this;
        }

        public Builder priorityFlag(String priorityFlag)
                throws InvalidDataException {
            if (priorityFlag != null
                    && (priorityFlag.trim().equalsIgnoreCase("Y") || priorityFlag
                    .trim().equalsIgnoreCase("N"))) {
                this.priorityFlag = priorityFlag.trim().equalsIgnoreCase("Y") ? true
                        : false;
                return this;
            } else {
                throw new InvalidDataException("invalid priorityFlag value :"
                        + priorityFlag + "in file.");
            }
        }

        public Transaction build() {
            Transaction transaction = new Transaction();
            transaction.extTranId = this.extTranId;
            transaction.clientId = this.clientId;
            transaction.securityId = this.securityId;
            transaction.transactionType = this.transactionType;
            transaction.transactionDate = this.transactionDate;
            transaction.marketValue = this.marketValue;
            transaction.priorityFlag = this.priorityFlag;
            return transaction;
        }
    }
}
