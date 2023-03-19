package com.example.hostelmanagement;

public class ReadFeedbackModel {
    private String email,feedback;
    public ReadFeedbackModel(){}
    public ReadFeedbackModel(String email, String feedback) {
        this.email = email;
        this.feedback = feedback;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
