
# Me bank coding exercise
## Input data:

The input csv file is assumed to be called meInput.csv and live in the directory the program is run from.

It is assumed the content of the file is valid and in chronological order, however some rudimentary input validation is performed.

The createdAt is converted to a LocalTimeDate and the amount will be converted into an integer, being the amount in cents. If these conversions fail, the record is ignored.

## The main functionality

A input data is passed to the MeBank class which creates a transaction object and puts it into a map of accounts containing list of transactions. One transaction is recorded against the outgoing funds account and one is recorded for the incoming funds account. If the reverse transaction Id is set, it will be added to a separate map of accounts which contains a map of transaction ids that have been reversed.
### The retrieval
When an account number, start and end date-times are supplied, MeBank will retrieve the list of transactions and find those in that time-frame. Once the start of the time-frame is found, search will stop when the transactions are past the end of the time-frame, hence shortening the search. Worst case the entire list is traversed.

If a transaction is found, the map of reversals is checked and if the transaction id is "not" found in that map, the amount is added to a running total and transaction count is incremented.
### Where to get it:

Clone or Download source from: [https://github.com/jimbosoft/meToo](https://github.com/jimbosoft/meToo)

### Build:

mvn clean install

### Run:

java -jar target/meToo-1.0-SNAPSHOT.jar  

### Usage:

The program reads and writes to standard IO. You will see:

    accountId: 12345  
    from (20/10/2018 12:00:00): 20/10/2018 12:00:00  
    to (20/10/2018 12:00:00): 20/10/2018 12:00:00  
    Relative balance for the period is: 0  
    Number of transactions included is: 0  
      
    accountId:  

To end the program press Ctrl-C or equivalent.

