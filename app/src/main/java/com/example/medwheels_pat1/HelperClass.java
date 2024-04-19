package com.example.medwheels_pat1;
public class HelperClass {
    String mail,pass,name,addNotes,add,Phone,emName,emRela,med,all,Dob,gender_d,blood_d;

    public HelperClass() {
    }

    public HelperClass(String mail, String pass, String name, String addNotes, String add, String phone, String emName, String emRela, String med, String all, String dob, String gender_d, String blood_d) {
        this.mail = mail;
        this.pass = pass;
        this.name = name;
        this.addNotes = addNotes;
        this.add = add;
        Phone = phone;
        this.emName = emName;
        this.emRela = emRela;
        this.med = med;
        this.all = all;
        Dob = dob;
        this.gender_d = gender_d;
        this.blood_d = blood_d;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddNotes() {
        return addNotes;
    }

    public void setAddNotes(String addNotes) {
        this.addNotes = addNotes;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getEmName() {
        return emName;
    }

    public void setEmName(String emName) {
        this.emName = emName;
    }

    public String getEmRela() {
        return emRela;
    }

    public void setEmRela(String emRela) {
        this.emRela = emRela;
    }

    public String getMed() {
        return med;
    }

    public void setMed(String med) {
        this.med = med;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        this.Dob = dob;
    }

    public String getGender_d() {
        return gender_d;
    }

    public void setGender_d(String gender_d) {
        this.gender_d = gender_d;
    }

    public String getBlood_d() {
        return blood_d;
    }

    public void setBlood_d(String blood_d) {
        this.blood_d = blood_d;
    }
}
