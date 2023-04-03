public class App {
    public static void main(String[] args) {
        leer leer1 = new leer("TicketH.csv", 0, 4);
        leer leer2 = new leer("TicketH.csv", 1, 4);
        leer leer3 = new leer("TicketH.csv", 2, 4);
        leer leer4 = new leer("TicketH.csv", 3, 4);
        leer1.start();
        leer2.start();
        leer3.start();
        leer4.start();
    }
}