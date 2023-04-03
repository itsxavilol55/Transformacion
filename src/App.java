public class App {
    public static void main(String[] args) {
        leer leer1 = new leer("TicketH.csv", 0, 2);
        leer leer2 = new leer("TicketH.csv", 1, 2);
        leer1.start();
        leer2.start();
    }
}