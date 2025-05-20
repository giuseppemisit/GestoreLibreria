package gestione.commands;

import base.libro.Libro;
import base.libreria.Libreria;

public class AggiungiLibro extends AbstractModifiche {

    public AggiungiLibro(Libreria libreria, Libro libro) {
        super(libreria, libro);
    }

    @Override
    public void execute() {
        checkNotExecuted();
        boolean successo = libreria.aggiungiLibro(libro);
        verificaSuccesso(successo, "Aggiunta non riuscita");
        operazioneEffettuata = true;
    }

    @Override
    public void undo() {
        checkExecuted();
        boolean successo = libreria.rimuoviLibro(libro);
        verificaSuccesso(successo, "Annullamento non riuscito");
        operazioneEffettuata = false;
    }

}