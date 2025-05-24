package base.libro;

import base.utility.Autore;
import base.utility.InfoExtra;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractLibro implements Libro {

    private final String isbn;
    private String titolo;
    private InfoExtra.Valutazione valutazione;
    private InfoExtra.StatoLettura statoLettura;

    private final Set<Autore> autori = new HashSet<>();
    private final Set<InfoExtra.GenereLibro> genere = new HashSet<>();

    public AbstractLibro(String isbn, String titolo, InfoExtra.Valutazione valutazione,
                         InfoExtra.StatoLettura statoLettura,
                         Set<Autore> autori,
                         Set<InfoExtra.GenereLibro> genere) {
        // Verifiche
        if(!isbn.matches("\\d{13}"))
            throw new IllegalArgumentException("ISBN non valido");
        if (titolo == null || titolo.isBlank())
            throw new IllegalArgumentException("Inserire un titolo valido");
        if(autori == null || autori.isEmpty())
            throw new IllegalArgumentException("Inserire almeno un autore per il libro");

        this.isbn = isbn;
        this.titolo = titolo;
        this.valutazione = valutazione;
        this.statoLettura = statoLettura;
        for (Autore autore : autori) {aggiungiAutore(autore);}
        if (genere != null) {
            for (InfoExtra.GenereLibro g : genere) { aggiungiGenere(g); }
        }

    }

    @Override
    public String getIsbn() {
        return isbn;
    }

    @Override
    public String getTitolo() {
        return titolo;
    }

    @Override
    public InfoExtra.Valutazione getValutazione() {
        return valutazione;
    }

    @Override
    public InfoExtra.StatoLettura getStatoLettura() {
        return statoLettura;
    }

    public void setTitolo(String titolo) {
        if(titolo == null || titolo.isBlank())
            throw new IllegalArgumentException("Inserire un titolo valido");
        this.titolo = titolo;
    }

    public abstract Libro clone();

    public void setValutazione(InfoExtra.Valutazione valutazione) {
        this.valutazione = valutazione;
    }

    public void setStatoLettura(InfoExtra.StatoLettura statoLettura) {
        this.statoLettura = statoLettura;
    }

    @Override
    public Collection<Autore> getAutori() {
        return Collections.unmodifiableSet(autori);
    }

    @Override
    public Collection<InfoExtra.GenereLibro> getGeneri() {
        return Collections.unmodifiableSet(genere);
    }

    @Override
    public boolean aggiungiAutore(Autore autore){
        if(autore == null)
            throw new IllegalArgumentException("Inserire un autore valido");
        return this.autori.add(autore);
    }

    @Override
    public boolean rimuoviAutore(Autore autore){
        if(autore == null)
            throw new IllegalArgumentException("Inserire un autore valido");
        if(this.autori.size() <= 1)
            return false; // Il libro deve avere almeno un autore
        return this.autori.remove(autore);
    }

    @Override
    public boolean aggiungiGenere(InfoExtra.GenereLibro genere){
        if(genere == null)
            return false;
        return this.genere.add(genere);
    }

    @Override
    public boolean rimuoviGenere(InfoExtra.GenereLibro genere){
        if(genere == null)
            return false;
        return this.genere.remove(genere);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof AbstractLibro)) return false;

        AbstractLibro l = (AbstractLibro) obj;
        return l.isbn.equals(this.isbn);
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + isbn.hashCode();
        return result;
    }

}