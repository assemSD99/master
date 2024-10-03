package com.saadaoui.master.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PersonalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String firstNameArabic;
    private String lastNameArabic;
    private String nationality;
    private String profession;
    private String phone;
    private String birthDate;
    private String birthPlace;
    private String gender;
    private String address;
    private String city;

    // Getters et setters
    // Constructeurs (vide et complet)

    public PersonalInfo() {
    }

    public PersonalInfo(String firstName, String lastName, String firstNameArabic, String lastNameArabic, String nationality, String profession, String phone, String birthDate, String birthPlace, String gender, String address, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.firstNameArabic = firstNameArabic;
        this.lastNameArabic = lastNameArabic;
        this.nationality = nationality;
        this.profession = profession;
        this.phone = phone;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.gender = gender;
        this.address = address;
        this.city = city;
    }

    // Getters et setters...


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstNameArabic() {
        return firstNameArabic;
    }

    public void setFirstNameArabic(String firstNameArabic) {
        this.firstNameArabic = firstNameArabic;
    }

    public String getLastNameArabic() {
        return lastNameArabic;
    }

    public void setLastNameArabic(String lastNameArabic) {
        this.lastNameArabic = lastNameArabic;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
