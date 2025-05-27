package controllo;

import base.libreria.Libreria;
import base.libro.Libro;
import esplora.CommandEsplora;
import esplora.CommandInterroga;
import esplora.CommandOrdina;
import esplora.interroga.Parametri;
import esplora.ordina.utility.TipoOrdinamento;
import gestione.CommandAggiorna;
import gestione.GestoreAggiorna;
import gestione.commands.AggiungiLibro;
import gestione.commands.ModificaLibro;
import gestione.commands.RimuoviLibro;

import java.util.ArrayList;
import java.util.List;


public class ConcreteLibreriaHub implements LibreriaHub {

    private final Libreria libreria;
    private final GestoreAggiorna gestoreAggiorna;

    public ConcreteLibreriaHub(Libreria libreria) {
        this.libreria = libreria;
        this.gestoreAggiorna = new GestoreAggiorna();
    }

    @Override
    public String utenteCorrente() {
        return libreria.utenteCorrente();
    }

    @Override
    public void aggiungiLibro(Libro libro) {
        CommandAggiorna cmdAggiungi = new AggiungiLibro(libreria, libro);
        gestoreAggiorna.eseguiComando(cmdAggiungi);
    }

    @Override
    public void rimuoviLibro(Libro libro) {
        CommandAggiorna cmdRimuovi = new RimuoviLibro(libreria, libro);
        gestoreAggiorna.eseguiComando(cmdRimuovi);
    }

    @Override
    public void modificaLibro(Libro originale, Libro modificato) {
        CommandAggiorna cmdModifica = new ModificaLibro(libreria, originale, modificato);
        gestoreAggiorna.eseguiComando(cmdModifica);
    }

    @Override
    public void annullaAggiornamento() {
        if(gestoreAggiorna.haComandoDaAnnullare())
            gestoreAggiorna.annullaComando();
    }

    @Override
    public List<Libro> visualizzaLibri() {
        return new ArrayList<>(libreria.getLibriUtente());
    }

    @Override
    public List<Libro> visualizzaLibriOrdinati(TipoOrdinamento tipoOrdinamento) {
        List<Libro> libri = visualizzaLibri();
        CommandEsplora cmdOrdina = new CommandOrdina(libri, tipoOrdinamento);
        return cmdOrdina.execute();
    }

    @Override
    public List<Libro> cercaLibri(Parametri parametri) {
        List<Libro> libri = visualizzaLibri();
        CommandInterroga cmdRicerca = new CommandInterroga(libri, parametri);
        return cmdRicerca.execute();
    }

    @Override
    public List<Libro> filtraLibri(Parametri parametri) {
        List<Libro> libri = visualizzaLibri();
        CommandInterroga cmdFiltro = new CommandInterroga(libri, parametri);
        return cmdFiltro.execute();
    }

}
