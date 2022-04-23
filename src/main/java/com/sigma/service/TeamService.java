package com.sigma.service;

import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.dto.TeamDto;
import com.sigma.model.entity.Participant;
import com.sigma.model.entity.Team;
import com.sigma.model.entity.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface TeamService {
    public List<TeamDto> getAllTeams();

    public Team findTeamById(Long teamId);

    public Team createTeam(TeamDto teamDto);

    public void updateTeam(TeamDto updatedTeam, Long teamId);

    public void deleteTeam(Long teamId);

    public Team confirmTeam(Team team, boolean confirmation); //TODO: DTO????


    public Participant findParticipantById(Long teamId, Long participantId);

    public List<ParticipantDto> getAllParticipants(final Long teamId);

    public Participant createParticipant(final ParticipantDto participantDto, final Long teamId);

    public void updateParticipant(final ParticipantDto newParticipant, final Long participantId, final Long teamId);

    public void deleteParticipant(final Long teamId, final Long participantId);
}