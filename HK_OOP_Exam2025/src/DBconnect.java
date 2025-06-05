
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class DBconnect {
    private static String propfile = "resources/DB.properties";
    private static Properties PROPERTIES = new Properties();
    // Viser til hva som er nødvendige info for inn logging
    private static Set<String> requiredProps = new HashSet<>(Set.of("db.url", "db.username", "db.password"));

    public DBconnect() {
        Path propPath = Paths.get(propfile);
        if (!Files.exists(Path.of(propfile))) {
            System.out.println("Feil: Filen er ikke funnet! " + propPath);
            return;
        } else if (!Files.isReadable(Path.of(propfile))) {
            System.out.println("Feil: Filen er ikke lesbar! !" + propPath);
        }

        try (BufferedReader bf = Files.newBufferedReader(Paths.get(propfile))){
            PROPERTIES.load(bf);
            if (!PROPERTIES.keySet().containsAll(requiredProps)) {
                requiredProps.removeAll(PROPERTIES.keySet());
                System.out.println("Mangler database opplysninger " + requiredProps);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hendter inn logging info til databasen fra properties filen
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PROPERTIES.getProperty("db.url"),
                PROPERTIES.getProperty("db.username"),
                PROPERTIES.getProperty("db.password")
        );
    }
}
