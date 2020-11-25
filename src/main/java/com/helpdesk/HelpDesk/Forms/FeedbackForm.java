package com.helpdesk.HelpDesk.Forms;

public class FeedbackForm {
    private String specification;
    private String rating;
    private boolean successful;

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        successful = successful;
    }
}
