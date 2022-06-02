package com.sigma.service;

import com.sigma.exception.QuizException;
import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.dto.TeamDto;
import com.sigma.model.entity.Participant;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.Team;
import org.springframework.security.access.AuthorizationServiceException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface TeamService {

    /**
     * Finds all teams
     *
     * @return List of teams
     */
    List<TeamDto> getAllTeams();

    /**
     * Finds {@link Team} by id
     *
     * @param teamId Team id
     * @return Found team
     * @throws EntityNotFoundException If entered Team id does not exist
     */
    Team findTeamById(Long teamId);

    /**
     * Creates new {@link Team} with entered teamName in {@link TeamDto}, other fields created automatically
     *
     * @param teamDto New team data
     * @return New team
     * @throws EntityExistsException if team with same name exists
     */
    Team createTeam(TeamDto teamDto);

    /**
     * Updates {@link Team} with new data in {@link TeamDto} (teamNAme, captainId)
     *
     * @param updatedTeam Updated data
     * @param teamId      Team id
     * @return Updated team
     * @throws EntityNotFoundException       If entered Team id does not exist
     * @throws AuthorizationServiceException If wrong captain is authorized does not exist
     */
    Team updateTeam(TeamDto updatedTeam, Long teamId);

    /**
     * Deletes {@link Team} by id
     *
     * @param teamId Team's id
     * @throws EntityNotFoundException       If entered Team id does not exist
     * @throws AuthorizationServiceException If wrong captain is authorized does not exist
     */
    void deleteTeam(Long teamId);

    /**
     * Confirms {@link Team} as real one for {@link Quiz}
     *
     * @param team         existing team
     * @param confirmation is Confirmed or not
     * @return Updated team
     */
    Team confirmTeam(Team team, boolean confirmation);

    /**
     * Updates existing {@link Participant} in {@link Team} (works in collaboration with {@link ParticipantService})
     *
     * @param newParticipant Updated Player
     * @param teamId         Chosen team
     * @throws EntityNotFoundException       If entered Team id does not exist
     * @throws EntityNotFoundException       If entered Player does not exist
     * @throws AuthorizationServiceException If wrong captain is authorized does not exist
     */
    void updatePl(final ParticipantDto newParticipant, final Long participantId, final Long teamId);

    /**
     * Adds new {@link Participant}, entered in {@link ParticipantDto}, to existing team (works in collaboration with {@link ParticipantService})
     *
     * @param newParticipant New Player
     * @param teamId         Chosen team
     * @throws EntityNotFoundException       If entered Team id does not exist
     * @throws AuthorizationServiceException If wrong captain is authorized does not exist
     */
    void addPl(final ParticipantDto newParticipant, final Long teamId);

    /**
     * Assign existing {@link Team} for a {@link Quiz} (works in collaboration with {@link QuizService})
     *
     * @param quiz   Existing Quiz
     * @param teamId Existing Team id
     * @throws EntityNotFoundException If entered Quiz id does not exist
     * @throws QuizException           If Quiz is not ANNOUNCED yet
     * @throws QuizException           If Max Number of teams is reached
     * @throws QuizException           If Number of Participants doesn't suit criteria
     */
    Team applyForQuiz(final Quiz quiz, final Long teamId);
}