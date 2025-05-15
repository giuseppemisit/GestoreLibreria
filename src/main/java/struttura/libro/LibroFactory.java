package struttura.libro;

import struttura.autore.AutoreConcrete;

import java.util.HashMap;
import java.util.Map;

public class LibroFactory {

    Map<Integer, Map<Integer, Libro>> libri = new HashMap<>();
    Map<Integer, AutoreConcrete> autori = new HashMap<>();
}
