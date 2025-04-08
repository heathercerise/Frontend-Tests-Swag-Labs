# Swag Labs Frontend E2E Tests

These are some E2E tests for the Swag Labs website to test user interaction with key frontend features.


## Instructions to run

The main way to run these tests is with Maven test.
* Run "mvn test" in terminal while located in project directory.
* Run as "maven test" in Eclipse workspace from project directory.

Alternatively, you can run each test individually or within a class.

Another option is to run the UserFactory class to generate tests for all test classes except for LoginTests which need to be run separately. These tests automate the running of each test class with different login credentials for more comprehensive testing. Login credentials can be removed or added from the data provider located in UserFactory class.

### Additional options

The config.properties file may be edited (located in /src/test/java/config) to allow to change testing to target a different environment.