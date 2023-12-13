## Useful Gradle commands

The project makes use of Gradle and uses the Gradle wrapper to help you out carrying some common tasks such as building
the project or running it.

### List all Gradle tasks

List all the tasks that Gradle can do, such as `build` and `test`.

```console
$ ./gradlew tasks
```

### Build the project

Compiles the project, runs the test and then creates an executable JAR file

```console
$ ./gradlew build
```

### Run the tests

There are two types of tests, the unit tests and the functional tests. These can be executed as follows.

- Run unit tests only

  ```console
  $ ./gradlew test
  ```

### Run the application

Run the application which will be listening on port `8080`.

```console
$ ./gradlew bootRun
```

## API

Below is a list of API endpoints with their respective input and output. Please note that the application needs to be
running for the following endpoints to work. For more information about how to run the application, please refer
to [run the application](#run-the-application) section above.

### Store Transactions

Endpoint

```text
POST /products/storeProduct
```

To read the input file "Input.txt" and process the data into Product Transactions

```console
$ curl "http://localhost:8080/products/storeProduct"
```


### Get Stored Product Transactions

Endpoint

```text
GET /products/product
```

Retrieving Product Transactions processed from the input file using CURL

```console
$ curl "http://localhost:8080//products/product
```

Example output

```json
[
  {
    "recordCode": "315",
    "clientType": "CL  ",
    "clientNumber": "4321",
    "accountNumber": "0003",
    "subAccountNumber": "0001",
    "oppositePartyCode": "FCC   ",
    "productGroupCode": "FU",
    "exchangeCode": "CME ",
    "symbol": "N1    ",
    "expirationDate": "20100910",
    "currencyCode": "JPY",
    "movementCode": "01",
    "buySellCode": "S",
    "quantityLongSign": " ",
    "quantityLong": "0000000000",
    "quantityShortSign": " ",
    "quantityShort": "0000000006",
    "exchangeBrokerFee": "000000000000",
    "exchangeBrokerFeeDC": "D",
    "exchangeBrokerFeeCurrencyCode": "USD",
    "clearingFee": "000000000030",
    "clearingFeeDC": "D",
    "clearingFeeCurrencyCode": "USD",
    "commissionFee": "000000000000",
    "commissionFeeDC": "D",
    "commissionFeeCurrencyCode": "JPY",
    "transactionDate": "20100819",
    "futureReference": "059484",
    "ticketNumber": "      ",
    "externalNumber": "000317",
    "transactionPrice": "00009255.0000000",
    "traderInitials": "      ",
    "openCloseCode": "       ",
    "oppositeTraderID": "O"
  }
]
```

### View Product Transactions by Client Number

Endpoint

```text
GET /products/product/{clientId}>
```

Filtering Product Transactions by Client Number

```console
$ curl "http://localhost:8080/products/product/1234"
```

Example output

```json
[
  {
    "recordCode": "315",
    "clientType": "CL  ",
    "clientNumber": "1234",
    "accountNumber": "0003",
    "subAccountNumber": "0001",
    "oppositePartyCode": "FCC   ",
    "productGroupCode": "FU",
    "exchangeCode": "CME ",
    "symbol": "N1    ",
    "expirationDate": "20100910",
    "currencyCode": "JPY",
    "movementCode": "01",
    "buySellCode": "S",
    "quantityLongSign": " ",
    "quantityLong": "0000000000",
    "quantityShortSign": " ",
    "quantityShort": "0000000006",
    "exchangeBrokerFee": "000000000000",
    "exchangeBrokerFeeDC": "D",
    "exchangeBrokerFeeCurrencyCode": "USD",
    "clearingFee": "000000000030",
    "clearingFeeDC": "D",
    "clearingFeeCurrencyCode": "USD",
    "commissionFee": "000000000000",
    "commissionFeeDC": "D",
    "commissionFeeCurrencyCode": "JPY",
    "transactionDate": "20100819",
    "futureReference": "059484",
    "ticketNumber": "      ",
    "externalNumber": "000317",
    "transactionPrice": "00009255.0000000",
    "traderInitials": "      ",
    "openCloseCode": "       ",
    "oppositeTraderID": "O"
  }
]
```

### Export the Product Transactions to CSV and download

Endpoint

```text
GET /products/product/{clientId}/csv>
```

```text
GET /products/product/csv>
```

Retrieving readings using CURL

```console
$ curl "http://localhost:8080/products/product/1234/csv"
```

```console
$ curl "http://localhost:8080/products/product/csv"
```

Please refer the Output.csv file in the project root directory for reference.
