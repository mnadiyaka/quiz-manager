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

    @Override
    public List<ParticipantDto> getAllParticipants() {
        log.info("Getting list of participants");
        return participantRepository.findAll().stream().map(participant -> ParticipantDto.fromParticipant(participant)).toList();
    }

    @Override
    public ParticipantDto findParticipantById(Long participantId) {
        log.info("Searching for participant with id {}", participantId);
        ParticipantDto participantDto = ParticipantDto.fromParticipant(participantRepository.findById(participantId).get());
        if (participantDto == null) {
            throw new EntityNotFoundException();
        }
        return participantDto;
    }

    @Override
    public Participant createParticipant(ParticipantDto participantDto, TeamDto teamDto) {
        Participant participant = ParticipantDto.toParticipant(participantDto);
        participant.setTeam(TeamDto.toTeam(teamDto));
        log.info("Creating new participant {}", participantDto);
        return participantRepository.save(participant);
    }

    @Override
    public void updateParticipant(ParticipantDto updatedParticipant, Long participantId) {
        ParticipantDto oldParticipant = ParticipantDto.fromParticipant(participantRepository.findById(participantId).get());
        if (oldParticipant == null) {
            throw new EntityNotFoundException();
        }
        log.info("Updating participant {}", oldParticipant);
        oldParticipant.setFirstname(updatedParticipant.getFirstname());
        oldParticipant.setLastname(updatedParticipant.getLastname());
        participantRepository.save(ParticipantDto.toParticipant(oldParticipant));
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
}