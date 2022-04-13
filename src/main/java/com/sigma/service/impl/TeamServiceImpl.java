package com.sigma.service.impl;

import com.sigma.model.dto.TeamDto;
import com.sigma.model.entity.Role;
import com.sigma.model.entity.Team;
import com.sigma.model.entity.User;
import com.sigma.repository.TeamRepository;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    private final UserService userService;

    @Override
    public TeamDto findTeamById(Long teamId) {
        log.info("Searching for team with id {}", teamId);
        return TeamDto.fromTeam(teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException()));
    }

    @Override
    @Transactional
    public Team createTeam(TeamDto teamDto, Long captainId) {
        User user = userService.findUserById(captainId);
        if (user.getRole().equals(Role.ADMIN)) {
            throw new AuthorizationServiceException("This user is admin, not captain");
        }

        if (teamRepository.findByTeamName(teamDto.getTeamName()) != null) {
            throw new EntityExistsException("Already exists");
        }

        teamDto.setCaptain(user);

        log.info("Creating new team {}", teamDto);

        teamDto.setParticipants(new ArrayList<>());
        return teamRepository.save(TeamDto.toTeam(teamDto));
    }

    @Override
    @Transactional
    public void updateTeam(TeamDto updatedTeam, Long userId, Long teamId) {
        TeamDto oldTeam = checkTeam(userId, teamId);
        log.info("Updating team {}", oldTeam);
        oldTeam.setTeamName(updatedTeam.getTeamName());
        if (updatedTeam.getParticipants() != null) {
            oldTeam.setParticipants(updatedTeam.getParticipants());
        }
        if (updatedTeam.getCaptain() != null) {
            oldTeam.setCaptain(updatedTeam.getCaptain());
        }
        teamRepository.save(TeamDto.toTeam(oldTeam));
    }

    @Override
    @Transactional
    public void deleteTeam(Long userId, Long teamId) {
        TeamDto team = checkTeam(userId, teamId);
        log.info("Deleting team {}", team);
        teamRepository.delete(TeamDto.toTeam(team));
    }

    @Override
    public Team teamConfirmation(Team team, boolean confirmation) { //TODO: change idea?
        team.setConfirmed(confirmation);
        return teamRepository.save(team);
    }

    @Override
    public List<TeamDto> getAllTeams(Long userId) {
        log.info("Getting list of teams");
        return teamRepository.findAll().stream().filter(team -> team.getCaptain().getId() == userId)
                .map(team -> TeamDto.fromTeam(team)).toList();
    }

    private TeamDto checkTeam(Long userId, Long teamId) {
        TeamDto team = findTeamById(teamId);
        if (team.getCaptain().getId() != userId) {
            throw new AuthorizationServiceException("Wrong account credentials");
        }
        return team;
    }
}