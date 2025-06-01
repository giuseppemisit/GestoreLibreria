package esplora.interroga;

import base.libro.Libro;
import base.utility.Autore;

public class ParametriRicerca implements Parametri {

    private final String titolo;
    private final Autore autore;
    private final String isbn;

    public ParametriRicerca(String titolo, Autore autore, String isbn){
        this.titolo = titolo;
        this.autore = autore;
        this.isbn = isbn;
    }

    @Override
    public boolean isEmpty(){
        return this.titolo == null &&
                this.autore == null &&
                this.isbn == null;
    }

    @Override
    public boolean soddisfaCriteri(Libro libro){

        boolean soddisfaTitolo = this.titolo == null ||
                libro.getTitolo().toLowerCase().contains(this.titolo.toLowerCase());

        boolean soddisfaAutori = this.autore == null ||
                libro.getAutori().stream()
                        .anyMatch(a ->
                                a.getNome().toLowerCase().contains(this.autore.getNome().toLowerCase()) &&
                                        a.getCognome().toLowerCase().contains(this.autore.getCognome().toLowerCase())
                        );

        boolean soddisfaIsbn = this.isbn == null ||
                libro.getIsbn().toLowerCase().contains(this.isbn.toLowerCase());

        return soddisfaTitolo && soddisfaAutori && soddisfaIsbn;
    }

}
