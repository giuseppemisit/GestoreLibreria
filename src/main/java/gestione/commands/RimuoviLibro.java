package gestione.commands;

import base.libro.Libro;
import base.libreria.Libreria;

public class RimuoviLibro extends AbstractCommandGestione {

    public RimuoviLibro(Libreria libreria, Libro libro) {
        super(libreria, libro);
    }
    
    @Override
    public void execute(){
        checkNotExecuted();
        boolean successo = libreria.rimuoviLibro(libro);
        verificaSuccesso(successo, "Eliminazione non riuscita");
        operazioneEffettuata = true;
    }

    @Override
    public void undo(){
        checkExecuted();
        boolean successo = libreria.aggiungiLibro(libro);
        verificaSuccesso(successo, "Annullamento non riuscito");
        operazioneEffettuata = false;
    }
    
}