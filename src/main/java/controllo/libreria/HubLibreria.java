package controllo.libreria;

import app.pannelli.Observer;
import base.libro.Libro;
import esplora.interroga.Parametri;
import esplora.ordina.utility.TipoOrdinamento;

import java.util.List;

public interface HubLibreria {

    String utenteCorrente();

    // getState
    List<Libro> vistaLibreriaCorrente();

    // setState
    void vistaLibreriaReset();

    void eseguiAggiuntaLibro(Libro libro);
    void eseguiRimozioneLibro(Libro libro);
    void eseguiModificaLibro(Libro originale, Libro modificato);
    void annullaUltimaGestione();

    void ordinaLibri(TipoOrdinamento tipo);
    void cercaLibri(Parametri parametri);
    void filtraLibri(Parametri parametri);


    // Metodi per observer
    void aggiungiObserver(Observer observer);
    void rimuoviObserver(Observer observer);
    void notificaObserver();

}
