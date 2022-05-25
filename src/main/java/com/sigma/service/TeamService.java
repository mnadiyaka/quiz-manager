package com.sigma.service;

import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.dto.TeamDto;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.Team;

import java.util.List;

public interface TeamService {
    public List<TeamDto> getAllTeams();

    public Team findTeamById(Long teamId);

    public Team createTeam(TeamDto teamDto);

    public Team updateTeam(TeamDto updatedTeam, Long teamId);

    public void deleteTeam(Long teamId);

    public Team confirmTeam(Team team, boolean confirmation); //TODO: DTO????

    public void updatePl(final ParticipantDto newParticipant, final Long participantId, final Long teamId);

    public void addPl(final ParticipantDto newParticipant, final Long teamId);

    public Team applyForQuiz(final Quiz quiz, final Long teamId);
}