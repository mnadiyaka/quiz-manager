package com.sigma.service;

import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.entity.Participant;
import com.sigma.model.entity.Team;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface ParticipantService {

    /**
     * Finds all {@link Participant}s for selected {@link Team}
     *
     * @param teamId Team's id to select from
     * @return List of Participants
     */
    List<ParticipantDto> getAllParticipants(Long teamId);

    /**
     * Finds {@link Participant} by entered existing id
     *
     * @param teamId        Possible existing {@link Team}
     * @param participantId Possible existing Participant
     * @return Found Participant
     * @throws EntityNotFoundException If Participant's id does not exist for chosen team
     */
    Participant findParticipantById(final Long teamId, Long participantId);

    /**
     * Creates new {@link Participant} from entered Data in {@link ParticipantDto}
     *
     * @param participantDto Entered data
     * @param teamId         Team's id, whose Participant will be created
     * @return New {@link Participant}
     */
    Participant createParticipant(final ParticipantDto participantDto, final Long teamId);

    /**
     * Tries to update existing {@link Participant} with data from {@link ParticipantDto}, if it exists
     *
     * @param newParticipant Updated {@link ParticipantDto} data
     * @param participantId  Participant's id
     * @param teamId         {@link Team}'s id
     * @return Updated {@link Participant}
     * @throws EntityNotFoundException If Participant's id doesn't exist for chosen team
     */
    Participant updateParticipant(ParticipantDto newParticipant, Long participantId, Long teamId);

    /**
     * Deletes by {@link Participant}'s id and {@link Team}'s id
     *
     * @param teamId        {@link Team}'s id
     * @param participantId Participant's id
     * @throws EntityNotFoundException If Participant's id doesn't exist for chosen team
     */
    void deleteParticipant(Long teamId, Long participantId);
}