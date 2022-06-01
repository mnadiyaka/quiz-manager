# Table of contents
* [Setup](#setting-up-project)
* [Domain](#quiz-manager)
* [Technologies](#technologies)

# setting-up project
To successfully run application on local computer you need certain programs installed and run:
    1. IntelliJ Idea or other JDK 
    2. MySQL
    3. Before running application do mvn clean install
    

# Quiz Manager
### Quiz manager. DoMain:
what-> functions
	1. ADMIN:
        a) Authorization. Login. Registration.
        b) Creating or choosing locations.
        c) Managing quizzes.
        f) Confirming teams for quizzes
        e) Setting up table of results, maneging it.
	2. CAPTAIN:
        a) Authorization. Login. Registration.
        b) Managing Team.
		c) Managing Participants of own team.
		d) Sending team request for confirmation.

# Technologies
- SpringBoot / SpringRestAPI / SpringDataJPA(Hibernate) / SpringSecurity(Session based auth (JSessionId)).
- MySQL.
- Maven.
- Unit tests (at least 75%). JUnit / Mockito. 
- Collect test covarage.
