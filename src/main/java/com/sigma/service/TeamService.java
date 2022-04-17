package com.sigma.service;

import com.sigma.model.dto.TeamDto;
import com.sigma.model.entity.Team;

import java.util.List;

public interface TeamService {

    public List<TeamDto> getAllTeams(Long userId);

    public TeamDto findTeamById(Long teamId);

    public Team createTeam(TeamDto teamDto, Long captainId);

    public void updateTeam(TeamDto updatedTeam, Long userId, Long teamId);

    public void deleteTeam(Long userId, Long teamId);

    public Team confirmTeam(Long teamId, boolean confirmation); //TODO: DTO????

    public Team applyForQuiz(final Long quizId, final Long teamId);
}