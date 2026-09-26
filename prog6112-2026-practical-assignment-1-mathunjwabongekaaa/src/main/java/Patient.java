/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mathu
 */
public class Patient {
    private String patientID;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String medicalCondition;
    private PatientCatagory category;
    
    // Constructor
    public Patient(String patientID, String firstName, String lastName, 
                   int age, String gender, String medicalCondition, 
                   PatientCatagory category) {
        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.category = category;
    }
    
    // Getters
    public String getPatientID() {
        return patientID;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getGender() {
        return gender;
    }
    
    public String getMedicalCondition() {
        return medicalCondition;
    }
    
    public PatientCatagory getCatagory() {
        return category;
    }
    
    // Setters
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }
    
    public void setCatagory(PatientCatagory category) {
        this.category = category;
    }
    
    // Display patient details
    public void displayDetails() {
        System.out.println("Patient ID: " + patientID);
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Medical Condition: " + medicalCondition);
        System.out.println("Patient Category: " + (category != null ? category : "Not set"));
    }
}