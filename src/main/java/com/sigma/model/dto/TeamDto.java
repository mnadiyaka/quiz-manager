package com.sigma.model.dto;

import com.sigma.model.entity.Team;
import com.sigma.model.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class TeamDto {

    private Long id;

    @NotBlank(message = "Enter name")
    @Size(min = 3, max = 20)
    private String teamName;

    private boolean confirmed;

    private Long captainId;

//    private List<ParticipantDto> participants;

    public static TeamDto fromTeam(Team team) {
        return new TeamDto()
                .setId(team.getId())
                .setTeamName(team.getTeamName())
                //.setConfirmed(team.getConfirmed())
                .setCaptainId(team.getCaptain().getId());
                //.setParticipants((team.getParticipants()).stream().map((player)->ParticipantDto.fromParticipant(player)).collect(Collectors.toList()));
    }

    public static Team toTeam(TeamDto teamDto) {
        return new Team()
                .setId(teamDto.getId())
                .setTeamName(teamDto.getTeamName())
                //.setConfirmed(teamDto.getConfirmed())
                .setCaptainId(teamDto.getCaptainId());
                //.setParticipants(teamDto.getParticipants().stream().map((playerDto)->ParticipantDto.toParticipant(playerDto)).collect(Collectors.toList()));
    }
}