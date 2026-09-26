/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mathu
 */
public class BedManagement {

    private String[][] beds;
    private Patient[][] bedPatients;

    public BedManagement() {

        beds = new String[4][5];
        bedPatients = new Patient[4][5];

        int bedNumber = 1;

        for (int row = 0; row < 4; row++) {

            for (int col = 0; col < 5; col++) {

                beds[row][col] = String.format("B%02d", bedNumber);
                bedPatients[row][col] = null;

                bedNumber++;
            }
        }
    }

    // Find a bed using its bed ID
    private int[] findBed(String bedID) {

        for (int row = 0; row < 4; row++) {

            for (int col = 0; col < 5; col++) {

                if (beds[row][col].equalsIgnoreCase(bedID)) {

                    return new int[]{row, col};
                }
            }
        }

        return null;
    }

    // Allocate a bed to an inpatient
    public boolean allocateBed(String bedID, Patient patient) {

        // Only inpatients can receive beds
        if (patient.getCatagory() != PatientCatagory.INPATIENT) {
            return false;
        }

        int[] position = findBed(bedID);

        if (position == null) {
            return false;
        }

        int row = position[0];
        int col = position[1];

        // Check if bed is already occupied
        if (bedPatients[row][col] != null) {
            return false;
        }

        bedPatients[row][col] = patient;

        return true;
    }

    // Release a bed
    public boolean releaseBed(String bedID) {

        int[] position = findBed(bedID);

        if (position == null) {
            return false;
        }

        int row = position[0];
        int col = position[1];

        // Check if bed is already empty
        if (bedPatients[row][col] == null) {
            return false;
        }

        bedPatients[row][col] = null;

        return true;
    }

    // Display complete ward layout
    public void displayWardLayout() {

        System.out.println("\n========== WARD LAYOUT ==========");

        for (int row = 0; row < 4; row++) {

            for (int col = 0; col < 5; col++) {

                if (bedPatients[row][col] == null) {

                    System.out.print(
                            "[" + beds[row][col] + " Available] ");

                } else {

                    System.out.print(
                            "[" + beds[row][col] + " Occupied] ");
                }
            }

            System.out.println();
        }

        System.out.println("=================================");
    }

    // Display available beds
    public void displayAvailableBeds() {

        boolean found = false;

        System.out.println("\n========== AVAILABLE BEDS ==========");

        for (int row = 0; row < 4; row++) {

            for (int col = 0; col < 5; col++) {

                if (bedPatients[row][col] == null) {

                    System.out.println(beds[row][col]);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No beds are currently available.");
        }

        System.out.println("====================================");
    }

    // Display occupied beds
    public void displayOccupiedBeds() {

        boolean found = false;

        System.out.println("\n========== OCCUPIED BEDS ==========");

        for (int row = 0; row < 4; row++) {

            for (int col = 0; col < 5; col++) {

                if (bedPatients[row][col] != null) {

                    Patient patient = bedPatients[row][col];

                    System.out.println(
                            beds[row][col]
                            + " - "
                            + patient.getPatientID()
                            + " - "
                            + patient.getFirstName()
                            + " "
                            + patient.getLastName()
                    );

                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No beds are currently occupied.");
        }

        System.out.println("====================================");
    }

    // Check whether patient already has a bed
    public boolean patientHasBed(String patientID) {

        for (int row = 0; row < 4; row++) {

            for (int col = 0; col < 5; col++) {

                if (bedPatients[row][col] != null
                        && bedPatients[row][col]
                                .getPatientID()
                                .equalsIgnoreCase(patientID)) {

                    return true;
                }
            }
        }

        return false;
    }

    // Return total occupied beds
    public int getOccupiedBedCount() {

        int count = 0;

        for (int row = 0; row < 4; row++) {

            for (int col = 0; col < 5; col++) {

                if (bedPatients[row][col] != null) {
                    count++;
                }
            }
        }

        return count;
    }

    // Return total available beds
    public int getAvailableBedCount() {

        return 20 - getOccupiedBedCount();
    }

    // Calculate occupancy percentage
    public double getOccupancyPercentage() {

        return (getOccupiedBedCount() / 20.0) * 100;
    }
}