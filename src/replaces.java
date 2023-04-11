public class replaces {
    public static void removeSpaces(StringBuilder nuevaLinea) {
        nuevaLinea.replace(0, nuevaLinea.length(),
                nuevaLinea.toString().replaceAll(" ", ""));
    }

    public static void ponerComillas(StringBuilder nuevaLinea) {
        nuevaLinea.replace(0, nuevaLinea.length(),
                nuevaLinea.toString().replaceAll(",([a-zA-Z \\.&?-]+)", ",'$1'"));
    }

    public static void dateFormat(StringBuilder nuevaLinea) {
        nuevaLinea.replace(0, nuevaLinea.length(),
                nuevaLinea.toString().replaceAll("([0-9]*)\\/([0-9]*)\\/([0-9]*)", "'$2/$1/$3'"));
    }
}
