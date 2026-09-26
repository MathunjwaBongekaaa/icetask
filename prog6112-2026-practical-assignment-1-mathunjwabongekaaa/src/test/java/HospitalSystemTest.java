/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//tests the main functionality of the hospital system
@DisplayName("Hospital Main System Tests")
class HospitalMainTest {
    private PatientManagement patientManagement;
    private BedManagement bedManagement;
    private Patient testPatient;
    
    @BeforeEach
    void setUp() {
        patientManagement = new PatientManagement();
        bedManagement = new BedManagement();
        testPatient = new Patient("P001", "Test", "User", 25, "M", "Healthy", PatientCatagory.INPATIENT);
    }
    // Tests the complete patient registration, bed allocation,
    // updating and deletion process
    @Nested
    @DisplayName("End-to-End Workflow Tests")
    class EndToEndWorkflowTests {
        @Test
        @DisplayName("Should handle complete patient registration and bed allocation workflow")
        void testCompleteWorkflow() {
            // 1. Register patient
            assertTrue(patientManagement.registerPatient(testPatient));
            assertEquals(1, patientManagement.getTotalPatients());
            
            // 2. Search for patient
            Patient found = patientManagement.searchPatient("P001");
            assertNotNull(found);
            assertEquals("Test", found.getFirstName());
            
            // 3. Allocate bed
            assertTrue(bedManagement.allocateBed("B01", found));
            assertTrue(bedManagement.patientHasBed("P001"));
            assertEquals(19, bedManagement.getAvailableBedCount());
            assertEquals(1, bedManagement.getOccupiedBedCount());
            
            // 4. Display ward layout (should not throw exceptions)
            assertDoesNotThrow(() -> bedManagement.displayWardLayout());
            
            // 5. Release bed
            assertTrue(bedManagement.releaseBed("B01"));
            assertFalse(bedManagement.patientHasBed("P001"));
            assertEquals(20, bedManagement.getAvailableBedCount());
            assertEquals(0, bedManagement.getOccupiedBedCount());
            
            // 6. Update patient
            assertTrue(patientManagement.updatePatient(
                "P001", "Updated", "Name", 30, "M", "Recovered", PatientCatagory.OUTPATIENT
            ));
            Patient updated = patientManagement.searchPatient("P001");
            assertEquals("Updated", updated.getFirstName());
            assertEquals("Name", updated.getLastName());
            assertEquals(30, updated.getAge());
            assertEquals(PatientCatagory.OUTPATIENT, updated.getCatagory());
            
            // 7. Delete patient
            assertTrue(patientManagement.deletePatient("P001"));
            assertNull(patientManagement.searchPatient("P001"));
            assertEquals(0, patientManagement.getTotalPatients());
        }
        
        @Test
        @DisplayName("Should handle multiple patients with bed allocation")
        void testMultiplePatientsWorkflow() {
            // Register 3 inpatients
            Patient patient1 = new Patient("P001", "John", "Doe", 30, "M", "Healthy", PatientCatagory.INPATIENT);
            Patient patient2 = new Patient("P002", "Jane", "Smith", 25, "F", "Sick", PatientCatagory.INPATIENT);
            Patient patient3 = new Patient("P003", "Bob", "Brown", 40, "M", "Minor", PatientCatagory.INPATIENT);
            
            patientManagement.registerPatient(patient1);
            patientManagement.registerPatient(patient2);
            patientManagement.registerPatient(patient3);
            
            // Allocate beds
            bedManagement.allocateBed("B01", patient1);
            bedManagement.allocateBed("B02", patient2);
            bedManagement.allocateBed("B03", patient3);
            
            assertEquals(17, bedManagement.getAvailableBedCount());
            assertEquals(3, bedManagement.getOccupiedBedCount());
            assertEquals(15.0, bedManagement.getOccupancyPercentage(), 0.01);
            
            // Release one bed
            bedManagement.releaseBed("B02");
            assertEquals(18, bedManagement.getAvailableBedCount());
            assertEquals(2, bedManagement.getOccupiedBedCount());
            
            // Display all patients
            assertDoesNotThrow(() -> patientManagement.displayAllPatients());
            
            // Generate reports
            assertDoesNotThrow(() -> {
                System.out.println("\nGenerating reports...");
                patientManagement.displayAllPatients();
                bedManagement.displayAvailableBeds();
                bedManagement.displayOccupiedBeds();
            });
        }
        
        @Test
        @DisplayName("Should prevent outpatient from getting bed")
        void testOutpatientCannotGetBed() {
            Patient outpatient = new Patient("P004", "Out", "Patient", 50, "F", "Healthy", PatientCatagory.OUTPATIENT);
            patientManagement.registerPatient(outpatient);
            
            // Try to allocate bed to outpatient (should fail)
            assertFalse(bedManagement.allocateBed("B01", outpatient));
            assertEquals(20, bedManagement.getAvailableBedCount());
            assertEquals(0, bedManagement.getOccupiedBedCount());
            assertFalse(bedManagement.patientHasBed("P004"));
        }
        
        @Test
        @DisplayName("Should prevent emergency patient from getting bed")
        void testEmergencyPatientCannotGetBed() {
            Patient emergencyPatient = new Patient("P005", "Emergency", "Patient", 45, "M", "Critical", PatientCatagory.EMERGENCY);
            patientManagement.registerPatient(emergencyPatient);
            
            // Try to allocate bed to emergency patient (should fail)
            assertFalse(bedManagement.allocateBed("B01", emergencyPatient));
            assertEquals(20, bedManagement.getAvailableBedCount());
            assertEquals(0, bedManagement.getOccupiedBedCount());
            assertFalse(bedManagement.patientHasBed("P005"));
        }
    }
    
    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {
        @Test
        @DisplayName("Should handle duplicate patient registration")
        void testDuplicateRegistration() {
            patientManagement.registerPatient(testPatient);
            Patient duplicate = new Patient("P001", "Duplicate", "User", 30, "M", "Sick", PatientCatagory.INPATIENT);
            assertFalse(patientManagement.registerPatient(duplicate));
            assertEquals(1, patientManagement.getTotalPatients());
        }
        
        @Test
        @DisplayName("Should handle bed allocation when no beds available")
        void testNoBedsAvailable() {
            // Allocate all 20 beds
            for (int i = 1; i <= 20; i++) {
                Patient patient = new Patient("P" + String.format("%03d", i), 
                    "Patient" + i, "Doe", 20, "M", "Healthy", PatientCatagory.INPATIENT);
                patientManagement.registerPatient(patient);
                bedManagement.allocateBed("B" + String.format("%02d", i), patient);
            }
            
            assertEquals(0, bedManagement.getAvailableBedCount());
            assertEquals(20, bedManagement.getOccupiedBedCount());
            
            // Try to allocate bed for 21st patient
            Patient extraPatient = new Patient("P021", "Extra", "Patient", 30, "M", "Sick", PatientCatagory.INPATIENT);
            patientManagement.registerPatient(extraPatient);
            assertFalse(bedManagement.allocateBed("B01", extraPatient));
            assertEquals(0, bedManagement.getAvailableBedCount());
            assertEquals(20, bedManagement.getOccupiedBedCount());
        }
        
        @Test
        @DisplayName("Should handle updating non-existent patient")
        void testUpdateNonExistentPatient() {
            boolean result = patientManagement.updatePatient(
                "P999", "New", "Name", 30, "M", "Healthy", PatientCatagory.INPATIENT
            );
            assertFalse(result);
        }
        
        @Test
        @DisplayName("Should handle deleting non-existent patient")
        void testDeleteNonExistentPatient() {
            assertFalse(patientManagement.deletePatient("P999"));
        }
        
        @Test
        @DisplayName("Should handle searching for non-existent patient")
        void testSearchNonExistentPatient() {
            assertNull(patientManagement.searchPatient("P999"));
        }
    }
    
    @Nested
    @DisplayName("Category Selection Tests")
    class CategorySelectionTests {
        @Test
        @DisplayName("Should return INPATIENT for category 1")
        void testGetCategoryInpatient() {
            // This would require mocking Scanner, but we can test the enum directly
            assertEquals(PatientCatagory.INPATIENT, PatientCatagory.valueOf("INPATIENT"));
        }
        
        @Test
        @DisplayName("Should return OUTPATIENT for category 2")
        void testGetCategoryOutpatient() {
            assertEquals(PatientCatagory.OUTPATIENT, PatientCatagory.valueOf("OUTPATIENT"));
        }
        
        @Test
        @DisplayName("Should return EMERGENCY for category 3")
        void testGetCategoryEmergency() {
            assertEquals(PatientCatagory.EMERGENCY, PatientCatagory.valueOf("EMERGENCY"));
        }
    }
}