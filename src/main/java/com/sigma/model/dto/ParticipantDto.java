package com.sigma.model.dto;

import com.sigma.model.entity.Participant;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ParticipantDto {

    private Long id;

    private String firstname;

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