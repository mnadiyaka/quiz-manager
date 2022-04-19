package com.sigma.service;

import com.sigma.model.dto.TeamDto;
import com.sigma.model.entity.Team;

import java.util.Set;

public interface TeamService {

    Set<TeamDto> getAllTeams(Long userId);

    Team findTeamById(Long teamId);

    Team createTeam(TeamDto teamDto, Long captainId);

    void updateTeam(TeamDto updatedTeam, Long userId, Long teamId);

    void deleteTeam(Long userId, Long teamId);

    Team confirmTeam(Long teamId, boolean confirmation); //TODO: DTO????

    Team applyForQuiz(final Long quizId, final Long teamId);
}