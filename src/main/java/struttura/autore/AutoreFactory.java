package struttura.autore;

import java.util.HashMap;
import java.util.Map;

public class AutoreFactory {

    private static final Map<String, Autore> autori = new HashMap<>();

    public static Autore getAutore(String nome, String cognome, String biografia){
        nome = formattaStringa(nome);
        cognome = formattaStringa(cognome);
        String chiave = chiaveAutore(nome, cognome);

        Autore autore = autori.get(chiave);

        if(autore == null){
            autore = new AutoreConcrete(nome, cognome, biografia);
            autori.put(chiave, autore);
        }
        return autore;
    }


    private static String chiaveAutore(String nome, String cognome, String biografia){
        nome = formattaStringa(nome);
        cognome = formattaStringa(cognome);
        return nome + "|" + cognome + Integer.toString(biografia.hashCode());
    }

    private static String formattaStringa(String s){
        if(s == null || s.isBlank())
            return "";
        s = s.trim();
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

}
