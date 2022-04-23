package com.sigma.repository;

import com.sigma.model.entity.Participant;
import com.sigma.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    public Participant findParticipantByIdAndTeamId(Long id, Long teamId);

    public List<Participant> findParticipantsByTeamId(Long teamId);
}