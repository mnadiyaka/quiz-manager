package com.sigma.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_id")
    private int teamId;

    @Column(name = "team_name")
    private String teamName;


    @Column(name = "confirmed")
    private boolean confirmed;

    @OneToMany
    @Column(name = "quiz_id")
    private List<Quiz> quizId;

    @OneToOne
    @Column(name = "captain_id")
    private User captainId;

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public List<Quiz> getQuizId() {
        return quizId;
    }

    public void setQuizId(List<Quiz> quizId) {
        this.quizId = quizId;
    }

    public User getCaptainId() {
        return captainId;
    }

    public void setCaptainId(User captainId) {
        this.captainId = captainId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return teamId == team.teamId && confirmed == team.confirmed && teamName.equals(team.teamName) && Objects.equals(quizId, team.quizId) && captainId.equals(team.captainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamId, teamName, confirmed, quizId, captainId);
    }
}