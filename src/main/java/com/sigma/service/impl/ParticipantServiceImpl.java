package com.sigma.service.impl;

import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.entity.Participant;
import com.sigma.repository.ParticipantRepository;
import com.sigma.service.ParticipantService;
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
        if (participantDto != null) {
            return participantDto;
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Participant createParticipant(ParticipantDto participantDto) {
        log.info("Creating new participant {}", participantDto.toString());
        return participantRepository.save(ParticipantDto.toParticipant(participantDto));
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
        oldParticipant.setTeam(updatedParticipant.getTeam());
        participantRepository.save(ParticipantDto.toParticipant(oldParticipant));
    }

    @Override
    public void deleteParticipant(Long participantId) {
        if (!participantRepository.existsById(participantId)) {
            throw new EntityNotFoundException();
        }
        log.info("Deleting team {}", participantRepository.getById(participantId));
        participantRepository.deleteById(participantId);
    }
}