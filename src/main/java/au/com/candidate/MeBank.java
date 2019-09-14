package au.com.candidate;

import java.time.LocalDateTime;
import java.util.*;

public class MeBank
{
    public class FoundResult
    {
        int amount = 0;
        int nrOfTransactions = 0;

        public FoundResult(int amount, int nrOfTransactions) {
            this.amount = amount;
            this.nrOfTransactions = nrOfTransactions;
        }
    }
    Map<String, ArrayList<MeTransaction>> tranactions = new HashMap<String, ArrayList<MeTransaction>>();
    Map<String, HashMap<String, Integer>> reversals = new HashMap<String, HashMap<String, Integer>>();

    public void addTransaction(String fromAcctNr, String toAcctNr, String txId, LocalDateTime createTime, int amount, String txType, String reverseTxId)
    {
        if (!reverseTxId.isEmpty())
        {
            addReversal(fromAcctNr, reverseTxId, amount);
            addReversal(toAcctNr, reverseTxId, amount * -1);
        }
        else
        {
            addTx(fromAcctNr, new MeTransaction(toAcctNr, txId, createTime, amount * -1, txType, reverseTxId));
            addTx(toAcctNr, new MeTransaction(fromAcctNr, txId, createTime, amount, txType, reverseTxId));
        }
    }

    private void addTx(String accountNr, MeTransaction tx)
    {
        ArrayList<MeTransaction> txs;
        if (tranactions.containsKey(accountNr))
        {
            txs = tranactions.get(accountNr);
        }
        else
        {
            txs = new ArrayList<MeTransaction>();
            tranactions.put(accountNr,txs);
        }
        txs.add(tx);
    }

    private void addReversal(String accountNr, String txId, int amount)
    {
        HashMap<String, Integer> reversalMap;
        if (reversals.containsKey(accountNr))
        {
            reversalMap = reversals.get(accountNr);
        }
        else
        {
            reversalMap = new HashMap<String, Integer>();
            reversals.put(accountNr,reversalMap);
        }
        reversalMap.put(txId, amount);
     }

    public FoundResult getRelBalance(String acctNr, LocalDateTime from, LocalDateTime to)
    {
        if (from.compareTo(to) > 0)
        {
            System.out.print("Error: Start time must be before End time, no result returned");
            return new FoundResult(0, 0);
        }
        int total = 0;
        int cnt = 0;
        ArrayList<MeTransaction> accountTx = tranactions.get(acctNr);

        if (accountTx == null)
        {
            return new FoundResult(total, cnt);
        }
        HashMap<String, Integer> excludes = reversals.get(acctNr);

        boolean startFound = false;
        for(MeTransaction txs : accountTx)
        {
            if (TickTock.inRange(from, to, txs.createAt))
            {
                startFound = true;
                if (excludes == null || !excludes.containsKey(txs.transactionId))
                {
                    total += txs.amount;
                    cnt++;
                }
            }
            else if (startFound) // must have reached the end of the range
            {
                break;
            }
        }
        return new FoundResult(total, cnt);
    }
    /**
     * Sort the transaction by date-time
     * makes the finding of relevant transaction a lot simpler
     */
    public void readCompleteSort()
    {
        Iterator it = tranactions.values().iterator();
        while (it.hasNext()) {
            ArrayList<MeTransaction> txs = (ArrayList<MeTransaction>) it.next();
            Collections.sort(txs, new Comparator<MeTransaction>(){
                public int compare(MeTransaction s1, MeTransaction s2) {
                    return s1.createAt.compareTo(s2.createAt);
                }
            });
        }
    }
    public String toString()
    {
        String ret = "Transactions: " + tranactions;
        ret += "Reversal: " + reversals;
        return ret;
    }
}
