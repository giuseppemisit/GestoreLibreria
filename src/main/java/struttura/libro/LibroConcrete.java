package struttura.libro;

import struttura.dettagli.Autore;
import struttura.dettagli.Informazioni;

public final class LibroConcrete implements Libro {

    private final int ISBN;
    private final String TITOLO;
    private final Autore AUTORE;
    private final Informazioni.GenereLibro GENERE;

    public LibroConcrete(int ISBN, String TITOLO, Autore AUTORE, Informazioni.GenereLibro GENERE) {
        if((ISBN != 13) || (ISBN != 10))
            throw new IllegalArgumentException("ISBN non valido");
        this.ISBN = ISBN;
        this.TITOLO = TITOLO;
        this.AUTORE = AUTORE;
        this.GENERE = GENERE;
    }

    public int getISBN() {
        return ISBN;
    }
    public String getTitolo() {
        return TITOLO;
    }
    public Autore getAutore() {
        return AUTORE;
    }
    public Informazioni.GenereLibro getGenere() {
        return GENERE;
    }

    @Override
    public String dettagli(Informazioni.Valutazione valutazione, Informazioni.StatoLettura stato) {
        StringBuilder sb = new StringBuilder();

        sb.append("ISBN: ").append(ISBN).append("\n");

        sb.append("\nTitolo: ").append(TITOLO).append("\n");
        sb.append("Autore: ").append(AUTORE.getNome()).append(" ").append(AUTORE.getCognome()).append("\n");
        sb.append("Genere: ").append(GENERE).append("\n");

        sb.append("\nStato: ").append(stato).append("\n");
        sb.append("Valutazione: ").append(valutazione.getStelle()).append("/5").append(" Stelle\n");

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof LibroConcrete)) return false;

        LibroConcrete l = (LibroConcrete) obj;
        return l.ISBN == this.ISBN;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + ISBN;
        return result;
    }
}