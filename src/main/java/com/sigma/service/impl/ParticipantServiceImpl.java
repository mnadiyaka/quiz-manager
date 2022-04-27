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
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    public Participant findParticipantById(final Long teamId, final Long participantId) {
        log.info("Searching for participant with id {}", participantId);
        Participant participant = participantRepository.findParticipantByIdAndTeamId(participantId, teamId);
        if (Objects.equals(participant, null)) {
            throw new EntityNotFoundException("Participant doesn't exist");
        }
        return participant;
    }

    @Override
    @Transactional
    public Participant createParticipant(final ParticipantDto participantDto, final Long teamId) {
        final Participant newPlayer = ParticipantDto.toParticipant(participantDto);
        newPlayer.setTeamId(teamId);
        return participantRepository.save(newPlayer);
    }

    @Override
    @Transactional
    public Participant updateParticipant(final ParticipantDto newParticipant, final Long participantId, final Long teamId) {

        final Participant old = findParticipantById(teamId, participantId);
        Optional.ofNullable(newParticipant.getFirstname()).ifPresent(old::setFirstname);
        Optional.ofNullable(newParticipant.getLastname()).ifPresent(old::setLastname);
        return participantRepository.save(old);
    }

    @Override
    @Transactional
    public void deleteParticipant(final Long teamId, final Long participantId) {
        final Participant participant = findParticipantById(teamId, participantId);

        if (!participant.getTeamId().equals(teamId)) {
            throw new AuthorizationServiceException("Wrong user");
        }
        log.info("Deleting participant {}", participant);
        participantRepository.delete(participant);
    }

    @Override
    public List<ParticipantDto> getAllParticipants(final Long teamId) {
        log.info("Getting list of participants");
        return participantRepository.findParticipantsByTeamId(teamId).stream().map(ParticipantDto::fromParticipant).toList();
    }
}