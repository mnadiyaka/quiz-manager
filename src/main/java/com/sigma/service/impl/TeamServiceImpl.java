package com.sigma.service.impl;

import com.sigma.exception.QuizStateException;
import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.dto.QuizDto;
import com.sigma.model.dto.TeamDto;
import com.sigma.model.entity.Participant;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.QuizResults;
import com.sigma.model.entity.Role;
import com.sigma.model.entity.State;
import com.sigma.model.entity.Team;
import com.sigma.model.entity.User;
import com.sigma.repository.TeamRepository;
import com.sigma.service.QuizResultService;
import com.sigma.service.QuizService;
import com.sigma.service.TeamService;
import com.sigma.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    private final UserService userService;

    private final QuizService quizService;

    private final QuizResultService quizResultService;

    @Override
    public Team findTeamById(final Long teamId) {
        log.info("Searching for team with id {}", teamId);
        return teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    @Transactional
    public Team createTeam(final TeamDto teamDto, final Long captainId) {
        final User user = userService.findUserById(captainId);
        if (user.getRole().equals(Role.ADMIN)) {
            throw new AuthorizationServiceException("This user is admin, not captain");
        }

        if (!Objects.equals(teamRepository.findByTeamName(teamDto.getTeamName()), null)) {
            throw new EntityExistsException("Already exists");
        }

        teamDto.setCaptain(user);

        log.info("Creating new team {}", teamDto);

        teamDto.setParticipants(new ArrayList<>());
        return teamRepository.save(TeamDto.toTeam(teamDto));
    }

    @Override
    @Transactional
    public void updateTeam(final TeamDto updatedTeam, final Long userId, final Long teamId) {
        final Team oldTeam = checkTeam(userId, teamId);
        log.info("Updating team {}", oldTeam);

        Optional.ofNullable(updatedTeam.getTeamName()).ifPresent(oldTeam::setTeamName);
        final List<Participant> participants = updatedTeam.getParticipants().stream().map(ParticipantDto::toParticipant).collect(Collectors.toList());
        Optional.ofNullable(participants).ifPresent(oldTeam::setParticipants);
        Optional.ofNullable(updatedTeam.getCaptain()).ifPresent(oldTeam::setCaptain);

        teamRepository.save(oldTeam);
    }

    @Override
    @Transactional
    public void deleteTeam(final Long userId, final Long teamId) {
        final Team team = checkTeam(userId, teamId);

        log.info("Deleting team {}", team);
        teamRepository.delete(team);
    }

    @Override
    public List<TeamDto> getAllTeams(final Long userId) {
        log.info("Getting list of teams");
        return teamRepository.findAll().stream().filter(team -> Objects.equals(team.getCaptain().getId(), userId))
                .map(TeamDto::fromTeam).toList();
    }

    private Team checkTeam(Long userId, Long teamId) {
        final Team team = findTeamById(teamId);
        if (!Objects.equals(team.getCaptain().getId(), userId)) {
            throw new AuthorizationServiceException("Wrong account credentials");
        }
        return team;
    }

    @Override
    @Transactional
    public Team confirmTeam(final Long teamId, final boolean confirmation) { //TODO: change idea?
        final Team team = findTeamById(teamId);
        team.setConfirmed(confirmation);
        return teamRepository.save(team);
    }

    @Override
    @Transactional
    public Team applyForQuiz(final Long quizId, final Long teamId) {
        final Quiz quiz = quizService.findQuizById(quizId);
        if (!quiz.getState().equals(State.ANOUNCED)) {
            throw new QuizStateException("Quiz closed, try another one");
        }
        final Team team = findTeamById(teamId);
        final List<Quiz> teamsQ = team.getQuizzes();
        teamsQ.add(quiz);
        team.setQuizzes(teamsQ);

        final QuizResults quizResults = new QuizResults();//TODO: Change idea or place?
        quizResults.setQuiz(quiz);
        quizResults.setTeam(team);
        quizResultService.createRes(quizResults);

        return teamRepository.save(team);
    }
}