# Table of contents
* [Setup](#setting-up-project)
* [Domain](#quiz-manager)
* [Technologies](#technologies)

# Setting-up project
To successfully run application on local computer you need certain programs installed and run:
    1. IntelliJ Idea or other JDK 
    2. MySQL
    3. Before running application do mvn clean install
    

# Quiz Manager
### DoMain:
	- ADMIN:
        * Authorization. Login. Registration.
        * Creating or choosing locations.
        * Managing quizzes.
        * Confirming teams for quizzes
        * Setting up table of results, maneging it.
	- CAPTAIN:
        * Authorization. Login. Registration.
        * Managing Team.
		* Managing Participants of own team.
		* Sending team request for confirmation.

# Technologies
- SpringBoot / SpringRestAPI / SpringDataJPA(Hibernate) / SpringSecurity(Session based auth (JSessionId)).
- MySQL.
- Maven.
- Unit tests (at least 75%). JUnit / Mockito. 
- Collect test covarage.
