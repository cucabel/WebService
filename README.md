# WebService

Pre-request script
- postman.setEnvironmentVariable('isoTime',(new Date()).toISOString());

Body
- {
    "amount" : "1000.00",
    "timestamp" : "{{isoTime}}"
}

RESTful API for statistics. The main use case for the API is to calculate real-time statistics for the last 60 seconds of transactions.
API's endpoints:
- POST /transactions - called every time a transaction is made.
- GET /statistics - returns the statistic based on the transactions of the last 60 seconds.
- DELETE /transactions - deletes transactions.
