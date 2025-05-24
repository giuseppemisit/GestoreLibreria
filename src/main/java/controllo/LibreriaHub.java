package controllo;

import base.libro.Libro;
import esplora.interroga.Parametri;
import esplora.ordina.utility.TipoOrdinamento;

import java.util.List;

public interface LibreriaHub {
    void aggiungiLibro(Libro libro);
    void rimuoviLibro(Libro libro);
    void modificaLibro(Libro originale, Libro modificato);
    void annullaAggiornamento();
    List<Libro> visualizzaLibri();
    List<Libro> visualizzaLibriOrdinati(TipoOrdinamento tipoOrdinamento);
    List<Libro> cercaLibri(Parametri parametri);
    List<Libro> filtraLibri(Parametri parametri);
}
