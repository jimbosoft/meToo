package au.com.candidate;

import java.time.LocalDateTime;

public class MeTransaction
{
    String fromAcct;
    String transactionId;
    LocalDateTime createAt;
    int amount;
    String txType;
    String reverseTxId;

    public MeTransaction(String fromAcct, String txId, LocalDateTime createAt, int amount, String txType, String reverseTxId) {
        this.fromAcct = fromAcct;
        this.transactionId = txId;
        this.createAt = createAt;
        this.amount = amount;
        this.txType = txType;
        this.reverseTxId = reverseTxId;
    }

    public String toString()
    {
        return "created " + createAt + " amount " + amount + " type " + txType + " from " + fromAcct + " reversalTx " + reverseTxId;
    }
}
