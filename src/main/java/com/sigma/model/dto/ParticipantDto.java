package com.sigma.model.dto;

import com.sigma.model.entity.Participant;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class ParticipantDto {

    private Long id;

    @NotBlank(message = "Enter firstname")
    @Size(min = 3, max = 20)
    private String firstname;

    @NotBlank(message = "Enter firstname")
    @Size(min = 3, max = 20)
    private String lastname;

    public static ParticipantDto fromParticipant(Participant participant) {
        return new ParticipantDto()
                .setId(participant.getId())
                .setFirstname(participant.getFirstname())
                .setLastname(participant.getLastname());
    }

    public static Participant toParticipant(ParticipantDto participantDto) {
        return new Participant()
                .setId(participantDto.id)
                .setLastname(participantDto.lastname)
                .setFirstname(participantDto.firstname);
    }
}