/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mathu
 */
import java.util.Scanner;

public class HospitalMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        PatientManagement patientManagement = new PatientManagement();
        BedManagement bedManagement = new BedManagement();

        int choice;

        System.out.println("======================================");
        System.out.println("   MEDICARE HOSPITAL PATIENT SYSTEM");
        System.out.println("======================================");
        
        do {

            System.out.println("\n========== Patient Management ==========");
            System.out.println("1. Register Patient");
            System.out.println("2. Search Patient");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Display All Patients");
            System.out.println("6. Allocate Bed");
            System.out.println("7. Release Bed");
            System.out.println("8. Display Ward Layout");
            System.out.println("9. Display Available Beds");
            System.out.println("10. Display Occupied Beds");
            System.out.println("11. Generate Reports");
            System.out.println("0. Exit");
            System.out.println("===============================");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    registerPatient(scanner, patientManagement);
                    break;

                case 2:
                    searchPatient(scanner, patientManagement);
                    break;

                case 3:
                    updatePatient(scanner, patientManagement);
                    break;

                case 4:
                    deletePatient(scanner, patientManagement);
                    break;

                case 5:
                    patientManagement.displayAllPatients();
                    break;

                case 6:
                    allocateBed(scanner, patientManagement, bedManagement);
                    break;

                case 7:
                    releaseBed(scanner, bedManagement);
                    break;

                case 8:
                    bedManagement.displayWardLayout();
                    break;

                case 9:
                    bedManagement.displayAvailableBeds();
                    break;

                case 10:
                    bedManagement.displayOccupiedBeds();
                    break;

                case 11:
                    generateReports(patientManagement, bedManagement);
                    break;

                case 0:
                    System.out.println("\nThank you for using MediCare Hospital System.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);

        scanner.close();
    }

    // ==========================================
    // REGISTER PATIENT
    // ==========================================

    public static void registerPatient(
            Scanner scanner,
            PatientManagement patientManagement) {

        System.out.println("\n========== REGISTER PATIENT ==========");

        System.out.print("Enter Patient ID: ");
        String patientID = scanner.nextLine();

        if (patientManagement.searchPatient(patientID) != null) {

            System.out.println("Patient ID already exists.");
            return;
        }

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Enter Medical Condition: ");
        String medicalCondition = scanner.nextLine();

        PatientCatagory category = getCategory(scanner);

        Patient patient = new Patient(
                patientID,
                firstName,
                lastName,
                age,
                gender,
                medicalCondition,
                category
        );

        if (patientManagement.registerPatient(patient)) {

            System.out.println("Patient registered successfully.");

        } else {

            System.out.println("Patient could not be registered.");
        }
    }

    // ==========================================
    // GET PATIENT CATEGORY
    // ==========================================

    public static PatientCatagory getCategory(Scanner scanner) {

        System.out.println("\nSelect Patient Category:");
        System.out.println("1. Inpatient");
        System.out.println("2. Outpatient");
        System.out.println("3. Emergency");
        System.out.print("Enter category: ");

        int categoryChoice = scanner.nextInt();
        scanner.nextLine();

        switch (categoryChoice) {

            case 1:
                return PatientCatagory.INPATIENT;

            case 2:
                return PatientCatagory.OUTPATIENT;

            case 3:
                return PatientCatagory.EMERGENCY;

            default:
                System.out.println("Invalid category. Outpatient selected.");
                return PatientCatagory.OUTPATIENT;
        }
    }

    // ==========================================
    // SEARCH PATIENT
    // ==========================================

   public static void searchPatient(
        Scanner scanner,
        PatientManagement patientManagement) {
    System.out.println("\n========== SEARCH PATIENT ==========");
    System.out.print("Enter Patient ID: ");
    String patientID = scanner.nextLine();
    Patient patient = patientManagement.searchPatient(patientID);
    if (patient != null) {
        System.out.println("\nPatient Found:");
        System.out.println("--------------------------------------");
        patient.displayDetails();
        System.out.println("--------------------------------------");
    } else {
        System.out.println("Patient not found.");
    }
}
    // ==========================================
    // UPDATE PATIENT
    // ==========================================

    public static void updatePatient(
            Scanner scanner,
            PatientManagement patientManagement) {

        System.out.println("\n========== UPDATE PATIENT ==========");

        System.out.print("Enter Patient ID: ");
        String patientID = scanner.nextLine();

        Patient patient = patientManagement.searchPatient(patientID);

        if (patient == null) {

            System.out.println("Patient not found.");
            return;
        }

        System.out.print("Enter New First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter New Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter New Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter New Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Enter New Medical Condition: ");
        String medicalCondition = scanner.nextLine();

        PatientCatagory category = getCategory(scanner);

        boolean updated = patientManagement.updatePatient(
                patientID,
                firstName,
                lastName,
                age,
                gender,
                medicalCondition,
                category
        );

        if (updated) {

            System.out.println("Patient updated successfully.");

        } else {

            System.out.println("Patient could not be updated.");
        }
    }

    // ==========================================
    // DELETE PATIENT
    // ==========================================

    public static void deletePatient(
            Scanner scanner,
            PatientManagement patientManagement) {

        System.out.println("\n========== DELETE PATIENT ==========");

        System.out.print("Enter Patient ID: ");
        String patientID = scanner.nextLine();

        if (patientManagement.deletePatient(patientID)) {

            System.out.println("Patient deleted successfully.");

        } else {

            System.out.println("Patient not found.");
        }
    }

    // ==========================================
    // ALLOCATE BED
    // ==========================================

    public static void allocateBed(
            Scanner scanner,
            PatientManagement patientManagement,
            BedManagement bedManagement) {

        System.out.println("\n========== BED MANAGEMENT ==========");

        System.out.print("Enter Patient ID: ");
        String patientID = scanner.nextLine();

        Patient patient = patientManagement.searchPatient(patientID);

        if (patient == null) {

            System.out.println("Patient not found.");
            return;
        }

        // Only inpatients can receive beds
        if (patient.getCatagory() != PatientCatagory.INPATIENT) {

            System.out.println(
                    "Bed allocation denied. Only Inpatients may be allocated a bed."
            );

            return;
        }

        // Check whether patient already has a bed
        if (bedManagement.patientHasBed(patientID)) {

            System.out.println("This patient already has a bed.");
            return;
        }

        // Check whether beds are available
        if (bedManagement.getAvailableBedCount() == 0) {

            System.out.println("No beds are available.");
            return;
        }

        System.out.print("Enter Bed ID (e.g. B01): ");
        String bedID = scanner.nextLine();

        if (bedManagement.allocateBed(bedID, patient)) {

            System.out.println(
                    "Bed " + bedID + " successfully allocated to "
                    + patient.getFirstName() + " "
                    + patient.getLastName()
            );

        } else {

            System.out.println(
                    "Bed allocation failed. Check that the bed exists "
                    + "and is available."
            );
        }
    }

    // ==========================================
    // RELEASE BED
    // ==========================================

    public static void releaseBed(
            Scanner scanner,
            BedManagement bedManagement) {

        System.out.println("\n========== RELEASE BED ==========");

        System.out.print("Enter Bed ID: ");
        String bedID = scanner.nextLine();

        if (bedManagement.releaseBed(bedID)) {

            System.out.println("Bed " + bedID + " has been released.");

        } else {

            System.out.println(
                    "Bed could not be released. "
                    + "Check that the bed exists and is occupied."
            );
        }
    }

    // ==========================================
    // REPORTS
    // ==========================================

    public static void generateReports(
            PatientManagement patientManagement,
            BedManagement bedManagement) {

        System.out.println("\n======================================");
        System.out.println("          MEDICARE REPORTS");
        System.out.println("======================================");

        // All registered patients
        System.out.println("\n1. ALL REGISTERED PATIENTS");
        patientManagement.displayAllPatients();

        // Available beds
        System.out.println("\n2. ALL AVAILABLE BEDS");
        bedManagement.displayAvailableBeds();

        // Occupied beds
        System.out.println("\n3. ALL OCCUPIED BEDS");
        bedManagement.displayOccupiedBeds();

        // Total registered patients
        System.out.println("\n4. TOTAL REGISTERED PATIENTS");
        System.out.println(
                patientManagement.getTotalPatients()
        );

        // Total occupied beds
        System.out.println("\n5. TOTAL OCCUPIED BEDS");
        System.out.println(
                bedManagement.getOccupiedBedCount()
        );

        // Bed occupancy percentage
        System.out.println("\n6. BED OCCUPANCY PERCENTAGE");

        System.out.printf(
                "%.2f%%%n",
                bedManagement.getOccupancyPercentage()
        );

        System.out.println("======================================");
    }
}

