package com.sigma.service;

import com.sigma.model.dto.TeamDto;
import com.sigma.model.entity.Team;

import java.util.List;

public interface TeamService {
    public List<TeamDto> getAllTeams();

    public TeamDto findTeamById(Long teamId);

    public Team createTeam(TeamDto teamDto, Long captainId);

    public void updateTeam(TeamDto updatedTeam, Long teamId);

    public void deleteTeam(Long teamId);

    public Team confirmTeam(Team team, boolean confirmation); //TODO: DTO????
}