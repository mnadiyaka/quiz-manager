package com.sigma.service.impl;

import com.sigma.exception.QuizException;
import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.dto.TeamDto;
import com.sigma.model.entity.Participant;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.Team;
import com.sigma.repository.TeamRepository;
import com.sigma.service.ParticipantService;
import com.sigma.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    private final ParticipantService participantService;

    @Override
    public Team findTeamById(final Long teamId) {
        log.info("Searching for team with id {}", teamId);
        return teamRepository.findById(teamId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public Team createTeam(final TeamDto teamDto) {
        if (!Objects.isNull(teamRepository.findByTeamName(teamDto.getTeamName()))) {
            throw new EntityExistsException("Already exists");
        }
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        teamDto.setCaptainId(userId);
        log.info("Creating new team {}", teamDto);
        return teamRepository.save(TeamDto.toTeam(teamDto));
    }

    @Override
    @Transactional
    public Team updateTeam(final TeamDto updatedTeam, final Long teamId) {
        final Team oldTeam = check(teamId);
        log.info("Updating team {}", oldTeam);
        Optional.ofNullable(updatedTeam.getTeamName()).ifPresent(oldTeam::setTeamName);
        Optional.ofNullable(updatedTeam.getCaptainId()).ifPresent(oldTeam::setCaptainId);
        return teamRepository.save(oldTeam);
    }

    @Override
    @Transactional
    public void deleteTeam(final Long teamId) {

        log.info("deleting :" + teamId);
        teamRepository.delete(check(teamId));
    }

    @Override
    public List<TeamDto> getAllTeams() {
        log.info("Getting list of teams");
        return teamRepository.findAll().stream()
                .map(TeamDto::fromTeam).toList();
    }

    @Override
    public Team confirmTeam(final Team team, final boolean confirmation) { //TODO: change idea?
        team.setConfirmed(confirmation);
        return teamRepository.save(team);
    }

    private Team check(Long teamId) {
        Team team = (findTeamById(teamId));
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!team.getCaptainId().equals(userId)) {
            throw new AuthorizationServiceException("Wrong account");
        }
        return team;
    }

    @Override
    @Transactional
    public void updatePl(final ParticipantDto newParticipant, final Long participantId, final Long teamId) {
        final Team team = check(teamId);
        final List<Participant> people = team.getParticipants();
        final Participant old = participantService.findParticipantById(teamId, participantId);
        people.remove(old);

        final Participant upd = participantService.updateParticipant(newParticipant, participantId, teamId);

        people.add(upd);
        team.setParticipants(people);
        teamRepository.save(team);
    }

    @Override
    @Transactional
    public void addPl(final ParticipantDto newParticipant, final Long teamId) {
        final Team team = check(teamId);
        final Participant newPl = participantService.createParticipant(newParticipant, teamId);
        final List<Participant> people = team.getParticipants();
        people.add(newPl);
        team.setParticipants(people);
        teamRepository.save(team);
    }

    @Override
    @Transactional
    public Team applyForQuiz(final Quiz quiz, final Long teamId) {
        final Team team = findTeamById(teamId);
        if (team.getParticipants().size() > quiz.getParticipantInTeamNumberMax() || team.getParticipants().size() < quiz.getParticipantInTeamNumberMin()) {
            throw new QuizException("Wrong size of the team");
        }

        final List<Quiz> teamsQ = team.getQuizzes();
        teamsQ.add(quiz);
        team.setQuizzes(teamsQ);

        return teamRepository.save(team);
    }
}