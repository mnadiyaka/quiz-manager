package com.sigma.model.dto;

import com.sigma.model.entity.Location;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.Team;
import com.sigma.model.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class TeamDto {

    private Long id;

    private String teamName;

    private boolean confirmed;

    private User captain;

    private List<Quiz> quizzes;

    public static TeamDto fromTeam(Team team) {
        return new TeamDto()
                .setId(team.getId())
                .setTeamName(team.getTeamName())
                //.setConfirmed(team.getConfirmed())
                .setCaptain(team.getCaptain())
                .setQuizzes(team.getQuizzes());
    }

    public static Team toTeam(TeamDto teamDto) {
        return new Team()
                .setId(teamDto.getId())
                .setTeamName(teamDto.getTeamName())
                //.setConfirmed(teamDto.getConfirmed())
                .setCaptain(teamDto.getCaptain())
                .setQuizzes(teamDto.getQuizzes());
    }
}