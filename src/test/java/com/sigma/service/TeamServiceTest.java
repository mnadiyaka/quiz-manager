package com.sigma.service;

import com.sigma.configuration.auth.TokenAuth;
import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.dto.TeamDto;
import com.sigma.model.entity.Participant;
import com.sigma.model.entity.Team;
import com.sigma.model.entity.User;
import com.sigma.repository.ParticipantRepository;
import com.sigma.repository.TeamRepository;
import com.sigma.service.impl.ParticipantServiceImpl;
import com.sigma.service.impl.TeamServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
    public void createTeamTest_WithTeam_ThenReturnNewTeam() {
        User user = new User();
        user.setId(1L);
        SecurityContext securityContext = mock(SecurityContextImpl.class);
        TokenAuth tokenAuth = new TokenAuth(anyString());
        tokenAuth.setUser(user);
        when(securityContext.getAuthentication()).thenReturn(tokenAuth);

        Team expected = new Team();
        expected.setId(1L);
        expected.setCaptain(user);

        when(teamRepository.save(expected)).thenReturn(expected);

        Team actual = teamService.createTeam(TeamDto.fromTeam(expected));
        Assertions.assertEquals(expected, actual);
        verify(teamRepository, times(1)).save(expected);
    }

    @Test
    public void updateTeamTest_WithUpdatedTeamAndId_ThenReturnUpdatedTeam() {//TODO: change service
        Team team = new Team();
        team.setId(1L);
        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));

        Team oldTeam = teamService.findTeamById(1L);
        oldTeam.setTeamName("new");

        when(teamRepository.save(oldTeam)).thenReturn(oldTeam);

//        Team actual = teamService.updateTeam(TeamDto.fromTeam(oldTeam), 1L);
//        Assertions.assertEquals(oldTeam, actual);
        verify(teamRepository, times(1)).save(oldTeam);
    }

    @Test
    public void deleteTeam_WithCorrectCaptain() {
        Team expected = new Team();
        expected.setId(1L);
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
    public void getAllTeams_WithTeamId_ThenReturnList() {
        List<Team> teams = new ArrayList<>();
        teams.add(new Team());
        teams.add(new Team());
        teams.add(new Team());
        when(teamRepository.findAll()).thenReturn(teams);

        List<TeamDto> actual = teamService.getAllTeams();
        Assertions.assertEquals(teams.stream().map(TeamDto::fromTeam).collect(Collectors.toList()), actual);
        verify(teamRepository, times(1)).findAll();
    }
}