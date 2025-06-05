import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class dataParser {

    public static void parseFile(File file, Connection conn) throws FileNotFoundException, SQLException {
        try (Scanner scanner = new Scanner(file)) {

            int scrapyardCount = Integer.parseInt(scanner.nextLine().trim());
            for (int i = 0; i < scrapyardCount; i++) {
                int id = Integer.parseInt(scanner.nextLine().trim());
                String name = scanner.nextLine().trim();
                String address = scanner.nextLine().trim();
                String phone = scanner.nextLine().trim();

                try (PreparedStatement ps = conn.prepareStatement(
                        "INSERT IGNORE INTO Scrapyard (ScrapyardID, Name, Address, PhoneNumber) " +
                                "VALUES (?, ?, ?, ?)")) {
                    ps.setInt(1, id);
                    ps.setString(2, name);
                    ps.setString(3, address);
                    ps.setString(4, phone);
                    ps.executeUpdate();
                }
                if (scanner.hasNextLine()) scanner.nextLine(); // Hopper over "----"
            }

            // Sammler felles verdiene for alle tabellene
            int vehicleCount = Integer.parseInt(scanner.nextLine().trim());
            for (int i = 0; i < vehicleCount; i++) {
                int vehicleId = Integer.parseInt(scanner.nextLine().trim());
                int scrapyardId = Integer.parseInt(scanner.nextLine().trim());
                String type = scanner.nextLine().trim();
                String brand = scanner.nextLine().trim();
                String model = scanner.nextLine().trim();
                int year = Integer.parseInt(scanner.nextLine().trim());
                String regNum = scanner.nextLine().trim();
                String chassis = scanner.nextLine().trim();
                boolean driveable = Boolean.parseBoolean(scanner.nextLine().trim());
                int wheelsForSale = Integer.parseInt(scanner.nextLine().trim());


                /* Lager en switch staement slik programet kan sortere ut de individuelle verdiene til
                   den tabellen den hører til. */
                switch (type) {
                    case "FossilCar" -> {
                        String fuelType = scanner.nextLine().trim();
                        int fuelAmount = Integer.parseInt(scanner.nextLine().trim());
                        /*
                         INSERT IGNORE brukes for å unngå at programmet krasjer hvis det oppstår en feil,
                         som for eksempel brudd på unike eller fremmednøkkel-regler i databasen.
                         I stedet for å kaste en feil, vil raden bare ikke bli lagt til.
                        */
                        try (PreparedStatement ps = conn.prepareStatement(
                                "INSERT IGNORE INTO FossilCar (VehicleID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, " +
                                        "ScrapyardID, FuelType, FuelAmount) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                            ps.setInt(1, vehicleId);
                            ps.setString(2, brand);
                            ps.setString(3, model);
                            ps.setInt(4, year);
                            ps.setString(5, regNum);
                            ps.setString(6, chassis);
                            ps.setBoolean(7, driveable);
                            ps.setInt(8, wheelsForSale);
                            ps.setInt(9, scrapyardId);
                            ps.setString(10, fuelType);
                            ps.setInt(11, fuelAmount);
                            ps.executeUpdate();
                        }
                    }
                    case "ElectricCar" -> {
                        int batteryCapacity = Integer.parseInt(scanner.nextLine().trim());
                        int chargingLevel = Integer.parseInt(scanner.nextLine().trim());

                        try (PreparedStatement ps = conn.prepareStatement(
                                "INSERT IGNORE INTO ElectricCar (VehicleID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, " +
                                        "ScrapyardID, BatteryCapacity, ChargeLevel) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                            ps.setInt(1, vehicleId);
                            ps.setString(2, brand);
                            ps.setString(3, model);
                            ps.setInt(4, year);
                            ps.setString(5, regNum);
                            ps.setString(6, chassis);
                            ps.setBoolean(7, driveable);
                            ps.setInt(8, wheelsForSale);
                            ps.setInt(9, scrapyardId);
                            ps.setInt(10, batteryCapacity);
                            ps.setInt(11, chargingLevel);
                            ps.executeUpdate();
                        }
                    }
                    case "Motorcycle" -> {
                        // Legger til de individuelle verdiene for denne tabellen
                        boolean hasSidecar = Boolean.parseBoolean(scanner.nextLine().trim());
                        int engineCapacity = Integer.parseInt(scanner.nextLine().trim());
                        boolean isModified = Boolean.parseBoolean(scanner.nextLine().trim());
                        int numberOfWheels = Integer.parseInt(scanner.nextLine().trim());

                        try (PreparedStatement ps = conn.prepareStatement(
                                "INSERT IGNORE INTO Motorcycle (VehicleID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, " +
                                        "ScrapyardID, HasSidecar, EngineCapacity, IsModified, NumberOfWheels) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                            ps.setInt(1, vehicleId);
                            ps.setString(2, brand);
                            ps.setString(3, model);
                            ps.setInt(4, year);
                            ps.setString(5, regNum);
                            ps.setString(6, chassis);
                            ps.setBoolean(7, driveable);
                            ps.setInt(8, wheelsForSale);
                            ps.setInt(9, scrapyardId);
                            ps.setBoolean(10, hasSidecar);
                            ps.setInt(11, engineCapacity);
                            ps.setBoolean(12, isModified);
                            ps.setInt(13, numberOfWheels);
                            ps.executeUpdate();
                        }
                    }
                    default -> System.out.println("Ukjent kjøretøystype: " + type);
                }
                if (scanner.hasNextLine()) scanner.nextLine(); // Hopp over "----"
            }
            System.out.println("Ferdig med å lese og importere data!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
