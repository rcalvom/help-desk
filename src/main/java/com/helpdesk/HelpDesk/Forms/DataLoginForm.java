package com.helpdesk.HelpDesk.Forms;

public class DataLoginForm {

    private String name;
    private String username;
    private String boundingType;
    private String dependency;
    private String location;
    private String phone;
    private int phoneExtension;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBoundingType() {
        return boundingType;
    }

    public void setBoundingType(String boundingType) {
        this.boundingType = boundingType;
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPhoneExtension() {
        return phoneExtension;
    }

    public void setPhoneExtension(int phoneExtension) {
        this.phoneExtension = phoneExtension;
    }
}
