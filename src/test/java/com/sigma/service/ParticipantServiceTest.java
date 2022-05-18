package com.sigma.service;

import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.entity.Participant;
import com.sigma.repository.ParticipantRepository;
import com.sigma.service.impl.ParticipantServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
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
    }

    @Test
    public void findParticipantById_WithNonExistingId_ThenThrowException() {
        when(participantRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> participantService.findParticipantById(anyLong(), anyLong()));
    }

    @Test
    @Transactional
    public void createParticipantTest() {
        Participant expected = new Participant();
        expected.setId(1L);
        expected.setTeamId(1L);

        when(participantRepository.save(expected)).thenReturn(expected);

        Participant actual = participantService.createParticipant(ParticipantDto.fromParticipant(expected), 1L);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Transactional
    public void updateParticipantTest() {
        Participant participant = new Participant();
        participant.setId(1L);
        participant.setTeamId(1L);
        when(participantRepository.findParticipantByIdAndTeamId(anyLong(), anyLong())).thenReturn(participant);

        Participant oldParticipant = participantService.findParticipantById(1L, 1L);
        oldParticipant.setLastname("new");

        when(participantRepository.save(oldParticipant)).thenReturn(oldParticipant);

        Participant actual = participantService.updateParticipant(ParticipantDto.fromParticipant(oldParticipant), 1L, 1L);
        Assertions.assertEquals(oldParticipant, actual);
    }

    @Test
    @Transactional
    public void deleteParticipants() {//TODO: correct
        Participant expected = new Participant();
        expected.setId(1L);
        expected.setTeamId(1L);
        when(participantRepository.findParticipantByIdAndTeamId(anyLong(), anyLong())).thenReturn(expected);

        participantService.deleteParticipant(1L, 1L);
        verify(participantRepository).delete(expected);
    }

    @Test
    public void getAllParticipants() {
        List<Participant> participants = new ArrayList<>();
        participants.add(new Participant());
        participants.add(new Participant());
        participants.add(new Participant());
        when(participantRepository.findParticipantsByTeamId(anyLong())).thenReturn(participants);

        List<ParticipantDto> actual = participantService.getAllParticipants(anyLong());
        Assertions.assertEquals(participants.stream().map(ParticipantDto::fromParticipant).collect(Collectors.toList()), actual);
    }
}