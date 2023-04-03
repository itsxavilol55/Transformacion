import java.lang.Thread;
import java.lang.Runnable;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class leer extends Thread {
    private String archivoCSV;
    private int aumento;
    private int inicio;

    public leer(String archivo, int inicio, int aumento) {
        archivoCSV = "C:/datos/" + archivo;
        this.aumento = aumento;
        this.inicio = inicio;
    }

    public void run() {
        System.out.println("dasfdasdfsd");
        try (CSVReader reader = new CSVReader(new FileReader(archivoCSV))) {
            Connection con = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost:1433;encrypt=true;databaseName=almacen;TrustServerCertificate=true;",
                    "Javi", "javi54321");
            Statement stmt = con.createStatement();
            List<String[]> datos = reader.readAll();
            for (int i = inicio; i < datos.size(); i += aumento) {
                String[] linea = datos.get(i);
                stmt.executeQuery("INSERT INTO envios values (" + linea[0] + ")");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("no se pudo :P");
            e.printStackTrace();
        }
    }
}
