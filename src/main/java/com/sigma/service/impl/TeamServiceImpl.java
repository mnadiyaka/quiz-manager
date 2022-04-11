package com.sigma.service.impl;

import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.dto.TeamDto;
import com.sigma.model.entity.Role;
import com.sigma.model.entity.Team;
import com.sigma.model.entity.User;
import com.sigma.repository.TeamRepository;
import com.sigma.service.ParticipantService;
import com.sigma.service.TeamService;
import com.sigma.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    private final UserService userService;

    private final ParticipantService participantService;

    @Override
    public List<TeamDto> getAllTeams() {
        log.info("Getting list of teams");
        return teamRepository.findAll().stream().map(team -> TeamDto.fromTeam(team)).toList();
    }

    @Override
    public TeamDto findTeamById(Long teamId) {
        log.info("Searching for team with id {}", teamId);
        TeamDto teamDto = TeamDto.fromTeam(teamRepository.findById(teamId).get());
        if (teamDto == null) {
            throw new EntityNotFoundException();
        }
        return teamDto;
    }

    @Override
    @Transactional
    public Team createTeam(TeamDto teamDto, Long captainId) {
        User user = userService.findUserById(captainId);
        if (user == null) {
            throw new EntityNotFoundException("User with id = " + captainId + " not found");
        }

        if (user.getRole().equals(Role.ADMIN)) {
            throw new EntityNotFoundException("This user is admin, not captain");
        }

        if (teamRepository.findByTeamName(teamDto.getTeamName())) {
            throw new EntityExistsException("Already exists");
        }

        log.info("Creating new team {}", teamDto.toString());

        teamDto.setCaptain(userService.findUserById(captainId));
        return teamRepository.save(TeamDto.toTeam(teamDto));
    }

    @Override
    @Transactional
    public void updateTeam(TeamDto updatedTeam, Long teamId) {
        TeamDto oldTeam = TeamDto.fromTeam(teamRepository.findById(teamId).get());
        if (oldTeam == null) {
            throw new EntityNotFoundException();
        }
        log.info("Updating team {}", oldTeam);
        oldTeam.setTeamName(updatedTeam.getTeamName());
        oldTeam.setParticipants(updatedTeam.getParticipants());
        oldTeam.setCaptain(updatedTeam.getCaptain());
        teamRepository.save(TeamDto.toTeam(oldTeam));
    }

    @Override
    @Transactional
    public void deleteTeam(Long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new EntityNotFoundException();
        }
        log.info("Deleting team {}", teamRepository.getById(teamId));
        teamRepository.deleteById(teamId);
    }

    @Override
    public TeamDto addParticipant(ParticipantDto participantDto, Long userId, Long teamId) {
        TeamDto team = TeamDto.fromTeam(teamRepository.getById(teamId));
        if (team == null) {
            throw new EntityNotFoundException("Team doesnt exist");
        }
        if (team.getCaptain().getId() != userId) {
            throw new EntityNotFoundException("Wrong account credentials");
        }
        List<ParticipantDto> people = team.getParticipants();
        ParticipantDto newPlayer = ParticipantDto.fromParticipant(participantService.createParticipant(participantDto, team));
        people.add(newPlayer);
        team.setParticipants(people);
        updateTeam(team, team.getId());
        return team;
    }

    @Override
    public Team teamConfirmation(Team team, boolean confirmation) { //TODO: change idea?
        team.setConfirmed(confirmation);
        return teamRepository.save(team);
    }
}