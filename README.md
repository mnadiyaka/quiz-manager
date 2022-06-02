## Table of contents
* [Setup](#setting-up-project)
* [Domain](#quiz-manager)
* [Infrastructure setup](#infrastructure-setup)
* [Project startup instruction](#project-startup-instruction)

## Setting-up project
To successfully run application on local computer you need certain programs installed and run:
### Infrastructure setup:
* JDK version 17 - https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
* Lombok - https://projectlombok.org/
* MySQL - https://www.mysql.com/downloads/
* Maven - https://maven.apache.org/install.html


### Project startup instruction:
* Before running application do:
    * mvn clean install
* Afterwords:
    * mvn spring-boot:run

## Quiz Manager
### DoMain:
A project for managing Quiz processes: from creating quizzes (choosing time, assigning locations etc) to applying for quiz as team (managing team members included)
Currently there included:

![This is an image](https://pasteboard.co/8imTdrh3PJPw.png)
- Authorization. Login. Registration.
- Managing quizzes (choosing location, confirming teams, setting results).
- Managing Team (managing participants, sending team request for confirmation).
