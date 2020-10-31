package com.helpdesk.HelpDesk.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Lob
    private String specification;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Rating rating;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar date;

    public Feedback() {}

    public Feedback(String specification, Rating rating){
        this.specification = specification;
        this.rating = rating;
        this.date = Calendar.getInstance();
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return dateFormat.format(this.date.getTime());
    }

    private enum Rating{
        EXCELENTE, BUENO, REGULAR, MALO, DEFICIENTE
    }

}
