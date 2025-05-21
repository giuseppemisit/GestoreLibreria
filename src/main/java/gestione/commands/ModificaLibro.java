package gestione.commands;

import base.libro.Libro;
import base.libreria.Libreria;

public class ModificaLibro extends AbstractCommandAggiorna {

    private Libro libroModificato;
    private Libro libroOriginale;

    public ModificaLibro(Libreria libreria, Libro libro, Libro libroModificato) {
        super(libreria, libro);
        this.libroModificato = libroModificato;
    }

    @Override
    public void execute() {
        checkNotExecuted();
        libroOriginale = libro.clone();
        boolean successo = libreria.modificaLibro(libro, libroModificato);
        verificaSuccesso(successo, "Modifica non riuscita");
        operazioneEffettuata = true;
    }

    @Override
    public void undo() {
        checkExecuted();
        boolean successo = libreria.modificaLibro(libroModificato, libroOriginale);
        verificaSuccesso(successo, "Annullamento non riuscito");
        operazioneEffettuata = false;
    }

}
