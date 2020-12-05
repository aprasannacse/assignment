Project:PIMS(Person Information Management System)
Technology/Tools Used:Java8,Spring Boot,REST API,Spring Security,H2 Database,Junit,Jacoco CodeCoverage,LOMBOK,STS IDE,POSTMAN and GITHUB

Steps to Run:
1)Checkout the pims source from GITHUB
2)Import the source into STS IDE
3)Update Maven project(Right-click on your project(s) --> Maven --> Update Project) to download the all dependencies jar into Maven local repository(.m2).
4)If you are using LOMBOK first time in your STS IDE,then need to follow the simple installation steps given in next section.
3)Clean & Build the project.
4)Run the Application startup file PIMSApplicationStartUp.java as SpringBootApp.
5)Hit the shared Endpoints using POSTMAN tool with the provided Login credentials.
6)To generate Junit and Code Coverage Report,Right Click pom.xml > Run As > Maven Build

LOMBOK Installation Steps:
1)After Maven project update,LOMBOK jar would be automatically downloaded into .m2 folder.
2)Navigate to local maven .m2 folder (..\.m2\repository\org\projectlombok\lombok\1.18.16)and double click on lombok-x.xx.xx.jar 
3)Specify the path of your STS ide .exe file and click install.
4)Restart your ide.
Reference Link- https://howtodoinjava.com/automation/lombok-eclipse-installation-examples/

Key Directories:
pims/data/- It contains the h2 database file(localDatabase.mv.db) which contains the sample data used for Junit Testcases execution.
pims/logs/- It contains the Application logs files (debug and error logs).
pims/reports/- It contains the Junit Test case and Code Coverage reports.

Note:
1)To minimize the Test Data Setup effort for Junit Test cases(TC),sample conditional datas are stored in h2 DB file.This local DB file would be used for the application and Junit TC executions.However,the test data for following TC needs to be updated for each executions.
testAddPerson(),testDeletePet() and testLinkPetToPerson().
2)Requirement-C2:Delete Pet Data functionality is implemented and now it is enabled.It can be easily disabled by uncommenting the Annotation @PreAuthorize("denyAll") used in the PetController class.

Login Credentials:
ADMIN ROlE:Username/Password-admin/admin
USER ROLE:Username/Password-user/user