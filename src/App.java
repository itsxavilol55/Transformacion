import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        replaces.inserta();
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
        // LeerTicketH leerTicketH1 = new LeerTicketH(stmt, 0, 2);
        // LeerTicketH leerTicketH2 = new LeerTicketH(stmt, 1, 2);
        // leerTicketH1.start();
        // leerTicketH2.start();
        // ticketD
        // int total = 5;
        // LeerTicketD[] leerTicketD = new LeerTicketD[total];
        // for (int i = 0; i < total; i++)
        // leerTicketD[i] = new LeerTicketD(stmt, i, total);
        // for (int i = 0; i < total; i++)
        // leerTicketD[i].start();
        // Synergy
        // LeerSynergy LeerSynergy1 = new LeerSynergy(stmt, 0, 2);
        // LeerSynergy LeerSynergy2 = new LeerSynergy(stmt, 1, 2);
        // LeerSynergy1.start();
        // LeerSynergy2.start();
        // // VentaAutos
        int total2 = 10;
        LeerAutos[] leerAutos = new LeerAutos[total2];
        for (int i = 0; i < total2; i++)
            leerAutos[i] = new LeerAutos(stmt, i, total2);
        for (int i = 0; i < total2; i++)
            leerAutos[i].start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("___----.-.-.-.-");
        System.exit(0);
    }
}