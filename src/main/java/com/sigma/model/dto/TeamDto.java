package com.sigma.model.dto;

import com.sigma.model.entity.Team;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class TeamDto {

    private Long id;

    @NotBlank(message = "Enter name")
    @Size(min = 3, max = 20)
    private String teamName;

    private boolean confirmed;

    private Long captainId;

    public static TeamDto fromTeam(Team team) {
        return new TeamDto()
                .setId(team.getId())
                .setTeamName(team.getTeamName())
                .setConfirmed(team.isConfirmed())
                .setCaptainId(team.getCaptain().getId());
    }

    public static Team toTeam(TeamDto teamDto) {
        return new Team()
                .setId(teamDto.getId())
                .setTeamName(teamDto.getTeamName())
                .setConfirmed(teamDto.isConfirmed())
                .setCaptainId(teamDto.getCaptainId());
    }
}