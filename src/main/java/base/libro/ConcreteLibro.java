package base.libro;

import base.Autore;
import base.InfoExtra;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ConcreteLibro extends AbstractLibro {

    private final Set<Autore> autori;
    private final Set<InfoExtra.GenereLibro> genere;

    public ConcreteLibro(String isbn,
                         String titolo,
                         InfoExtra.Valutazione valutazione,
                         InfoExtra.StatoLettura statoLettura,
                         Set<Autore> autori,
                         Set<InfoExtra.GenereLibro> genere){

        super(isbn,titolo,valutazione,statoLettura);

        if(autori == null || autori.isEmpty())
            throw new IllegalArgumentException("Inserire almeno un autore per il libro");

        this.autori = new HashSet<>(autori);
        this.genere = (genere != null) ? new HashSet<>(genere) : new HashSet<>();
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
        if(autore == null) throw new IllegalArgumentException("Inserire un autore valido");
        return this.autori.add(autore);
    }
    @Override
    public boolean rimuoviAutore(Autore autore){
        if(autore == null) throw new IllegalArgumentException("Inserire un autore valido");
        return this.autori.remove(autore);
    }
    @Override
    public boolean aggiungiGenere(InfoExtra.GenereLibro genere){
        return this.genere.add(genere);
    }
    @Override
    public boolean rimuoviGenere(InfoExtra.GenereLibro genere){
        return this.genere.remove(genere);
    }

}