package com.example.hostelmanagement;

public class billClass {
   private String roomId,email;
   public billClass(){}

    public billClass(String roomId, String email) {
        this.roomId = roomId;
        this.email = email;
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
