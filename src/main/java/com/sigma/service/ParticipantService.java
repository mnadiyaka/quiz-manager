package com.sigma.service;

import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.entity.Participant;

import java.util.List;

public interface ParticipantService {

    public List<ParticipantDto> getAllParticipants(Long teamId);

    public Participant findParticipantById(final Long teamId, Long participantId);

    public Participant createParticipant(final ParticipantDto participantDto, final Long teamId);

    public Participant updateParticipant(ParticipantDto newParticipant, Long participantId, Long teamId);

    public void deleteParticipant(Long teamId, Long participantId);
}