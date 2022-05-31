package com.sigma.service;

import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.dto.TeamDto;
import com.sigma.model.entity.Participant;
import com.sigma.model.entity.Role;
import com.sigma.model.entity.Team;
import com.sigma.model.entity.User;
import com.sigma.repository.TeamRepository;
import com.sigma.service.impl.ParticipantServiceImpl;
import com.sigma.service.impl.TeamServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.EntityExistsException;
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

    private final ParticipantService participantService = mock(ParticipantServiceImpl.class);

    @BeforeEach
    void setUp() {
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
    public void createTeamTest_WithTeam_ThenReturnNewTeam() {//TODO: Correct, fails
        ArgumentCaptor<Team> captor = ArgumentCaptor.forClass(Team.class);

        Team team = new Team();
        team.setId(1L);
        team.setTeamName("name");
        User user = new User();
        user.setId(1L);
        user.setRole(Role.CAPTAIN);
        team.setCaptainId(1L);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(securityContext.getAuthentication().getPrincipal()).thenReturn(1L);
        SecurityContextHolder.setContext(securityContext);

        team.setCaptain(user);
        when(teamRepository.findByTeamName("name")).thenReturn(null);

        when(teamRepository.save(new Team())).thenReturn(new Team());

        Team expected = teamService.createTeam(TeamDto.fromTeam(team));

        verify(teamRepository, times(1)).save(captor.capture());
        Assertions.assertEquals(captor.getValue(), expected);
    }

    @Test
    public void createTeamTest_WithExistingTeamName_ThenThrowException() {
        Team team = new Team();
        team.setId(1L);
        team.setTeamName("name");
        User user = new User();
        user.setId(1L);
        user.setRole(Role.CAPTAIN);
        team.setCaptainId(1L);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(securityContext.getAuthentication().getPrincipal()).thenReturn(1L);
        SecurityContextHolder.setContext(securityContext);

        team.setCaptain(user);
        when(teamRepository.findByTeamName("name")).thenReturn(team);

        Assertions.assertThrows(EntityExistsException.class, ()->teamService.createTeam(TeamDto.fromTeam(team)));
    }

    @Test
    public void updateTeamTest_WithUpdatedTeamAndId_ThenReturnUpdatedTeam() {
        Team team = new Team();
        team.setId(1L);
        team.setTeamName("name");
        team.setCaptainId(1L);

        User user = new User();
        user.setId(1L);
        user.setRole(Role.CAPTAIN);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(securityContext.getAuthentication().getPrincipal()).thenReturn(1L);
        SecurityContextHolder.setContext(securityContext);

        team.setCaptain(user);

        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));

        Team oldTeam = teamService.findTeamById(1L);
        oldTeam.setTeamName("new1111");

        when(teamRepository.save(oldTeam)).thenReturn(oldTeam);

        Team actual = teamService.updateTeam(TeamDto.fromTeam(oldTeam), 1L);
        Assertions.assertEquals(oldTeam, actual);
        verify(teamRepository, times(1)).save(oldTeam);
    }

    @Test
    public void deleteTeam_WithCorrectCaptain() {
        Team expected = new Team();
        expected.setId(1L);
        User user = new User();
        user.setId(1L);
        expected.setCaptainId(1L);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(securityContext.getAuthentication().getPrincipal()).thenReturn(1L);
        SecurityContextHolder.setContext(securityContext);

        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(expected));

        teamService.deleteTeam(1L);
        verify(teamRepository).delete(expected);
    }

    @Test
    public void deleteTeam_WithIncorrectCaptain_ThenThrowException() {
        Team expected = new Team();
        expected.setId(1L);
        when(teamRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> teamService.deleteTeam(anyLong()));
        verify(teamRepository, times(0)).delete(expected);
    }

    @Test
    public void getAllTeams_ThenReturnList() {
        List<Team> teams = new ArrayList<>();
        teams.add(new Team().setCaptain(new User("pp1", "pp", Role.CAPTAIN)));
        teams.add(new Team().setCaptain(new User("pp2", "pp", Role.CAPTAIN)));
        teams.add(new Team().setCaptain(new User("pp3", "pp", Role.CAPTAIN)));
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
        Assertions.assertTrue(actual.isConfirmed());
        verify(teamRepository, times(1)).save(expected);
    }

    @Test
    public void updatePlayer_WithParticipantDtoAndParticipantIdAndTeamId_ThenSaveTeam() {
        Team team = new Team();
        team.setId(1L);
        team.setTeamName("name");
        team.setCaptainId(1L);

        List<Participant> participants = new ArrayList<>();
        Participant participant = new Participant();
        participant.setFirstname("new");
        participant.setId(1L);
        participants.add(participant);

        participant = new Participant();
        participant.setFirstname("new");
        participant.setId(2L);
        participants.add(participant);

        User user = new User();
        user.setId(1L);
        user.setRole(Role.CAPTAIN);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(securityContext.getAuthentication().getPrincipal()).thenReturn(1L);
        SecurityContextHolder.setContext(securityContext);

        team.setCaptain(user);

        team.setParticipants(participants);
        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));
        when(participantService.findParticipantById(1L, 1L)).thenReturn(participants.get(1));

        participant = participantService.findParticipantById(1L, 1L);
        participant.setFirstname("1111");

        when(participantService.createParticipant(ParticipantDto.fromParticipant(participant), 1L)).thenReturn(participant);
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
        team.setCaptainId(1L);
        team.setParticipants(new ArrayList<>());

        User user = new User();
        user.setId(1L);
        user.setRole(Role.CAPTAIN);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(securityContext.getAuthentication().getPrincipal()).thenReturn(1L);
        SecurityContextHolder.setContext(securityContext);

        team.setCaptain(user);

        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));
        when(teamRepository.save(team)).thenReturn(team);

        teamService.addPl(new ParticipantDto().setTeamId(1L), 1L);

        Assertions.assertEquals(1, team.getParticipants().size());
        verify(teamRepository, times(1)).save(team);
    }
}