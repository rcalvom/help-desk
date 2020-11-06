package com.helpdesk.HelpDesk.Forms;

public class AssignRequestForm {
    private String id;
    private String creationDate;
    private String userUsername;
    private String specification;
    private String[] agentUsername;
    private String category;
    private Integer inventoryPlate;
    private int equipmentNumber;
    public AssignRequestForm() {}

    public AssignRequestForm(String id, String creationDate, String userUsername, String specification, Integer inventoryPlate, int equipmentNumber) {
        this.id = id;
        this.creationDate = creationDate;
        this.userUsername = userUsername;
        this.specification = specification;
        this.inventoryPlate = inventoryPlate;
        this.equipmentNumber = equipmentNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String[] getAgentUsername() {
        return agentUsername;
    }

    public void setAgentUsername(String[] agentUsername) {
        this.agentUsername = agentUsername;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getInventoryPlate() {
        return inventoryPlate;
    }

    public void setInventoryPlate(Integer inventoryPlate) {
        this.inventoryPlate = inventoryPlate;
    }

    public int getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(int equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }
}