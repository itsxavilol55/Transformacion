import java.lang.Thread;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.sql.SQLException;
import java.sql.Statement;

public class LeerTicketH extends Thread {
    private int aumento, inicio;
    private Hashtable<String, int[]> meses = new Hashtable<>();
    private Statement stmt;

    public LeerTicketH(Statement stmt, int inicio, int aumento) {
        this.aumento = aumento;
        this.inicio = inicio;
        this.stmt = stmt;
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
        try (CSVReader reader = new CSVReader(new FileReader("C:/datos/TicketH.csv"))) {
            List<String[]> datos = reader.readAll();
            String letras = "\\/([a-z]*)\\/", numeros = "([0-9]*)";
            int batchSize = 1000;
            for (int i = 1; i < datos.size(); i += batchSize) {
                StringBuilder values = new StringBuilder();
                for (int j = i + inicio; j < i + batchSize && j < datos.size(); j += aumento) {
                    StringBuilder nuevaLinea = new StringBuilder("(");
                    nuevaLinea.append(String.join(",", datos.get(j)));
                    nuevaLinea.replace(0, nuevaLinea.length(),
                            nuevaLinea.toString().replaceAll(" ", ""));
                    String mes = nuevaLinea.toString().replaceAll(".*" + letras + ".*", "$1");
                    int dia = Integer.parseInt(nuevaLinea.toString().replaceAll(".*," + numeros + "\\/.*", "$1"));
                    dia = Math.min(dia, meses.get(mes)[1]);
                    nuevaLinea.replace(0, nuevaLinea.length(),
                            nuevaLinea.toString().replaceAll(letras, "/" + meses.get(mes)[0] + "/"));
                    nuevaLinea.replace(0, nuevaLinea.length(),
                            nuevaLinea.toString().replaceAll(numeros + "\\/" + numeros + "\\/" + numeros,
                                    " '$2/" + dia + "/$3' "));
                    nuevaLinea.append("),");
                    values.append(nuevaLinea);
                }
                values.deleteCharAt(values.length() - 1);
                stmt.executeUpdate("INSERT INTO ventas values " + values.toString());
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
        System.out.println("se inserto correctamente en tabla: ventas");
    }
}