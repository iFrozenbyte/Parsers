package parsers.csv2json;

import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;


public class Transaction {
    private String idTransaction;
    private long fiscalReceiptNumber;
    private BigInteger sum;
    private Date operationDate;

    @Override
    public String toString() {
        return idTransaction + "," + fiscalReceiptNumber + "," + sum + "," + operationDate;
    }

    public Transaction() {
    }

    public Transaction(String idTransaction, long fiscalReceiptNumber, BigInteger sum, Date operationDate) {
        this.idTransaction = idTransaction;
        this.fiscalReceiptNumber = fiscalReceiptNumber;
        this.sum = sum;
        this.operationDate = operationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;
        return fiscalReceiptNumber == that.fiscalReceiptNumber && Objects.equals(idTransaction, that.idTransaction) && Objects.equals(sum, that.sum) && Objects.equals(operationDate, that.operationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTransaction, fiscalReceiptNumber, sum, operationDate);
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public long getFiscalReceiptNumber() {
        return fiscalReceiptNumber;
    }

    public void setFiscalReceiptNumber(long fiscalReceiptNumber) {
        this.fiscalReceiptNumber = fiscalReceiptNumber;
    }

    public BigInteger getSum() {
        return sum;
    }

    public void setSum(BigInteger sum) {
        this.sum = sum;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }
}