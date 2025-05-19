package gestione.commands;

import base.libro.Libro;
import repository.RepositoryLibri;

public class ModificaLibro extends AbstractLibroCommand {

    private Libro libroOriginale;

    public ModificaLibro(RepositoryLibri repository, Libro libro) {
        super(repository, libro);
    }

    @Override
    public void execute() {
        checkNotExecuted();
        libroOriginale = libro.clone();
        boolean successo = repository.aggiorna(libro);
        verificaSuccesso(successo, "Modifica non riuscita");
        operazioneEffettuata = true;
    }

    @Override
    public void undo() {
        checkExecuted();
        boolean successo = repository.aggiorna(libroOriginale);
        verificaSuccesso(successo, "Annullamento non riuscito");
        operazioneEffettuata = false;
    }

}
