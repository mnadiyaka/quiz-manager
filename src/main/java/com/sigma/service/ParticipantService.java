package com.sigma.service;

import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.entity.Participant;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface ParticipantService {

    /**
     * Finds all Participants for selected Team
     *
     * @param teamId Team's id to select from
     * @return List of Participants
     */
    List<ParticipantDto> getAllParticipants(Long teamId);

    /**
     * Finds Participant by entered existing id
     *
     * @param teamId        Possible existing Team
     * @param participantId Possible existing Participant
     * @return Found Participant
     * @throws EntityNotFoundException If Participant's id does not exist for chosen team
     */
    Participant findParticipantById(final Long teamId, Long participantId);

    /**
     * Creates new Participant from entered Data
     *
     * @param participantDto Entered data
     * @param teamId         Team's id, whose Participant will be created
     * @return New Participant
     */
    Participant createParticipant(final ParticipantDto participantDto, final Long teamId);

    /**
     * Tries to update existing Participant, if it exists
     *
     * @param newParticipant Updated data
     * @param participantId  Participant's id
     * @param teamId         Team's id
     * @return Updated Participant
     * @throws EntityNotFoundException If Participant's id doesn't exist for chosen team
     */
    Participant updateParticipant(ParticipantDto newParticipant, Long participantId, Long teamId);

    /**
     * Deletes by Participant's id and Team's id
     *
     * @param teamId        Team's id
     * @param participantId Participant's id
     * @throws EntityNotFoundException If Participant's id doesn't exist for chosen team
     */
    void deleteParticipant(Long teamId, Long participantId);
}