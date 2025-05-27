package esplora.interroga;

import base.libro.Libro;
import base.utility.Autore;

public class ParametriRicerca implements Parametri {

    private final String isbn;
    private final String titolo;
    private final Autore autore;

    public ParametriRicerca(String isbn, String titolo, Autore autore){
        this.isbn = isbn;
        this.titolo = titolo;
        this.autore = autore;
    }

    @Override
    public boolean isEmpty(){
        return this.isbn == null &&
                this.titolo == null &&
                this.autore == null;
    }

    @Override
    public boolean soddisfaCriteri(Libro libro){

        boolean soddisfaIsbn = this.isbn == null || this.isbn.equals(libro.getIsbn());

        boolean soddisfaTitolo = this.titolo == null || this.titolo.equals(libro.getTitolo());

        boolean soddisfaAutori = this.autore == null ||
                libro.getAutori().contains(autore);

        return soddisfaIsbn && soddisfaTitolo && soddisfaAutori;
    }

}
