/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mathu
 */

public class Inpatient extends Patient {

    private String wardNumber;
    private int bedNumber;

    // Constructor
    public Inpatient(String patientID, String firstName, String lastName,
                     int age, String gender, String medicalCondition,
                     String wardNumber) {

        // Initialise inherited attributes
        super(patientID, firstName, lastName, age, gender,
              medicalCondition, PatientCatagory.INPATIENT);

        this.wardNumber = wardNumber;
        this.bedNumber = 0;//Not allocated
    }

    // Getter for ward number
    public String getWardNumber() {
        return wardNumber;
    }

    // Getter for bed number
    public int getBedNumber() {
        return bedNumber;
    }

    // Setter for bed number
    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    // Override displayDetails()
    @Override
    public void displayDetails() {

        super.displayDetails();

        System.out.println("Ward Number: " + wardNumber);
        System.out.println("Bed Number: " + bedNumber);
    }
}
