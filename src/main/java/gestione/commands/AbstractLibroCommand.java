package gestione.commands;

import base.libro.Libro;
import gestione.Command;
import salvataggio.RepositoryLibri;

public abstract class AbstractLibroCommand implements Command {

    protected final RepositoryLibri repository;
    protected final Libro libro;
    protected boolean operazioneEffettuata;

    protected AbstractLibroCommand(RepositoryLibri repository, Libro libro) {
        if (repository == null || libro == null) {
            throw new IllegalArgumentException("Repository e libro non possono essere null");
        }
        this.repository = repository;
        this.libro = libro;
        this.operazioneEffettuata = false;
    }

    protected void checkNotExecuted() {
        if(operazioneEffettuata) {
            throw new IllegalStateException("Comando già eseguito");
        }
    }

    protected void checkExecuted() {
        if(!operazioneEffettuata) {
            throw new IllegalStateException("Nessuna operazione da annullare");
        }
    }

    protected void verificaSuccesso(boolean successo, String messaggioErrore) {
        if(!successo) {
            throw new IllegalStateException(messaggioErrore);
        }
    }
    
}
