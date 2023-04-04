public class App {
    public static void main(String[] args) {
        // ticketH
        leer leerTicketH1 = new leer("TicketH.csv", 0, 2, "ventas");
        leer leerTicketH2 = new leer("TicketH.csv", 1, 2, "ventas");
        leerTicketH1.start();
        leerTicketH2.start();
        // ticketD
        int total = 2;
        leer[] leerTicketD = new leer[total];
        for (int i = 0; i < total; i++)
            leerTicketD[i] = new leer("TicketD.csv", i, total, "DetalleVentas");
        for (int i = 0; i < total; i++)
            leerTicketD[i].start();
    }
}