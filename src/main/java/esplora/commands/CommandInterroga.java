package esplora.commands;

import base.libro.Libro;
import esplora.interroga.Parametri;

import java.util.ArrayList;
import java.util.List;

public class CommandInterroga extends AbstractCommandEsplora {

    private final Parametri interrogazione;

    public CommandInterroga(List<Libro> libri, Parametri interrogazione){
        super(libri);
        if(interrogazione == null)
            throw new IllegalArgumentException("L'oggetto con i parametri interrogazione non può essere null");

        this.interrogazione = interrogazione;
    }

    @Override
    public List<Libro> execute() {
        List<Libro> risultato = new ArrayList<>();
        for(Libro libro : libri){
            if(interrogazione.soddisfaCriteri(libro))
                risultato.add(libro);
        }
        return risultato;
    }

}
