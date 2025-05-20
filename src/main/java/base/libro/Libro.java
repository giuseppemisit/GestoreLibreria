package base.libro;

import base.utility.Autore;
import base.utility.InfoExtra;

import java.util.Collection;

public interface Libro extends Cloneable {

    /** @return l'ISBN a 13 cifre del libro */
    String getIsbn();

    /** @return il titolo del libro */
    String getTitolo();

    /** @return la valutazione corrente */
    InfoExtra.Valutazione getValutazione();

    /** @return lo stato di lettura */
    InfoExtra.StatoLettura getStatoLettura();

    /**
     * Mostra gli autori del libro
     * @return una collezione di autori non modificabili
     */
    Collection<Autore> getAutori();

    /**
     * Mostra i genere letterario del libro
     * @return una collezione di genere letterario non modificabili
     */
    Collection<InfoExtra.GenereLibro> getGeneri();

    /**
     * Aggiunge un autore al libro
     * @param autore
     * @return true se l'autore è stato aggiunto al libro, false altrimenti
     */
    boolean aggiungiAutore(Autore autore);

    /**
     * Rimuove un autore dal libro
     * @param autore
     * @return true se l'autore è stato rimosso dal libro, false altrimenti
     */
    boolean rimuoviAutore(Autore autore);

    /**
     * Aggiunge un genere letterario al libro
     * @param genere
     * @return true se il genere è stato aggiunto al libro, false altrimenti
     */
    boolean aggiungiGenere(InfoExtra.GenereLibro genere);

    /**
     * Rimuove un genere letterario dal libro
     * @param genere
     * @return true se il genere è stato rimosso dal libro, false altrimenti
     */
    boolean rimuoviGenere(InfoExtra.GenereLibro genere);

    /**
     * Crea un nuovo oggetto Libro identico a questo oggetto
     * @return un nuovo oggetto Libro
     */
    Libro clone();

}