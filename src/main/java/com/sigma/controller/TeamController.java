package com.sigma.controller;

import com.sigma.model.dto.TeamDto;
import com.sigma.model.entity.Team;
import com.sigma.model.entity.User;
import com.sigma.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping("/team")
@Slf4j
public class TeamController {
    private final TeamService teamService;

    @GetMapping("/all")
    public List<TeamDto> getTeams(@PathVariable Long userId) {
        return teamService.getAllTeams();
    }

    @PreAuthorize("hasRole('ROLE_CAPTAIN')")
    @PostMapping("")
    public Team createTeam(@RequestBody TeamDto teamDto, @AuthenticationPrincipal User user) {
        return teamService.createTeam(teamDto, user);
    }

    @PatchMapping("/{teamId}")
    public String updateTeam(@RequestBody TeamDto teamDto, @PathVariable("teamId") Long teamId) {
        teamService.updateTeam(teamDto, teamId);
        return "team updated";
    }

    @DeleteMapping("/{teamId}")
    public String deleteTeam(@PathVariable("teamId") Long teamId) {
        teamService.deleteTeam(teamId);
        return "deleted team";
    }
}