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

public class LeerSynergy extends Thread {
    private int aumento, inicio;
    private Statement stmt;
    private Hashtable<String, Double> conversionTable = new Hashtable<String, Double>();

    public LeerSynergy(Statement stmt, int inicio, int aumento) {
        this.aumento = aumento;
        this.inicio = inicio;
        this.stmt = stmt;
        conversionTable.put("Vietnam", 0.35);
        conversionTable.put("USA", 20.33);
        conversionTable.put("Italy", 1.23);
        conversionTable.put("Brazil", 3.68);
        conversionTable.put("Netherlands", 1.91);
        conversionTable.put("UnitedKingdom", 25.01);
        conversionTable.put("Russia", 0.27);
        conversionTable.put("Rusia", 0.27);
        conversionTable.put("Malaysia", 4.89);
        conversionTable.put("SouthKorea", 0.018);
        conversionTable.put("Germany", 14.66);
        conversionTable.put("Philippines", 0.42);
        conversionTable.put("Switzerland", 22.27);
        conversionTable.put("UnitedArabEmirates", 5.52);
        conversionTable.put("Mexico", 1.0);
        conversionTable.put("China", 3.13);
        conversionTable.put("Argentina", 0.21);
        conversionTable.put("Peru", 5.54);
        conversionTable.put("India", 0.27);
        conversionTable.put("Croatia", 3.27);
        conversionTable.put("Austria", 15.22);
        conversionTable.put("Poland", 5.27);
        conversionTable.put("Canada", 16.06);
        conversionTable.put("Ireland", 24.30);
        conversionTable.put("Slovakia", 2.15);
        conversionTable.put("Thailand", 0.63);
        conversionTable.put("France", 22.10);
        conversionTable.put("Belgium", 19.89);
        conversionTable.put("Japan", 0.18);
        conversionTable.put("Spain", 22.53);
        conversionTable.put("Turkey", 2.43);
        conversionTable.put("Singapore", 14.96);
        conversionTable.put("NewZealand", 14.48);
        conversionTable.put("Belorussia", 10.00);
    }

    public void run() {
        try (CSVReader reader = new CSVReader(new FileReader("C:/datos/synergy_logistics_database.csv"))) {
            List<String[]> datos = reader.readAll();
            int batchSize = 1000;
            for (int i = 1; i < datos.size(); i += batchSize) {
                StringBuilder values = new StringBuilder();
                for (int j = i + inicio; j < i + batchSize && j < datos.size(); j += aumento) {
                    StringBuilder nuevaLinea = new StringBuilder();
                    nuevaLinea.append("(");
                    nuevaLinea.append(String.join(",", datos.get(j)));
                    nuevaLinea.replace(0, nuevaLinea.length(),
                            nuevaLinea.toString().replaceAll(" ", ""));
                    String pais = nuevaLinea.toString().replaceAll("^\\([0-9]*,[A-Za-z]*,[A-Za-z]*,([A-Za-z]*).*",
                            "$1");
                    int total = Integer.parseInt(nuevaLinea.toString().replaceAll(".*,(\\d*)$", "$1"));
                    nuevaLinea.replace(0, nuevaLinea.length(),
                            nuevaLinea.toString().replaceAll(",([a-zA-Z \\.&?-]+)", ",'$1'"));
                    nuevaLinea.replace(0, nuevaLinea.length(),
                            nuevaLinea.toString().replaceAll("([0-9]*)\\/([0-9]*)\\/([0-9]*)", "'$2/$1/$3'"));
                    nuevaLinea.replace(0, nuevaLinea.length(),
                            nuevaLinea.toString().replaceAll(",(\\d*)$", "," + (conversionTable.get(pais) * total)));
                    nuevaLinea.append("),");
                    values.append(nuevaLinea);
                }
                values.deleteCharAt(values.length() - 1);
                stmt.executeUpdate("INSERT INTO exports values " + values.toString());
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
        System.out.println("se inserto correctamente en tabla: exports");
    }
}