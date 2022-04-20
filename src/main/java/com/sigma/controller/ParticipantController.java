package com.sigma.controller;

import com.sigma.model.dto.ParticipantDto;
import com.sigma.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team/{teamId}/player")
@Slf4j
public class ParticipantController {

    private final ParticipantService participantService;

    @DeleteMapping("/{playerId}")
    public String deleteUser(@PathVariable("teamId") Long teamId, @PathVariable("playerId") Long playerId) {
        participantService.deleteParticipant(teamId, playerId);
        return "deleted player";
    }

    @PostMapping("")
    public String addParticipant(@RequestBody ParticipantDto participantDto, @PathVariable Long teamId) {
        participantService.createParticipant(participantDto, teamId);
        log.info("Participant added");
        return "player created";
    }

    @PatchMapping("/{playerId}")
    public String updParticipant(@RequestBody ParticipantDto participantDto, @PathVariable Long teamId, @PathVariable Long playerId) {
        participantService.updateParticipant(participantDto, playerId, teamId);
        log.info("Participant updated");
        return "player updated";
    }

    @GetMapping("/all")
    public List<ParticipantDto> getPlayers(@PathVariable Long teamId) {
        return participantService.getAllParticipants(teamId);
    }
}
