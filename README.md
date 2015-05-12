# Setup the dev environment

For the development environment we will be using:
- Eclipse Luna (4.4.1) for JavaEE Developers: https://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/lunasr1
- XAMPP for Windows/Linux v1.8.3 (PHP 5.5.15): https://www.apachefriends.org/index.html

# Getting DB running

Start Apache and MySQL Modules from XAMPP
2. click "Admin" in MySQL row or type "http://localhost/phpmyadmin/" in your browser
3. go to "SQL" and copy/paste the create_ordermgmt.sql content into this window -> press "GO"
4. repeat for initializeEnums.sql (this inserts all the status data for ordering and reservation).
5. if you go to "Databases" now, you should see the "ordermgmt" database there with data for status and users.
Troubleshooting:
- Apache not starting
The ports are probably blocked - often skype is the main issue, just kill skype.


# How to start the project

It is as simple as this: just run Application.java

# Initial test-endpoint

To test, whether it works correctly, after you have started Application.java, just call this URL in your browser: http://localhost:8080/greeting

If you see something like {"id":1,"content":"Hello, name!"}, then it's working correctly :)
