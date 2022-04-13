package com.sigma.service.impl;

import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.dto.TeamDto;
import com.sigma.model.entity.Participant;
import com.sigma.repository.ParticipantRepository;
import com.sigma.service.ParticipantService;
import com.sigma.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;

    private final TeamService teamService;

    @Override
    public ParticipantDto findParticipantById(Long participantId) {
        log.info("Searching for participant with id {}", participantId);
        return ParticipantDto.fromParticipant(participantRepository.findById(participantId).orElseThrow(() -> new EntityNotFoundException()));
    }

    @Override
    public Participant createParticipant(ParticipantDto participantDto, Long userId, Long teamId) {
        TeamDto team = teamService.findTeamById(teamId);
        if (team == null) {
            throw new EntityNotFoundException("Team doesnt exist");
        }
        if (team.getCaptain().getId() != userId) {
            throw new EntityNotFoundException("Wrong account credentials");
        }
        List<ParticipantDto> people = team.getParticipants();
        Participant newPlayer = ParticipantDto.toParticipant(participantDto);
        newPlayer.setTeam(TeamDto.toTeam(team));
        participantRepository.save(newPlayer);
        people.add(ParticipantDto.fromParticipant(newPlayer));
        team.setParticipants(people);
        teamService.updateTeam(team, userId, team.getId());
        return newPlayer;
    }

    @Override
    public void updateParticipant(ParticipantDto newParticipant, Long participantId, Long userId, Long teamId) {
        TeamDto team = teamService.findTeamById(teamId);
        if (team == null) {
            throw new EntityNotFoundException("Team doesnt exist");
        }
        if (team.getCaptain().getId() != userId) {
            throw new EntityNotFoundException("Wrong account credentials");
        }
        List<ParticipantDto> people = team.getParticipants();
        Participant old = ParticipantDto.toParticipant(findParticipantById(participantId));
        people.remove(old);
        old.setTeam(TeamDto.toTeam(team));
        if (newParticipant.getFirstname() != null) {
            old.setFirstname(newParticipant.getFirstname());
        }
        if (newParticipant.getLastname() != null) {
            old.setLastname(newParticipant.getLastname());
        }
        participantRepository.save(old);
        people.add(newParticipant);
        team.setParticipants(people);
        teamService.updateTeam(team, userId, team.getId());
    }

    @Override
    public void deleteParticipant(Long userId, Long teamId, Long participantId) {
        Participant participant = participantRepository.getById(participantId);
        TeamDto team = TeamDto.fromTeam(participant.getTeam());
        if (team == null) {
            throw new EntityNotFoundException("Team doesnt exist");
        }
        if (team.getCaptain().getId() != userId) {
            throw new EntityNotFoundException("Wrong account credentials");
        }
        if (!participantRepository.existsById(participantId)) {
            throw new EntityNotFoundException();
        }
        log.info("Deleting participant {}", participantRepository.getById(participantId));
        participantRepository.deleteById(participantId);
    }

    @Override
    public List<ParticipantDto> getAllParticipants(Long userId, Long teamId) {
        TeamDto team = teamService.findTeamById(teamId);
        if (team == null) {
            throw new EntityNotFoundException("Team doesnt exist");
        }
        if (team.getCaptain().getId() != userId) {
            throw new EntityNotFoundException("Wrong account credentials");
        }
        log.info("Getting list of participants");
        return participantRepository.findAll().stream().filter(p -> p.getTeam().getId() == teamId).map(participant -> ParticipantDto.fromParticipant(participant)).toList();
    }
}