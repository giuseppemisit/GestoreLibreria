package repository;

import base.libro.Libro;
import interrogazione.ParametriFiltro;
import interrogazione.ParametriRicerca;
import ordinamento.Ordinamento;
import ordinamento.OrdinamentoContext;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRepository implements RepositoryLibri {

    private String username;
    private List<Libro> libri;

    public AbstractRepository(String username){
        this.username = username;
        this.libri = leggiDati(username);
    }

    public abstract Libro nuoviValoriAggiornamento();
    public abstract Ordinamento scegliOrdinamento();
    public abstract void scegliFiltri(ParametriFiltro filtri);
    public abstract void scegliRicerca(ParametriRicerca ricerca);

    @Override
    public abstract boolean salvaDati(String username, List<Libro> libri);
    @Override
    public abstract List<Libro> leggiDati(String username);


    public String getUsername() {
        return username;
    }

    @Override
    public boolean aggiungi(Libro libro) {
        if(libri.contains(libro))
            return false;
        if (!libri.add(libro))
            return false;

        boolean esitoSalvataggio = salvaDati(username, libri);
        if(!esitoSalvataggio) {
            libri.remove(libro);
            return false;
        }
        return true;
    }

    @Override
    public boolean rimuovi(Libro libro) {
        if(!libri.contains(libro))
            return false;
        if(!libri.remove(libro))
            return false;

        boolean esitoSalvataggio = salvaDati(username, libri);
        if(!esitoSalvataggio) {
            libri.add(libro);
            return false;
        }
        return true;
    }

    @Override
    public boolean aggiorna(Libro libro) {
        if(!libri.contains(libro))
            return false;
        Libro libroAggiornato = nuoviValoriAggiornamento();
        if(!libri.remove(libro) || !libri.add(libroAggiornato))
            return false;

        boolean esitoSalvataggio = salvaDati(username, libri);
        if(!esitoSalvataggio) {
            libri.remove(libroAggiornato);
            libri.add(libro);
            return false;
        }
        return true;
    }

    @Override
    public List<Libro> applicaOrdinamento(){
        OrdinamentoContext ordinamento = new OrdinamentoContext();
        ordinamento.setOrdinamento(scegliOrdinamento());
        List<Libro> libriOrdinati = new ArrayList<>(this.libri);
        ordinamento.esegui(libriOrdinati);
        return libriOrdinati;
    }

    @Override
    public List<Libro> applicaFiltri(){
        List<Libro> risultato = new ArrayList<>();
        ParametriFiltro filtro = ParametriFiltro.getInstance();
        scegliFiltri(filtro);

        if(filtro.isEmpty()) return libri;

        for(Libro libro : libri) {

            // Verifichiamo se soddisfa i criteri di filtro
            boolean soddisfaGenere = filtro.getGenere().isEmpty() ||
                    libro.getGeneri().stream().anyMatch(filtro.getGenere()::contains);

            boolean soddisfaStatoLettura = filtro.getStatoLettura() == null ||
                    filtro.getStatoLettura().equals(libro.getStatoLettura());

            boolean soddisfaValutazione = filtro.getValutazione() == null ||
                    filtro.getValutazione().equals(libro.getValutazione());

            // Aggiungiamo il libro se soddisfa almeno un criterio
            if(soddisfaGenere || soddisfaStatoLettura || soddisfaValutazione) {
                risultato.add(libro);
            }
        }
        return risultato;

    }

    @Override
    public List<Libro> annullaFiltri() {
        ParametriFiltro filtro = ParametriFiltro.getInstance();
        filtro.reset();
        return libri;
    }

    @Override
    public List<Libro> applicaRicerca() {
        List<Libro> risultato = new ArrayList<>();
        ParametriRicerca ricerca = ParametriRicerca.getInstance();
        scegliRicerca(ricerca);

        if(ricerca.isEmpty()) return libri;

        for(Libro libro : libri){

            boolean soddisfaIsbn = ricerca.getIsbn() == null || ricerca.getIsbn().equals(libro.getIsbn());

            boolean soddisfaTitolo = ricerca.getTitolo() == null || ricerca.getTitolo().equals(libro.getTitolo());

            boolean soffisfaAutori = ricerca.getAutori().isEmpty() ||
                    libro.getAutori().stream().anyMatch(ricerca.getAutori()::contains);

            // Aggiungiamo il libro se soddisfa almeno un criterio
            if(soddisfaIsbn || soddisfaTitolo || soffisfaAutori) {
                risultato.add(libro);
            }
        }
        return risultato;
    }

    @Override
    public List<Libro> annullaRicerca() {
        ParametriRicerca ricerca = ParametriRicerca.getInstance();
        ricerca.reset();
        return libri;
    }

    @Override
    public List<Libro> visualizzaLibriReset() {
        return libri;
    }

}
