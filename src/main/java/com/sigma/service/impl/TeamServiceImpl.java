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
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    private final UserService userService;

    @Override
    public TeamDto findTeamById(final Long teamId) {
        log.info("Searching for team with id {}", teamId);
        return TeamDto.fromTeam(teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException()));
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
        final TeamDto oldTeam = findTeamById(teamId);
        log.info("Updating team {}", oldTeam);

        Optional.ofNullable(updatedTeam.getTeamName()).ifPresent(oldTeam::setTeamName);
        Optional.ofNullable(updatedTeam.getParticipants()).ifPresent(oldTeam::setParticipants);
        Optional.ofNullable(updatedTeam.getCaptain()).ifPresent(oldTeam::setCaptain);

        teamRepository.save(TeamDto.toTeam(oldTeam));
    }

    @Override
    @Transactional
    public void deleteTeam(final Long userId, final Long teamId) {
        final TeamDto team = checkTeam(userId, teamId);

        log.info("Deleting team {}", team);
        teamRepository.delete(TeamDto.toTeam(team));
    }

    @Override
    public Team confirmTeam(final Team team, final boolean confirmation) { //TODO: change idea?
        team.setConfirmed(confirmation);
        return teamRepository.save(team);
    }

    @Override
    public List<TeamDto> getAllTeams(final Long userId) {
        log.info("Getting list of teams");
        return teamRepository.findAll().stream().filter(team -> Objects.equals(team.getCaptain().getId(), userId))
                .map(TeamDto::fromTeam).toList();
    }

    private TeamDto checkTeam(Long userId, Long teamId) {
        final TeamDto team = findTeamById(teamId);
        if (!Objects.equals(team.getCaptain().getId(), userId)) {
            throw new AuthorizationServiceException("Wrong account credentials");
        }
        return team;
    }
}