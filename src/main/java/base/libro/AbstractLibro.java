package base.libro;

import base.InfoExtra;

public abstract class AbstractLibro implements Libro {
    // isbn è univoco per ogni libro.
    private final String isbn;
    // i campi non univoci possono essere modificati.
    private String titolo;
    private InfoExtra.Valutazione valutazione;
    private InfoExtra.StatoLettura statoLettura;

    public AbstractLibro(String isbn, String titolo, InfoExtra.Valutazione valutazione, InfoExtra.StatoLettura statoLettura){
        if(!isbn.matches("\\d{13}"))
            throw new IllegalArgumentException("ISBN non valido");
        if (titolo == null || titolo.isBlank())
            throw new IllegalArgumentException("Inserire un titolo valido");

        this.isbn = isbn;
        this.titolo = titolo;
        this.valutazione = valutazione;
        this.statoLettura = statoLettura;
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
        if(titolo == null || titolo.isBlank()) throw new IllegalArgumentException("Inserire un titolo valido");
        this.titolo = titolo;
    }
    public void setValutazione(InfoExtra.Valutazione valutazione) {
        this.valutazione = valutazione;
    }
    public void setStatoLettura(InfoExtra.StatoLettura statoLettura) {
        this.statoLettura = statoLettura;
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