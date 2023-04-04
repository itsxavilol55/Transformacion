public class App {
    public static void main(String[] args) {
        // ticketH
        leer leerTicketH1 = new leer("TicketH.csv", 0, 2, "ventas");
        leer leerTicketH2 = new leer("TicketH.csv", 1, 2, "ventas");
        // ticketD
        leer leerTicketD1 = new leer("TicketD.csv", 0, 6, "DetalleVentas");
        leer leerTicketD2 = new leer("TicketD.csv", 1, 6, "DetalleVentas");
        leer leerTicketD3 = new leer("TicketD.csv", 2, 6, "DetalleVentas");
        leer leerTicketD4 = new leer("TicketD.csv", 3, 6, "DetalleVentas");
        leer leerTicketD5 = new leer("TicketD.csv", 4, 6, "DetalleVentas");
        leer leerTicketD6 = new leer("TicketD.csv", 5, 6, "DetalleVentas");

        // leerTicketH1.start();
        // leerTicketH2.start();

        leerTicketD1.start();
        leerTicketD2.start();
        leerTicketD3.start();
        leerTicketD4.start();
        leerTicketD5.start();
        leerTicketD6.start();

    }
}