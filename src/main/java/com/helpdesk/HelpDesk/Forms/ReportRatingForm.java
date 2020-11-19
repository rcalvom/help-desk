package com.helpdesk.HelpDesk.Forms;

public class ReportRatingForm {
    private String name;
    private int total;
    private int excellent;
    private int good;
    private int regular;
    private int bad;
    private int deficient;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getExcellent() {
        return excellent;
    }

    public void setExcellent(int excellent) {
        this.excellent = excellent;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public int getRegular() {
        return regular;
    }

    public void setRegular(int regular) {
        this.regular = regular;
    }

    public int getBad() {
        return bad;
    }

    public void setBad(int bad) {
        this.bad = bad;
    }

    public int getDeficient() {
        return deficient;
    }

    public void setDeficient(int deficient) {
        this.deficient = deficient;
    }
}
