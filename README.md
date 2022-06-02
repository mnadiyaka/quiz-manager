# quiz-manager
Quizz manager. Requirements:

	1. Authorization. Login. Registration.
	2. Two roles: admin, captain.
	3. As user with role Admin, I want:
		3.1. Register/Login.
		3.2. Location CRUD + List.
		3.3. Quiz CRUD + List.
		3.4. View teams for quizz.
		3.5. Confirm team.
	4. As user with role Captain, I want:
		4.1. Register/Login.
		4.2. Team CRUD.
		4.3. Participant CRUD.
		4.4. Add participant to the team.
		4.5. Send team request for confirmation.

Quizz manager. Technology stack:
- SpringBoot / SpringRestAPI / SpringDataJPA(Hibernate) / SpringSecurity(Session based auth (JSessionId)).
- MySQL.
- Build with Maven.
- Unit tests (at least 75%). JUnit (at least 5) / Mockito. Collect test covarage.
