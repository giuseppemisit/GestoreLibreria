package controllo.libreria;

import base.libreria.Libreria;
import base.libro.Libro;
import esplora.CommandEsplora;
import esplora.commands.CommandInterroga;
import esplora.commands.CommandOrdina;
import esplora.interroga.Parametri;
import esplora.ordina.utility.TipoOrdinamento;
import gestione.CommandGestione;
import gestione.commands.AggiungiLibro;
import gestione.commands.ModificaLibro;
import gestione.commands.RimuoviLibro;

import java.util.ArrayList;
import java.util.List;


public class ConcreteHubLibreria extends AbstractHubLibreria {

    private final Libreria libreria;
    private List<Libro> libreriaCorrente;

    private CommandGestione comandoGestione;
    private CommandEsplora comandoEsplora;


    public ConcreteHubLibreria(Libreria libreria) {
        this.libreria = libreria;
        this.libreriaCorrente = new ArrayList<>(libreria.getLibriUtente());
    }

    @Override
    public String utenteCorrente() {
        return libreria.utenteCorrente();
    }

    @Override
    public List<Libro> vistaLibreriaCorrente() {
        return new ArrayList<>(libreriaCorrente);
    }

    @Override
    public void vistaLibreriaReset() {
        libreriaCorrente = new ArrayList<>(libreria.getLibriUtente());
        notificaObserver();
    }

    @Override
    public void eseguiAggiuntaLibro(Libro libro) {
        comandoGestione = new AggiungiLibro(libreria, libro);
        comandoGestione.execute();
        aggiornaLibreriaCorrente();
        notificaObserver();
    }

    @Override
    public void eseguiRimozioneLibro(Libro libro) {
        comandoGestione = new RimuoviLibro(libreria, libro);
        comandoGestione.execute();
        aggiornaLibreriaCorrente();
        notificaObserver();
    }

    @Override
    public void eseguiModificaLibro(Libro originale, Libro modificato) {
        comandoGestione = new ModificaLibro(libreria, originale, modificato);
        comandoGestione.execute();
        aggiornaLibreriaCorrente();
        notificaObserver();
    }

    @Override
    public void annullaUltimaGestione() {
        if(comandoGestione == null)
            throw new IllegalStateException("Nessun ultima modifica da annullare!");
        comandoGestione.undo();
        comandoGestione = null;
        aggiornaLibreriaCorrente();
        notificaObserver();
    }

    @Override
    public void ordinaLibri(TipoOrdinamento tipoOrdinamento) {
        comandoEsplora = new CommandOrdina(libreriaCorrente, tipoOrdinamento);
        libreriaCorrente = comandoEsplora.execute();
        notificaObserver();
    }

    @Override
    public void cercaLibri(Parametri parametri) {
        List<Libro> libreriaOriginale = new ArrayList<>(libreria.getLibriUtente());
        comandoEsplora = new CommandInterroga(libreriaOriginale, parametri);
        libreriaCorrente = comandoEsplora.execute();
        notificaObserver();
    }

    @Override
    public void filtraLibri(Parametri parametri) {
        List<Libro> libreriaOriginale = new ArrayList<>(libreria.getLibriUtente());
        comandoEsplora = new CommandInterroga(libreriaOriginale, parametri);
        libreriaCorrente = comandoEsplora.execute();
        notificaObserver();
    }

    private void aggiornaLibreriaCorrente() {
        libreriaCorrente = new ArrayList<>(libreria.getLibriUtente());
    }

}
