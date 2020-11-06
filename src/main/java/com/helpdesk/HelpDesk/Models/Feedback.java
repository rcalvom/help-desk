package com.helpdesk.HelpDesk.Models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Lob
    private String specification;

    @NotNull
    @ManyToOne
    private Rating rating;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar date;

    public Feedback() {}

    public Feedback(String specification, Rating rating){
        this.specification = specification;
        this.rating = rating;
        this.date = Calendar.getInstance(TimeZone.getTimeZone("GMT-5:00"));
    }


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecification() {
        return this.specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Rating getRating() {
        return this.rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Calendar getDate() {
        return this.date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String formatDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        return dateFormat.format(this.date.getTime());
    }

}
