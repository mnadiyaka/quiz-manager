package com.sigma.service;

import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.dto.TeamDto;
import com.sigma.model.entity.Participant;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.Team;
import com.sigma.repository.ParticipantRepository;
import com.sigma.repository.TeamRepository;
import com.sigma.service.impl.ParticipantServiceImpl;
import com.sigma.service.impl.TeamServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AuthorizationServiceException;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TeamServiceTest {

    private TeamRepository teamRepository;

    private TeamService teamService;

    private ParticipantRepository participantRepository;

    private ParticipantService participantService;

    @BeforeEach
    void setUp() {
        participantRepository = mock(ParticipantRepository.class);
        participantService = new ParticipantServiceImpl(participantRepository);

        teamRepository = mock(TeamRepository.class);
        teamService = new TeamServiceImpl(teamRepository, participantService);
    }

    @Test
    public void findTeamById_WithExistingId_ThenReturnTeam() {
        final Team expectedResult = new Team();
        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(expectedResult));
        final Team result = teamService.findTeamById(anyLong());

        Assertions.assertEquals(expectedResult, result);
        verify(teamRepository, times(1)).findById(anyLong());
    }

    @Test
    public void findTeamById_WithNonExistingId_ThenThrowException() {
        when(teamRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> teamService.findTeamById(anyLong()));
    }

    @Test
    public void createTeam_WithTeamDto_ThenReturnNewTeam() {
        Team expected = new Team();
        expected.setId(1L);

        when(teamRepository.save(expected)).thenReturn(expected);

        Team actual = teamService.createTeam(TeamDto.fromTeam(expected));
        Assertions.assertEquals(expected, actual);
        verify(teamRepository, times(1)).save(expected);
    }

    @Test
    public void updateTeam_WithUpdatedTeamAndId_ThenReturnUpdatedTeam() {
        Team team = new Team();
        team.setId(1L);
        team.setTeamName("name");
        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));

        Team oldTeam = teamService.findTeamById(1L);
        oldTeam.setTeamName("new1111");

        when(teamRepository.save(oldTeam)).thenReturn(oldTeam);

        /*Team actual = */
        teamService.updateTeam(TeamDto.fromTeam(oldTeam), 1L);
        // Assertions.assertEquals(oldTeam, actual);//TODO: when merged previous will work
        verify(teamRepository, times(1)).save(oldTeam);
    }

    @Test
    public void deleteTeam_WithCorrectCaptain() {
        Team expected = new Team();
        expected.setId(1L);
        expected.setCaptainId(1L);
        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(expected));

        teamService.deleteTeam(1L);
        verify(teamRepository).delete(expected);
    }

    @Test
    public void deleteParticipants_WithIncorrectCaptain_ThenThrowException() {
        Team expected = new Team();
        expected.setId(1L);
        expected.setCaptainId(1L);
        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(expected));

        Assertions.assertThrows(AuthorizationServiceException.class, () -> teamService.deleteTeam(anyLong()));
        verify(teamRepository, times(0)).delete(expected);
    }

    @Test
    public void getAllTeams_ThenReturnList() {
        List<Team> teams = new ArrayList<>();
        teams.add(new Team().setCaptainId(1L));
        teams.add(new Team().setCaptainId(2L));
        teams.add(new Team().setCaptainId(3L));
        when(teamRepository.findAll()).thenReturn(teams);

        List<TeamDto> actual = teamService.getAllTeams();
        Assertions.assertEquals(teams.stream().map(TeamDto::fromTeam).collect(Collectors.toList()), actual);
        verify(teamRepository, times(1)).findAll();
    }

    @Test
    public void confirmTeam_WithTeamAndBoolean_ThenReturnUpdatedTeam() {
        Team expected = new Team();
        expected.setId(1L);

        when(teamRepository.save(expected)).thenReturn(expected);

        Team actual = teamService.confirmTeam(expected, true);
//        Assertions.assertEquals(!expected.getConfirmed(), actual.getConfirmed());
        verify(teamRepository, times(1)).save(expected);
    }

    @Test
    public void updatePlayer_WithParticipantDtoAndParticipantIdAndTeamId_ThenSaveTeam() {
        Team team = new Team();
        team.setId(1L);
        team.setTeamName("name");

        List<Participant> participants = new ArrayList<>();
        Participant participant = new Participant();
        participant.setFirstname("new");
        participant.setId(1L);
        participants.add(participant);

        participant = new Participant();
        participant.setFirstname("new");
        participant.setId(2L);
        participants.add(participant);

        team.setParticipants(participants);
        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));
        when(participantRepository.findParticipantByIdAndTeamId(1L, 1L)).thenReturn(participants.get(1));

        participant = participantService.findParticipantById(1L, 1L);
        participant.setFirstname("1111");

        when(participantRepository.save(participant)).thenReturn(participant);
        when(teamRepository.save(team)).thenReturn(team);

        teamService.updatePl(ParticipantDto.fromParticipant(participant), 1L, 1L);

        Assertions.assertEquals(participant, participantService.findParticipantById(1L, 1L));
        verify(teamRepository, times(1)).save(team);
    }

    @Test
    public void addPlayer_WithParticipantDtoAndTeamId_ThenSaveTeam() {
        Team team = new Team();
        team.setId(1L);
        team.setTeamName("name");
        team.setParticipants(new ArrayList<>());

        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));
        when(teamRepository.save(team)).thenReturn(team);

        teamService.addPl(new ParticipantDto().setTeamId(1L), 1L);

        Assertions.assertEquals(1, team.getParticipants().size());
//        verify
    }

    @Test
    public void applyForQuiz_WithQuizAndTeanId_ThenReturnTeam(){
        Team team = new Team();
        team.setId(1L);
        team.setTeamName("name");
        team.setQuizzes(new ArrayList<>());

        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));
        when(teamRepository.save(team)).thenReturn(team);

        Quiz quiz = new Quiz();
        quiz.setId(1L);

        teamService.applyForQuiz(quiz, 1L);
        Assertions.assertEquals(1, team.getParticipants().size());
//verify
    }
}