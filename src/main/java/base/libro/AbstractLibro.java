package base.libro;

import base.utility.Autore;
import base.utility.InfoExtra;

import java.util.*;

public abstract class AbstractLibro implements Libro {

    private final String isbn;
    private String titolo;
    private InfoExtra.Valutazione valutazione;
    private InfoExtra.StatoLettura statoLettura;

    private final Set<Autore> autori;
    private final Set<InfoExtra.GenereLibro> generi;

    public AbstractLibro(String isbn,
                         String titolo,
                         InfoExtra.Valutazione valutazione,
                         InfoExtra.StatoLettura statoLettura,
                         Set<Autore> autori,
                         Set<InfoExtra.GenereLibro> generi) {
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
        this.autori = new LinkedHashSet<>(autori);
        this.generi = new LinkedHashSet<>(generi);
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
    public List<Autore> getAutori() {
        return new ArrayList<>(autori);
    }

    @Override
    public List<InfoExtra.GenereLibro> getGeneri() {
        return new ArrayList<>(generi);
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
        return this.generi.add(genere);
    }

    @Override
    public boolean rimuoviGenere(InfoExtra.GenereLibro genere){
        if(genere == null)
            return false;
        return this.generi.remove(genere);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof AbstractLibro)) return false;

        Libro altro = (Libro) obj;
        if (!isbn.equals(altro.getIsbn())) return false;
        if (!titolo.equals(altro.getTitolo())) return false;

        if (valutazione == null) {
            if (altro.getValutazione() != null) return false;
        } else if (!valutazione.equals(altro.getValutazione())) {
            return false;
        }

        if (statoLettura == null) {
            if (altro.getStatoLettura() != null) return false;
        } else if (!statoLettura.equals(altro.getStatoLettura())) {
            return false;
        }

        if (!new HashSet<>(autori).equals(new HashSet<>(altro.getAutori()))) return false;
        if (!new HashSet<>(generi).equals(new HashSet<>(altro.getGeneri()))) return false;

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + isbn.hashCode();
        result = prime * result + titolo.hashCode();
        result = prime * result + (valutazione == null ? 0 : valutazione.hashCode());
        result = prime * result + (statoLettura == null ? 0 : statoLettura.hashCode());
        for (Autore autore : autori) { result = prime * result + autore.hashCode(); }
        for (InfoExtra.GenereLibro genere : generi) { result = prime * result + genere.hashCode(); }

        return result;
    }

}