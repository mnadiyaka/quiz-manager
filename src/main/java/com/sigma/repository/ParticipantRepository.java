package com.sigma.repository;

import com.sigma.model.Participant;
import com.sigma.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    public List<Participant> findParticipantsByTeam(Team team);
}
