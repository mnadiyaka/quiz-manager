package com.sigma.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_id")
    private int teamId;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "confirmed")
    private boolean confirmed;

    @ManyToMany
    @JoinTable(name = "enrolled_quizzes",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "quiz_id"))
    @Column(name = "quiz_id")
    private List<Quiz> quizzes;

    @OneToMany(mappedBy = "team")
    @Column(name = "captain_id")
    private List<User> captainId;

    @OneToMany(mappedBy = "teamId")
    private List<Participant> participants;

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
        return quizzes;
    }

    public void setQuizId(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public List<User> getCaptainId() {
        return captainId;
    }

    public void setCaptainId(List<User> captainId) {
        this.captainId = captainId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return teamId == team.teamId && confirmed == team.confirmed && teamName.equals(team.teamName) && quizzes.equals(team.quizzes) && captainId.equals(team.captainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamId, teamName, confirmed, quizzes, captainId);
    }
}
