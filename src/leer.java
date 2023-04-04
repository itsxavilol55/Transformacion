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
    private String tabla;

    public leer(String archivo, int inicio, int aumento, String tabla) {
        archivoCSV = "C:/datos/" + archivo;
        this.aumento = aumento;
        this.inicio = inicio;
        this.tabla = tabla;
    }

    public void run() {
        try (CSVReader reader = new CSVReader(new FileReader(archivoCSV))) {
            Connection con = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost:1433;encrypt=true;databaseName=almacen;TrustServerCertificate=true;",
                    "Javi", "javi54321");
            Statement stmt = con.createStatement();
            List<String[]> datos = reader.readAll();
            for (int i = inicio + 1; i <= datos.size(); i += aumento) {
                if (i == datos.size())
                    continue;
                String nuevalinea = String.join(",", datos.get(i));
                nuevalinea = nuevalinea.replaceAll("([0-9]*\\/[a-z]*\\/[0-9]*)", " '$1' ");
                nuevalinea = nuevalinea.replaceAll(" ", "");
                System.out.println(nuevalinea);
                stmt.executeUpdate("INSERT INTO " + tabla + " values (" + nuevalinea + ")");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        System.out.println("se inserto correctamente");
    }
}
