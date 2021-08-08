package com.sportingCenterWebApp.calendarservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Event")
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EVENT_ID")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "data")
    private String data;

    @Column(name = "inizio")
    private String oraInizio;

    @Column(name = "fine")
    private String oraFine;

    @Column(name = "activityId")
    private String activityId;

    @Column(name = "numberPartecipants")
    private Long number;

    public Event() {
        this.title ="";
        this.activityId ="";
        this.data ="";
        this.oraInizio="";
        this.oraFine="";
    }


    @Override
    public String toString() {
        return "{ "+ "Id: " +  this.id + " Name: " + this.title + " " + this.data + " " + this
        .oraInizio + this.oraFine + " actid: " + this.activityId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(String oraInizio) {
        this.oraInizio = oraInizio;
    }

    public String getOraFine() {
        return oraFine;
    }

    public void setOraFine(String oraFine) {
        this.oraFine = oraFine;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}


