package com.sigma.service;

import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.entity.Participant;

import java.util.List;

public interface ParticipantService {

    public List<ParticipantDto> getAllParticipants();

    public ParticipantDto findParticipantById(Long participantId);

    public Participant createParticipant(ParticipantDto participantDto);

    public void updateParticipant(ParticipantDto updatedParticipant, Long participantId);

    public void deleteParticipant(Long participantId);
}