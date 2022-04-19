package com.sigma.service;

import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.entity.Participant;

import java.util.Set;

public interface ParticipantService {

    Set<ParticipantDto> getAllParticipants(Long userId, Long teamId);

    Participant findParticipantById(Long participantId);

    Participant createParticipant(ParticipantDto participantDto, Long userId, Long teamId);

    Participant updateParticipant(ParticipantDto newParticipant, Long participantId, Long userId, Long teamId);

    void deleteParticipant(Long userId, Long teamId, Long participantId);
}