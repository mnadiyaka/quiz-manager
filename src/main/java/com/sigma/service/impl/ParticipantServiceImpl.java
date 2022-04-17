package com.sigma.service.impl;

import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.dto.TeamDto;
import com.sigma.model.entity.Participant;
import com.sigma.model.entity.Team;
import com.sigma.repository.ParticipantRepository;
import com.sigma.service.ParticipantService;
import com.sigma.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;

    private final TeamService teamService; //TODO: remove service

    @Override
    public Participant findParticipantById(final Long participantId) {
        log.info("Searching for participant with id {}", participantId);
        return participantRepository.findById(participantId).orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    @Transactional
    public Participant createParticipant(final ParticipantDto participantDto, final Long userId, final Long teamId) {
        Team team = checkTeam(userId, teamId);

        List<Participant> people = team.getParticipants();
        Participant newPlayer = ParticipantDto.toParticipant(participantDto);
        newPlayer.setTeam(team);
        participantRepository.save(newPlayer);
        people.add(newPlayer);
        team.setParticipants(people);
        teamService.updateTeam(TeamDto.fromTeam(team), userId, team.getId());
        return newPlayer;
    }

    @Override
    @Transactional
    public Participant updateParticipant(final ParticipantDto newParticipant, final Long participantId, final Long userId, final Long teamId) {
        final Team team = checkTeam(userId, teamId);

        List<Participant> people = team.getParticipants();
        final Participant old = findParticipantById(participantId);
        people.remove(old);
        Optional.ofNullable(newParticipant.getFirstname()).ifPresent(old::setFirstname);
        Optional.ofNullable(newParticipant.getLastname()).ifPresent(old::setLastname);

        old.setTeam(team);

        people.add(old);
        team.setParticipants(people);
        teamService.updateTeam(TeamDto.fromTeam(team), userId, team.getId());

        return participantRepository.save(old);
    }

    @Override
    @Transactional
    public void deleteParticipant(final Long userId, final Long teamId, final Long participantId) {
        final Participant participant = findParticipantById(participantId);
        Team team = checkTeam(userId, participant.getTeam().getId());

        log.info("Deleting participant {}", participant);
        participantRepository.deleteById(participantId);
    }

    @Override
    public List<ParticipantDto> getAllParticipants(final Long userId, final Long teamId) {
        final Team team = checkTeam(userId, teamId);
        log.info("Getting list of participants");
        return participantRepository.findAll().stream().filter(p -> Objects.equals(p.getTeam().getId(), teamId)).map(ParticipantDto::fromParticipant).toList();
    }

    private Team checkTeam(final Long userId, final Long teamId) {
        final Team team = teamService.findTeamById(teamId);
        if (!Objects.equals(team.getCaptain().getId(), userId)) {
            throw new AuthorizationServiceException("Wrong account credentials");
        }
        return team;
    }
}