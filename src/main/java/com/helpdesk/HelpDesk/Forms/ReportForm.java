package com.helpdesk.HelpDesk.Forms;

public class ReportForm {

    private boolean[] checkboxes;
    private SelectType selectType;

    public boolean[] getCheckboxes() { return checkboxes; }

    public void setCheckboxes(boolean[] checkboxes) { this.checkboxes = checkboxes; }

    public SelectType getSelectType() { return selectType; }

    public void setSelectType(SelectType selectType) { this.selectType = selectType; }

    public enum SelectType{
        DEPENDENCIA,
        VINCULACION,
        AGENTE,
        CATEGORIA
    }
}
