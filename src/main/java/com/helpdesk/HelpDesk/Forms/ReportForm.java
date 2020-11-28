package com.helpdesk.HelpDesk.Forms;

public class ReportForm {

    private boolean equipment;
    private boolean request;
    private boolean rating;
    private boolean efficacy;
    private SelectType selectType;

    public boolean getEquipment() { return equipment; }

    public void setEquipment(boolean equipment) { this.equipment = equipment; }

    public boolean getRequest() { return request; }

    public void setRequest(boolean request) { this.request = request; }

    public boolean getRating() { return rating; }

    public void setRating(boolean rating) { this.rating = rating; }

    public boolean getEfficacy() { return efficacy; }

    public void setEfficacy(boolean efficacy) { this.efficacy = efficacy; }

    public SelectType getSelectType() { return selectType; }

    public void setSelectType(SelectType selectType) { this.selectType = selectType; }

    public enum SelectType{
        Dependencia,
        Vinculación,
        Agente,
        Categoría
    }
}
