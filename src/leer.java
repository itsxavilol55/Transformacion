import java.lang.Thread;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class leer extends Thread {
    private String archivoCSV;
    private int aumento;
    private int inicio;
    private String tabla;
    private Hashtable<String, int[]> meses = new Hashtable<>();

    public leer(String archivo, int inicio, int aumento, String tabla) {
        archivoCSV = "C:/datos/" + archivo;
        this.aumento = aumento;
        this.inicio = inicio;
        this.tabla = tabla;
        meses.put("ene", new int[] { 1, 31 });
        meses.put("feb", new int[] { 2, 28 });
        meses.put("mar", new int[] { 3, 31 });
        meses.put("abr", new int[] { 4, 30 });
        meses.put("may", new int[] { 5, 31 });
        meses.put("jun", new int[] { 6, 30 });
        meses.put("jul", new int[] { 7, 31 });
        meses.put("ago", new int[] { 8, 31 });
        meses.put("sep", new int[] { 9, 30 });
        meses.put("oct", new int[] { 10, 31 });
        meses.put("nov", new int[] { 11, 30 });
        meses.put("dic", new int[] { 12, 31 });
    }

    public void run() {
        try (CSVReader reader = new CSVReader(new FileReader(archivoCSV))) {
            Connection con = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost:1433;encrypt=true;databaseName=almacen;TrustServerCertificate=true;",
                    "Javi", "javi54321");
            Statement stmt = con.createStatement();
            List<String[]> datos = reader.readAll();
            String mes;
            int dia = 0;
            String letras = "\\/([a-z]*)\\/", numeros = "([0-9]*)";
            for (int i = inicio + 1; i < datos.size(); i += aumento) {
                StringBuilder nuevalinea = new StringBuilder(String.join(",", datos.get(i)));
                nuevalinea.replace(0, nuevalinea.length(),
                        nuevalinea.toString().replaceAll(" ", ""));
                mes = nuevalinea.toString().replaceAll(".*" + letras + ".*", "$1");
                dia = Integer.parseInt(nuevalinea.toString().replaceAll(".*," + numeros + "\\/.*", "$1"));
                dia = Math.min(dia, meses.get(mes)[1]);
                nuevalinea.replace(0, nuevalinea.length(),
                        nuevalinea.toString().replaceAll(letras, "/" + meses.get(mes)[0] + "/"));
                nuevalinea.replace(0, nuevalinea.length(),
                        nuevalinea.toString().replaceAll(numeros + "\\/" + numeros + "\\/" + numeros,
                                " '$2/" + dia + "/$3' "));
                stmt.executeUpdate("INSERT INTO " + tabla + " values (" + nuevalinea.toString() + ")");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (CsvValidationException e) {
            e.printStackTrace();
            return;
        } catch (CsvException e) {
            e.printStackTrace();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("se inserto correctamente en tabla: " + tabla);
    }
}
