package gestione.commands;

import base.libro.Libro;
import gestione.CommandGestione;
import base.libreria.Libreria;

public abstract class AbstractCommandGestione implements CommandGestione {

    protected final Libreria libreria;
    protected final Libro libro;
    protected boolean operazioneEffettuata;

    protected AbstractCommandGestione(Libreria libreria, Libro libro) {
        if (libreria == null || libro == null) {
            throw new IllegalArgumentException("Libreria e libro non possono essere null");
        }
        this.libreria = libreria;
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
