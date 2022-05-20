package com.sigma.service;

import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.entity.Participant;
import com.sigma.repository.ParticipantRepository;
import com.sigma.service.impl.ParticipantServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ParticipantServiceTest {

    private ParticipantRepository participantRepository;

    private ParticipantService participantService;

    @BeforeEach
    void setUp() {
        participantRepository = mock(ParticipantRepository.class);
        participantService = new ParticipantServiceImpl(participantRepository);
    }

    @Test
    public void findParticipantById_WithExistingId_ThenReturnParticipant() {
        final Participant expectedResult = new Participant();
        when(participantRepository.findParticipantByIdAndTeamId(anyLong(), anyLong())).thenReturn(expectedResult);
        final Participant result = participantService.findParticipantById(anyLong(), anyLong());

        Assertions.assertEquals(expectedResult, result);
        verify(participantRepository, times(1)).findParticipantByIdAndTeamId(anyLong(), anyLong());
    }

    @Test
    public void findParticipantById_WithNonExistingId_ThenThrowException() {
        when(participantRepository.findParticipantByIdAndTeamId(anyLong(), anyLong())).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> participantService.findParticipantById(anyLong(), anyLong()));
    }

    @Test
    public void createParticipantTest_WithParticipant_ThenReturnNewParticipant() {
        Participant expected = new Participant();
        expected.setId(1L);
        expected.setTeamId(1L);

        when(participantRepository.save(expected)).thenReturn(expected);

        Participant actual = participantService.createParticipant(ParticipantDto.fromParticipant(expected), 1L);
        Assertions.assertEquals(expected, actual);
        verify(participantRepository, times(1)).save(expected);
    }

    @Test
    public void updateParticipantTest_WithUpdatedParticipantAndId_ThenReturnUpdatedParticipant() {
        Participant participant = new Participant();
        participant.setId(1L);
        participant.setTeamId(1L);
        when(participantRepository.findParticipantByIdAndTeamId(anyLong(), anyLong())).thenReturn(participant);

        Participant oldParticipant = participantService.findParticipantById(1L, 1L);
        oldParticipant.setLastname("new");

        when(participantRepository.save(oldParticipant)).thenReturn(oldParticipant);

        Participant actual = participantService.updateParticipant(ParticipantDto.fromParticipant(oldParticipant), 1L, 1L);
        Assertions.assertEquals(oldParticipant, actual);
        verify(participantRepository, times(1)).save(oldParticipant);
    }

    @Test
    public void deleteParticipants_WithCorrectCaptain() {
        Participant expected = new Participant();
        expected.setId(1L);
        expected.setTeamId(1L);
        when(participantRepository.findParticipantByIdAndTeamId(anyLong(), anyLong())).thenReturn(expected);

        participantService.deleteParticipant(1L, 1L);
        verify(participantRepository).delete(expected);
    }

    @Test
    public void deleteParticipants_WithIncorrectCaptain_ThenThrowException() {
        Participant expected = new Participant();
        expected.setId(1L);
        expected.setTeamId(1L);
        when(participantRepository.findParticipantByIdAndTeamId(anyLong(), anyLong())).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> participantService.deleteParticipant(anyLong(), anyLong()));
        verify(participantRepository, times(0)).delete(expected);
    }

    @Test
    public void getAllParticipants_WithTeamId_ThenReturnList() {
        List<Participant> participants = new ArrayList<>();
        participants.add(new Participant());
        participants.add(new Participant());
        participants.add(new Participant());
        when(participantRepository.findParticipantsByTeamId(anyLong())).thenReturn(participants);

        List<ParticipantDto> actual = participantService.getAllParticipants(anyLong());
        Assertions.assertEquals(participants.stream().map(ParticipantDto::fromParticipant).collect(Collectors.toList()), actual);
        verify(participantRepository, times(1)).findParticipantsByTeamId(anyLong());
    }
}