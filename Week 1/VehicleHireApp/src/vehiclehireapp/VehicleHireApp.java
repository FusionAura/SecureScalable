/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehiclehireapp;

import au.edu.swin.vehicle.Vehicle;
import au.edu.swin.vehicle.VehicleType;
import java.util.ArrayList;
//https://www.tutorialkart.com/java/read-a-string-from-console-input-in-java/
import java.util.Scanner;

public class VehicleHireApp {

    public static void main(String[] args) {
        Boolean running = true;

        // Create the vehicle types
        VehicleType sedan = new VehicleType("SEDAN", "A standard sedan", 4);
        VehicleType limo6 = new VehicleType("LIMO6", "A six seater limo", 6);
        VehicleType limo8 = new VehicleType("LIMO8", "An eight seater limo", 8);
        // Create the vehicles
        ArrayList<Vehicle> vehicles = new ArrayList();
        vehicles.add(new Vehicle("Ed's Holden Caprice", "Silver", sedan, 2002));
        vehicles.add(new Vehicle("John's Mercedes C200", "Black", sedan, 2005));
        vehicles.add(new Vehicle("Guy's Volvo 244 DL", "Blue", sedan, 1976));
        vehicles.add(new Vehicle("Sasco's Ford Limo", "White", limo6, 2014));
        vehicles.add(new Vehicle("Peter's Ford Limo", "White", limo6, 2004));
        vehicles.add(new Vehicle("Robert's Ford Limo", "White", limo8, 2003));
        System.out.println("\n\nList of vehicles in system:");
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
        while (running) {
            String typeCode = "";

            System.out.println("\nIt will display a list of vehicles based on the vehicle type you choose"
                    + "\n1: SEDAN"
                    + "\n2: LIMO6"
                    + "\n3: LIMO8"
                    + "\n4: Exit");
            System.out.println("\n\nPlease Select an option (1-4):");
            Scanner scanner = new Scanner(System.in);
            String select = scanner.nextLine();
            switch (select.toString()) {
                case "1": {
                    typeCode = "SEDAN";
                    break;
                }
                case "2": {
                    typeCode = "LIMO6";
                    break;
                }
                case "3": {
                    typeCode = "LIMO8";
                    break;
                }
                case "4": {
                    running = false;
                    break;
                }
                default: {
                    typeCode = "SEDAN";
                    break;
                }
            }

            //String typeCode = args[0];
            System.out.println("\n\nList of vehicle of type " + typeCode);

            for (Vehicle vehicle : vehicles) {
                if (vehicle.getType().getCode().equals(typeCode.toUpperCase())) {
                    System.out.println(vehicle);
                }
            }
        }
    }
}
