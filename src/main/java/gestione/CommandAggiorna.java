package gestione;

public interface CommandAggiorna {

    /**
     * Esegue il comando incapsulato dalla classe implementante.
     */
    void execute();

    /**
     * Annulla l'ultima azione eseguita dal metodo execute.
     * (Gestione di un solo livello di annullamento)
     */
    void undo();

}
