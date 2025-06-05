import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new DBconnect(); // Laster inn DB.properties og gjør klar tilkobling til databasen

        try (Connection conn = DBconnect.getConnection()) {

            // Importerer data fra .txt-fil til databasen (del 1)
            File file = new File("resources/vehicles.txt");
            dataParser.parseFile(file, conn);
            System.out.println("Import fullført!");

            // Oppretter scanner for brukermeny
            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                // Viser menyvalg
                System.out.println("\n===== MENY =====");
                System.out.println("1. Vis alle kjøretøy");
                System.out.println("2. Total mengde drivstoff i fossilbiler");
                System.out.println("3. Vis alle kjøretøy som er kjørbare");
                System.out.println("4. Skrot et kjøretøy (valgfri funksjon)");
                System.out.println("5. Avslutt");
                System.out.print("Velg: ");

                String valg = scanner.nextLine().trim(); // Leser brukerens valg

                switch (valg) {
                    case "1" -> {
                        // Henter og skriver ut alle kjøretøy fra databasen
                        List<Vehicle> vehicles = VehicleRepository.getAllVehicles(conn);
                        for (Vehicle v : vehicles) {
                            System.out.println(v);
                        }
                    }
                    case "2" -> {
                        // Summerer total drivstoffmengde i FossilCar-tabellen
                        int total = VehicleRepository.getTotalFuelAmount(conn);
                        System.out.println("Total drivstoffmengde i fossilbiler: " + total + " liter");
                    }
                    case "3" -> {
                        // Viser kun kjøretøy som er kjørbare
                        List<Vehicle> vehicles = VehicleRepository.getDriveableVehicles(conn);
                        for (Vehicle v : vehicles) {
                            System.out.println(v);
                        }
                    }
                    case "4" -> {
                        // Lar brukeren slette (skrote) et kjøretøy ved å angi VehicleID
                        System.out.print("Skriv inn VehicleID for kjøretøyet du vil skrote (slette): ");
                        int id = scanner.nextInt();
                        scanner.nextLine(); // Fanger opp linjeskift etter int-input
                        boolean success = VehicleRepository.scrapVehicleById(conn, id);
                        if (success) {
                            System.out.println("Kjøretøy med ID " + id + " ble skrotet.");
                        } else {
                            System.out.println("Ingen kjøretøy funnet med ID " + id + ".");
                        }
                    }
                    case "5" -> {
                        // Avslutter programmet
                        running = false;
                        System.out.println("Avslutter programmet...");
                    }
                    default -> {
                        // Feilhåndtering for ugyldig valg
                        System.out.println("Ugyldig valg, prøv igjen.");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
