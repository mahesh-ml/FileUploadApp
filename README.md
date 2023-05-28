**Task**

The goal is to create an application that allows uploading and getting a csv file

Sample Request file is stored at src/main/resources folder.

The first line of the file are the headers!
Note: The field code is unique

The application allows interaction through a Rest endpoint that allows the user to:

●	upload the data
●	Fetch all data
●	Fetch by code
●	Delete all data

Data upload:
Store to a database. The in memory database table will contain the fields as described on the csv file


**OpenAPI EndPoint**

http://localhost:8080/swagger-ui/index.html

**Local Run -** 

 Checkout project from github and import to intelliJ IDEA or any favourite IDE of your choice.

 Software/Tools ->
  JDK 17, Spring boot 3, Maven and H2 database

 After importing and setting JDK to Java 17  run mvn clean install to build the project 

 run ./mvnw spring-boot:run , the application should run on port 8080

or alternatively run below command under target folder 
**java -jar FileUploadApp-0.0.1-SNAPSHOT.jar**


**Request-response** 


Use Postman or any HttpClient to make http request . Below are the endpoint description 


**POST /api/uploadContent**

curl --location --request POST 'http://localhost:8080/api/uploadContent' \
--header 'Content-Type: application/csv' \
--form 'file=@"/Users/admin2/Downloads/exercise.csv"'

response
{
"message": "Uploaded the file successfully: exercise.csv"
}

**GET /api/getContent**

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

**GET /api/getContent/{code}**

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


**DELETE http://localhost:8080/api/deleteContent**

response -  Source Data cleared successfully



