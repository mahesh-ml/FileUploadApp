The goal is to create an application that allows uploading and getting a csv file.
The sample csv file can be found here:
https://drive.google.com/open?id=1ZAVoOrYWMwrXve8D7ySuLCTs7WIQH7xK

The first line of the file are the headers!
Note: The field code is unique

The application allows interaction through a Rest endpoint that allows the user to:

●	upload the data
●	Fetch all data
●	Fetch by code
●	Delete all data

Data upload:
Store to a database. The in memory database table will contain the fields as described on the csv file

Following api have been implemented
POST /api/uploadContent

curl --location --request POST 'http://localhost:8080/api/uploadContent' \
--header 'Content-Type: application/csv' \
--form 'file=@"/Users/admin2/Downloads/exercise.csv"'
response 
{
"message": "Uploaded the file successfully: exercise.csv"
}

GET /api/getContent
Get all uploaded contents
curl --location --request GET 'http://localhost:8080/api/getContent'
response
[
{
"id": 1,
"source": "ZIB",
"codeListCode": "ZIB001",
"code": "271636001",
"displayValue": "Polsslag regelmatig",
"longDescription": "The long description is necessary",
"fromDate": "2019-01-01",
"toDate": null,
"sortingPriority": "1"
},
{
"id": 2,
"source": "ZIB",
"codeListCode": "ZIB001",
"code": "61086009",
"displayValue": "Polsslag onregelmatig",
"longDescription": "",
"fromDate": "2019-01-01",
"toDate": null,
"sortingPriority": "2"
},
{
"id": 3,
"source": "ZIB",
"codeListCode": "ZIB002",
"code": "Type 5",
"displayValue": "Zachte keutels met duidelijke randen",
"longDescription": "",
"fromDate": "2019-01-01",
"toDate": null,
"sortingPriority": ""
},
{
"id": 4,
"source": "ZIB",
"codeListCode": "ZIB002",
"code": "Type 6",
"displayValue": "Zachte stukjes met gehavende randen",
"longDescription": "",
"fromDate": "2019-01-01",
"toDate": null,
"sortingPriority": ""
}....]

GET /api/getContent/{code}
curl --location --request GET 'http://localhost:8080/api/getContent/271636001'
response 
{
"id": 1,
"source": "ZIB",
"codeListCode": "ZIB001",
"code": "271636001",
"displayValue": "Polsslag regelmatig",
"longDescription": "The long description is necessary",
"fromDate": "2019-01-01",
"toDate": null,
"sortingPriority": "1"
}


DELETE http://localhost:8080/api/deleteContent
response Source Data cleared successfully



