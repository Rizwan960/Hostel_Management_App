package com.example.hostelmanagement;

public class model {
    private String aC,description,imageUrl,roomId,rent,number_of_beds;
public model()
{}

    public model(String AC, String description, String imageUrl, String roomId, String rent, String number_of_beds) {
        this.aC = AC;
        this.description = description;
       this. imageUrl = imageUrl;
       this. roomId = roomId;
       this.rent = rent;
       this. number_of_beds = number_of_beds;
    }

    public String getAC() {
        return aC;
    }

    public void setAC(String AC) {
        this.aC = AC;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
       this. imageUrl = imageUrl;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getNumber_of_beds() {
        return number_of_beds;
    }

    public void setNumber_of_beds(String number_of_beds) {
        this.number_of_beds = number_of_beds;
    }
}