package com.example.medwheels_pat1;
public class HelperClass {
    String name, email, password, additionalNotes,phone, emergencyRelation,emergencyName,medHistory,allergies,dob,address;
    public HelperClass(String email, String password, String name,String additionalNotes,String phone, String emergencyRelation,String emergencyName,String medHistory,String allergies,String dob,String address,String gender,String blood) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address=address;
        this.additionalNotes=additionalNotes;
        this.phone=phone;
        this.emergencyName=emergencyName;
        this.emergencyRelation=emergencyRelation;
        this.medHistory=medHistory;
        this.allergies=allergies;
        this.dob=dob;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
