This is an attempt to use spring boot to create a rest API framework which is secured using spring security. The current version uses json
webtoken to authenticate each request.

Steps to access the application:
1. Once jar is build using maven, it should be capable of deploying standalone without any database setup, as hashmap implementation is
   used in current version. Developers are free to use their own implementation of database with a switch of interface impl.
2. Once the app is up and running:
   2.a. Issue a get request on "http://localhost:8080/api/login". Use basic authentication with username as "bhaskar@gmail3.com"
        and password as "password3". Feel free to update "UserService" class to access user and credentials as per your database.
   2.b. A successful response from the above URL should provide a json token in "X-Authorization:". Keep this token handy for next step
3. To access rest resource, issue a get request on following "http://localhost:8080/api/messages". It is mandatory to use "X-Authorization"
   as a header key and the value is json token which is returned from previous step.
   
   
   
   TO DO:
   1. How to invalidate issued jsontoken?
   2. Provide any one database implementation
