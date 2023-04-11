import java.lang.Thread;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.sql.SQLException;
import java.sql.Statement;

public class LeerTicketD extends Thread {
    private int aumento, inicio;
    private Statement stmt;

    public LeerTicketD(Statement stmt, int inicio, int aumento) {
        this.aumento = aumento;
        this.inicio = inicio;
        this.stmt = stmt;
    }

    public void run() {
        try (CSVReader reader = new CSVReader(new FileReader("C:/datos/TicketD.csv"))) {
            List<String[]> datos = reader.readAll();
            int batchSize = 1000;
            for (int i = 1; i < datos.size(); i += batchSize) {
                StringBuilder values = new StringBuilder();
                for (int j = i + inicio; j < i + batchSize && j < datos.size(); j += aumento) {
                    StringBuilder nuevaLinea = new StringBuilder("(");
                    nuevaLinea.append(String.join(",", datos.get(j)));
                    replaces.removeSpaces(nuevaLinea);
                    nuevaLinea.append("),");
                    values.append(nuevaLinea);
                }
                values.deleteCharAt(values.length() - 1);
                stmt.executeUpdate("INSERT INTO detalleventas values " + values.toString());
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
        System.out.println("se inserto correctamente en tabla: detalleventas");
    }
}