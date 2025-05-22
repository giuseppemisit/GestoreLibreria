package app.UI;

import base.libro.Libro;
import base.utility.Autore;
import base.utility.InfoExtra;
import esplora.interroga.ParametriFiltro;
import esplora.interroga.ParametriRicerca;
import esplora.ordina.utility.TipoOrdinamento;

public interface UserInterface {

    /**
     * Selezionare un utente tra quelli disponibili
     * @return nome dell'utente selezionato
     */
    String selezionaUtenteEsistente();

    /**
     * Crea un nuovo utente nel sistema.
     * @return true se il nuovo utente è stato creato correttamente, false altrimenti
     */
    boolean creaNuovoUtente();

    /**
     * Permette di selezionare un libro esistente
     * @return Libro selezionato dall'utente
     */
    Libro selezionaLibroEsistente();

    /**
     * Crea un nuovo oggetto Libro
     * @return oggetto Libro creato dall'utente
     */
    Libro creaNuovoLibro();

    /**
     * Crea un nuovo oggetto Autore
     * @return oggetto autore creato dall'utente
     */
    Autore creaNuovoAutore();

    /**
     * Permette di selezionare un criterio di ordinamento tra quelli disponibili
     * @return il criterio di ordinamento scelto dall'utente
     */
    TipoOrdinamento selezionaOrdinamento();

    /**
     * Crea e restituisce un'istanza di ParametriRicerca
     * Vengono impostati i criteri di ricerca
     * relativi a ISBN, titolo e autore di un libro.
     * @return un oggetto ParametriRicerca
     */
    ParametriRicerca creaParametriRicerca();

    /**
     * Crea e restituisce un'istanza di ParametriFiltro
     * Vengono impostati i criteri di flitro
     * quali genere, valutazione, stato di lettura.
     * @return un oggetto ParametriFiltro
     */
    ParametriFiltro creaParametriFiltro();

    /**
     * Seleziona il genere tra quelli disponibili
     * @return genere
     */
    InfoExtra.GenereLibro selezionaGenere();

    /**
     * Seleziona la valutazione tra quelle ammissibili
     * @return valutazione
     */
    InfoExtra.Valutazione selezionaValutazione();

    /**
     * Seleziona lo stato di lettura
     * @return stato di lettura
     */
    InfoExtra.StatoLettura selezionaStatoLettura();

}
