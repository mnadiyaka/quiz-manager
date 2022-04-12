package com.sigma.controller;

import com.sigma.model.dto.ParticipantDto;
import com.sigma.model.dto.TeamDto;
import com.sigma.model.dto.UserDto;
import com.sigma.model.entity.Team;
import com.sigma.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/{userId}")
@Slf4j
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/teams")
    public List<TeamDto> getTeams(@PathVariable Long userId) {
        return teamService.getAllTeams(userId);
    }

    @PostMapping("/createTeam")
    public Team createTeam(@RequestBody TeamDto teamDto, @PathVariable Long userId) {
        return teamService.createTeam(teamDto, userId);
    }

    @PostMapping("/team/{teamId}/update")
    public String updateTeam(@RequestBody TeamDto teamDto, @PathVariable("userId") Long userId, @PathVariable("teamId") Long teamId) {
        teamService.updateTeam(teamDto, userId, teamId);
        return "team updated";
    }

    @DeleteMapping("/team/{teamId}/delete")
    public String deleteTeam(@PathVariable("userId") Long userId, @PathVariable("teamId") Long teamId) {
        teamService.deleteTeam(userId, teamId);
        return "deleted team";
    }
}