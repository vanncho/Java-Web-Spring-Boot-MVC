package com.residentevil.entities;

import com.residentevil.entities.enumerations.Magnitude;
import com.residentevil.entities.enumerations.Mutation;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "viruses")
public class Virus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "side_effects")
    private String sideEffects;

    @Column(name = "creator")
    private String creator;

    @Column(name = "is_deadly")
    private boolean isDeadly;

    @Column(name = "is_curable")
    private boolean isCurable;

    @Column(name = "mutation")
    @Enumerated(EnumType.STRING)
    private Mutation mutation;

    @Column(name = "turnover_rate")
    private Double turnoverRate;

    @Column(name = "hours_until_turn")
    private Integer hoursUntilTurn;

    @Column(name = "magnitude")
    @Enumerated(EnumType.STRING)
    private Magnitude magnitude;

    @Column(name = "released_on")
    private Date releasedOn;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "viruses_capitals",
    joinColumns = @JoinColumn(name = "virus_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "capital_id", referencedColumnName = "id"))
    private Set<Capital> capitals;

    public Virus() {

        this.capitals = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean getIsDeadly() {
        return isDeadly;
    }

    public void setIsDeadly(boolean deadly) {
        isDeadly = deadly;
    }

    public boolean getIsCurable() {
        return isCurable;
    }

    public void setIsCurable(boolean curable) {
        isCurable = curable;
    }

    public Mutation getMutation() {
        return mutation;
    }

    public void setMutation(Mutation mutation) {
        this.mutation = mutation;
    }

    public Double getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(Double turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    public Integer getHoursUntilTurn() {
        return hoursUntilTurn;
    }

    public void setHoursUntilTurn(Integer hoursUntilTurn) {
        this.hoursUntilTurn = hoursUntilTurn;
    }

    public Magnitude getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Magnitude magnitude) {
        this.magnitude = magnitude;
    }

    public Date getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(Date releasedOn) {
        this.releasedOn = releasedOn;
    }

    public Set<Capital> getCapitals() {
        return capitals;
    }

    public void setCapitals(Set<Capital> capitals) {
        this.capitals = capitals;
    }
}
