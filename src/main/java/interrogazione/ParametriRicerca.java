package interrogazione;

import base.utility.Autore;

import java.util.Set;

public class ParametriRicerca implements Parametri{

    private String isbn;
    private String titolo;
    private Set<Autore> autori;


    private static final ParametriRicerca instance = new ParametriRicerca();

    public ParametriRicerca(){}

    public static ParametriRicerca getInstance(){
        return instance;
    }


    //Metodi set
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setAutori(Set<Autore> autori) {
        this.autori = autori;
    }

    //Metodi get
    public String getIsbn() {
        return isbn;
    }

    public String getTitolo() {
        return titolo;
    }

    public Set<Autore> getAutori() {
        return autori;
    }

    @Override
    public void reset(){
        this.isbn = null;
        this.titolo = null;
        this.autori = null;
    }

    @Override
    public boolean isEmpty(){
        return this.isbn == null && this.titolo == null && this.autori == null;
    }

}
