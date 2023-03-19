package com.example.hostelmanagement;

public class acceptRoomModel {
    private String booked;
    private String roomId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;
    public acceptRoomModel()
    {}

    public acceptRoomModel(String booked, String roomId,String email) {
        this.booked = booked;
        this.roomId = roomId;
        this.email=email;
    }

    public String getBooked() {
        return booked;
    }

    public void setBooked(String booked) {
        this.booked = booked;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
