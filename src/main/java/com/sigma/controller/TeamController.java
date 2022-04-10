package com.sigma.controller;

import com.sigma.model.dto.QuizDto;
import com.sigma.model.dto.TeamDto;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.Team;
import com.sigma.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/")
@Slf4j
public class TeamController {

    private final TeamService teamService;
    @PostMapping("/{userId}/createTeam")

    public Team createTeam(@RequestBody TeamDto teamDto, @PathVariable Long userId){
        return teamService.createTeam(teamDto, userId);
    }
}
