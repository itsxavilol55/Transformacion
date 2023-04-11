import java.util.Hashtable;

public class replaces {
    private static final Hashtable<String, Integer> meses = new Hashtable<>();
    private static final Hashtable<String, Integer> dias = new Hashtable<>();
    private static String letras = "\\/([a-z]*)\\/", numeros = "([0-9]*)";

    public static void inserta() {
        meses.put("ene", 1);
        meses.put("feb", 2);
        meses.put("mar", 3);
        meses.put("abr", 4);
        meses.put("may", 5);
        meses.put("jun", 6);
        meses.put("jul", 7);
        meses.put("ago", 8);
        meses.put("sep", 9);
        meses.put("oct", 10);
        meses.put("nov", 11);
        meses.put("dic", 12);
        dias.put("1", 31);
        dias.put("2", 28);
        dias.put("3", 31);
        dias.put("4", 30);
        dias.put("5", 31);
        dias.put("6", 30);
        dias.put("7", 31);
        dias.put("8", 31);
        dias.put("9", 30);
        dias.put("10", 31);
        dias.put("11", 30);
        dias.put("12", 31);
    }

    public static void removeSpaces(StringBuilder nuevaLinea) {
        nuevaLinea.replace(0, nuevaLinea.length(),
                nuevaLinea.toString().replaceAll(" ", ""));
    }

    public static void ponerComillas(StringBuilder nuevaLinea) {
        nuevaLinea.replace(0, nuevaLinea.length(),
                nuevaLinea.toString().replaceAll("([a-zA-Z \\.&?-]+[0-9]*)", "'$1'"));
    }

    public static void dateFormat(StringBuilder nuevaLinea) {
        int dia = Integer.parseInt(nuevaLinea.toString().replaceAll(".*," + numeros + "\\/.*", "$1"));
        String mes = nuevaLinea.toString().replaceAll(".*\\/" + numeros + "\\/.*", "$1");
        dia = Math.min(dia, dias.get(mes));
        nuevaLinea.replace(0, nuevaLinea.length(),
                nuevaLinea.toString().replaceAll(numeros + "\\/" + numeros + "\\/" + numeros, "'$2/" + dia + "/$3'"));
    }

    public static void mesFormat(StringBuilder nuevaLinea) {
        String mes = nuevaLinea.toString().replaceAll(".*" + letras + ".*", "$1");
        nuevaLinea.replace(0, nuevaLinea.length(),
                nuevaLinea.toString().replaceAll(letras, "/" + meses.get(mes) + "/"));
    }
}
