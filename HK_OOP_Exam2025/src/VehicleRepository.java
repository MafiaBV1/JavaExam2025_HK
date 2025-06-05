import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleRepository {

    public static List<Vehicle> getAllVehicles(Connection conn) throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        // Hent FossilCars
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM FossilCar")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vehicles.add(new FossilCar(
                        rs.getInt("VehicleID"),
                        rs.getString("Brand"),
                        rs.getString("Model"),
                        rs.getInt("YearModel"),
                        rs.getString("RegistrationNumber"),
                        rs.getString("ChassisNumber"),
                        rs.getBoolean("Driveable"),
                        rs.getInt("NumberOfSellableWheels"),
                        rs.getInt("ScrapyardID"),
                        rs.getString("FuelType"),
                        rs.getInt("FuelAmount")
                ));
            }
        }

        // Hent ElectricCars
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM ElectricCar")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vehicles.add(new ElectricCar(
                        rs.getInt("VehicleID"),
                        rs.getString("Brand"),
                        rs.getString("Model"),
                        rs.getInt("YearModel"),
                        rs.getString("RegistrationNumber"),
                        rs.getString("ChassisNumber"),
                        rs.getBoolean("Driveable"),
                        rs.getInt("NumberOfSellableWheels"),
                        rs.getInt("ScrapyardID"),
                        rs.getInt("BatteryCapacity"),
                        rs.getInt("ChargeLevel")
                ));
            }
        }

        // Hent Motorcycles
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Motorcycle")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vehicles.add(new Motorcycle(
                        rs.getInt("VehicleID"),
                        rs.getString("Brand"),
                        rs.getString("Model"),
                        rs.getInt("YearModel"),
                        rs.getString("RegistrationNumber"),
                        rs.getString("ChassisNumber"),
                        rs.getBoolean("Driveable"),
                        rs.getInt("NumberOfSellableWheels"),
                        rs.getInt("ScrapyardID"),
                        rs.getBoolean("HasSidecar"),
                        rs.getInt("EngineCapacity"),
                        rs.getBoolean("IsModified"),
                        rs.getInt("NumberOfWheels")
                ));
            }
        }

        return vehicles;
    }

    public static int getTotalFuelAmount(Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT SUM(FuelAmount) AS total FROM FossilCar")) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    public static List<Vehicle> getDriveableVehicles(Connection conn) throws SQLException {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : getAllVehicles(conn)) {
            if (v.driveable) result.add(v);
        }
        return result;
    }


    public static boolean scrapVehicleById(Connection conn, int id) {
        String[] tables = {"FossilCar", "ElectricCar", "Motorcycle"};
        try {
            for (String table : tables) {
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM " + table + " WHERE VehicleID = ?")) {
                    ps.setInt(1, id);
                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        return true; // Slettet fra en tabell
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Feil ved sletting: " + e.getMessage());
        }
        return false; // Ikke funnet
    }

}

