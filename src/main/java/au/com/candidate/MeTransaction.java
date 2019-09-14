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

    /***
     *
     * @param fromAcct Identifier for all operations
     * @param txId important to match reversals
     * @param createAt must be valid LoacalDateTime
     * @param amount in cents $25.99 => 2599
     * @param txType stored but not used
     * @param reverseTxId stored but not used
     */
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
