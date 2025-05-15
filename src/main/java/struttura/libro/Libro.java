package struttura.libro;

import struttura.dettagli.Informazioni;

public interface Libro {

    /**
     * Restituisce i dettagli del libro e parametri soggettivi dell'utente
     * @return Stringa contenente i dettagli del libro
     */
    String dettagli(Informazioni.Valutazione valutazione, Informazioni.StatoLettura stato);

}
