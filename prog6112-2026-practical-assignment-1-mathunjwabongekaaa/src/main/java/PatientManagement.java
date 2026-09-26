/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mathu
 */
import java.util.ArrayList;
import java.util.List;

public class PatientManagement {
    private List<Patient> patients;
    
    public PatientManagement() {
        patients = new ArrayList<>();
    }
    
    // Register a new patient
    public boolean registerPatient(Patient patient) {
        if (searchPatient(patient.getPatientID()) != null) {
            return false; // Patient already exists
        }
        return patients.add(patient);
    }
    
    // Search for a patient by ID
    public Patient searchPatient(String patientID) {
        for (Patient patient : patients) {
            if (patient.getPatientID().equals(patientID)) {
                return patient;
            }
        }
        return null;
    }
    
    // Update patient details
    public boolean updatePatient(String patientID, String firstName, String lastName,
                                 int age, String gender, String medicalCondition,
                                 PatientCatagory category) {
        Patient patient = searchPatient(patientID);
        if (patient == null) {
            return false;
        }
        
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setMedicalCondition(medicalCondition);
        patient.setCatagory(category);
        
        return true;
    }
    
    // Delete a patient
    public boolean deletePatient(String patientID) {
        Patient patient = searchPatient(patientID);
        if (patient == null) {
            return false;
        }
        return patients.remove(patient);
    }
    
    // Display all patients
    public void displayAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        
        System.out.println("\n========== ALL PATIENTS ==========");
        for (Patient patient : patients) {
            System.out.println("--------------------------------------");
            patient.displayDetails();
        }
        System.out.println("--------------------------------------");
        System.out.println("Total Patients: " + patients.size());
    }
    
    // Get total number of patients
    public int getTotalPatients() {
        return patients.size();
    }
}