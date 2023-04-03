import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        String archivoCSV = "C:/datos/VentaAutos.csv";
        try (CSVReader reader = new CSVReader(new FileReader(archivoCSV))) {
            String[] linea;
            while ((linea = reader.readNext()) != null) {
                System.out.println(linea[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
    }
}