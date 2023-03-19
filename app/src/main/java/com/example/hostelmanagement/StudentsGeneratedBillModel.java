package com.example.hostelmanagement;

public class StudentsGeneratedBillModel {
    private String bill,lastDate,roomId,email;
   public StudentsGeneratedBillModel(){}

    public StudentsGeneratedBillModel(String bill, String lastDate,String roomId,String email) {
        this.bill = bill;
        this.lastDate = lastDate;
        this.roomId=roomId;
        this.email=email;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
