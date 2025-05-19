package gestione.commands;

import base.libro.Libro;
import repository.RepositoryLibri;

public class RimuoviLibro extends AbstractLibroCommand{

    public RimuoviLibro(RepositoryLibri repository, Libro libro) {
        super(repository, libro);
    }
    
    @Override
    public void execute(){
        checkNotExecuted();
        boolean successo = repository.rimuovi(libro);
        verificaSuccesso(successo, "Eliminazione non riuscita");
        operazioneEffettuata = true;
    }

    @Override
    public void undo(){
        checkExecuted();
        boolean successo = repository.aggiungi(libro);
        verificaSuccesso(successo, "Annullamento non riuscito");
        operazioneEffettuata = false;
    }
    
}