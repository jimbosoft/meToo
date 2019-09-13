package au.com.candidate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

public class GetCsvInputFile {

    private final String inputFileName = "meInput.csv";

    public GetCsvInputFile(MeBank bank)
    {
        readInput(bank);
    }

    private void readInput(MeBank bank) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(inputFileName));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] entry = line.split(cvsSplitBy);

                String txNr = entry[0];
                String fromAcctNr = entry[1];
                String toAcctNr = entry[2];
                String createdAt = entry[3];
                String amountStr = entry[4];
                String type = entry[5];

                String reverseTxId = "";
                if (entry.length > 6) {
                    reverseTxId = entry[6];
                }

                int amount = 0;
                boolean success = true;
                LocalDateTime createTime = TickTock.convert(createdAt.trim());
                try {
                    // assume format $.cc
                    amountStr = amountStr.trim().replace(".", "");
                    amount = Integer.parseInt(amountStr);
                }catch (Exception ex)
                {
                    System.out.println("Amount conversion failed for: " + amountStr);
                    success  = false;
                }

                if (createTime != null && success)
                {
                    bank.addTransaction(fromAcctNr, toAcctNr, txNr, createTime, amount, type, reverseTxId);
//                    if (!reverseTxId.isEmpty())
//                    {
//                        bank.addTransaction(toAcctNr, fromAcctNr, txNr, createTime, amount * -1, type, reverseTxId);
//                        bank.addReversal(fromAcctNr, reverseTxId, amount);
//                    }
//                    else
//                    {
//                        bank.addTransaction(fromAcctNr, new MeTransaction(toAcctNr, txNr, createTime, amount * -1, type, reverseTxId));
//                        bank.addTransaction(toAcctNr, new MeTransaction(fromAcctNr, txNr, createTime, amount, type, reverseTxId));
//                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Reading input file failed with: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Closing file failed for: " + e.getMessage());
                }
            }
        }
    }
}
