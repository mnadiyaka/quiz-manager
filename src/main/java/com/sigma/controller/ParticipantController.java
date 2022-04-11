package com.sigma.controller;

import com.sigma.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/{userId}/team/{teamId}/")
@Slf4j
public class ParticipantController {

    private final ParticipantService participantService;

//    @PostMapping("/addPart")
//    public Participant addParticipant(@RequestBody ParticipantDto participantDto, @PathVariable Long userId, @PathVariable Long teamId) {
//        log.info("Participant added");
//        return participantService.createParticipant(participantDto, userId, teamId);
//    }

    @DeleteMapping("/player/{playerId}/delete")//TODO: has exception about toString()
    public String deleteUser(@PathVariable("userId") Long userId, @PathVariable("teamId") Long teamId, @PathVariable("playerId") Long playerId) {
        participantService.deleteParticipant(userId, teamId, playerId);
        return "deleted user";
    }
}
