package esplora.interroga;

import base.libro.Libro;
import base.utility.Autore;

import java.util.HashSet;
import java.util.Set;

public class ParametriRicerca implements Parametri {

    private final String isbn;
    private final String titolo;
    private final Set<Autore> autori;

    public ParametriRicerca(String isbn, String titolo){
        this.isbn = isbn;
        this.titolo = titolo;
        this.autori = new HashSet<>();
    }

    public boolean aggiungiAutore(Autore autore){
        if(autore == null)
            return false;
        return this.autori.add(autore);
    }

    @Override
    public boolean isEmpty(){
        return this.isbn == null &&
                this.titolo == null &&
                this.autori.isEmpty();
    }

    @Override
    public boolean soddisfaCriteri(Libro libro){

        boolean soddisfaIsbn = this.isbn == null || this.isbn.equals(libro.getIsbn());

        boolean soddisfaTitolo = this.titolo == null || this.titolo.equals(libro.getTitolo());

        boolean soffisfaAutori = this.autori.isEmpty() ||
                libro.getAutori().stream().anyMatch(this.autori::contains);

        return soddisfaIsbn && soddisfaTitolo && soffisfaAutori;
    }

}
