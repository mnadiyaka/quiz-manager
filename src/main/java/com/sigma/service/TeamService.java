package com.sigma.service;

import com.sigma.exception.QuizException;
import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.dto.TeamDto;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.Team;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface TeamService {
    List<TeamDto> getAllTeams();

    Team findTeamById(Long teamId);

    Team createTeam(TeamDto teamDto);

    Team updateTeam(TeamDto updatedTeam, Long teamId);

    void deleteTeam(Long teamId);

    Team confirmTeam(Team team, boolean confirmation);

    void updatePl(final ParticipantDto newParticipant, final Long participantId, final Long teamId);

    /**
     * Adds new player to exicting team
     * @param newParticipant New Player
     * @param teamId
     */
    void addPl(final ParticipantDto newParticipant, final Long teamId);

    /**
     * Works with quizService to assign existing Team for a Quiz
     * @param quiz Existing Quiz
     * @param teamId Existing Team id
     * @throws EntityNotFoundException If entered Quiz id does not exist
     * @throws QuizException If Quiz is not ANNOUNCED yet
     * @throws QuizException If Max Number of teams is reached
     * @throws QuizException If Number of Participants doesn't suit criteria
     */
    Team applyForQuiz(final Quiz quiz, final Long teamId);
}