package struttura.libro;

import struttura.dettagli.Autore;

import java.util.HashMap;
import java.util.Map;

public class LibroFactory {

    Map<Integer, Map<Integer, Libro>> libri = new HashMap<>();
    Map<Integer, Autore> autori = new HashMap<>();
}
