package com.helpdesk.HelpDesk.Forms;

public class CreateRequestForm {

    private String description;

    public CreateRequestForm() {}

    public CreateRequestForm(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
