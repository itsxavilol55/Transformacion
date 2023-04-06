import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        Connection con;
        Statement stmt = null;
        try {
            con = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost:1433;encrypt=true;databaseName=almacen;TrustServerCertificate=true;",
                    "Javi", "javi54321");
            stmt = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // ticketH
        LeerTicketH leerTicketH1 = new LeerTicketH(stmt, 0, 2);
        LeerTicketH leerTicketH2 = new LeerTicketH(stmt, 1, 2);
        leerTicketH1.start();
        leerTicketH2.start();
        // ticketD
        // int total = 5;
        // leer[] leerTicketD = new leer[total];
        // for (int i = 0; i < total; i++)
        // leerTicketD[i] = new leer("TicketD.csv", i, total, "DetalleVentas");
        // for (int i = 0; i < total; i++)
        // leerTicketD[i].start();
    }
}