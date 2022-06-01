## Table of contents
* [Setup](#setting-up-project)
* [Domain](#quiz-manager)

## Setting-up project
To successfully run application on local computer you need certain programs installed and run:
1. Infrastructure setup:    
   * JDK version 17 - https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
   * Lombok - https://projectlombok.org/
   
   #### Technologies:
     - SpringBoot / SpringRestAPI / SpringDataJPA(Hibernate) / SpringSecurity(Session based auth (JWT)).
     - MySQL - https://maven.apache.org/install.html
     - Maven  - https://www.mysql.com/downloads/
     - JUnit / Mockito.
   

2. Project startup instruction:
   * Before running application do:
     * mvn clean install
   * Afterwords:
     * mvn spring-boot:run

## Quiz Manager
### DoMain:
A project for managing Quiz processes: from creating quizzes (choosing time, assigning locations etc) to applying for quiz as team (managing team members included)
Currently there included:
- Authorization. Login. Registration.
- Managing quizzes (choosing location, confirming teams, setting results). 
- Managing Team (managing participants, sending team request for confirmation).
