package gestione;

public class GestoreAggiorna {

    private CommandAggiorna comandoCorrente;

    public void eseguiComando(CommandAggiorna comando){
        if(comando == null)
            throw new IllegalArgumentException("Comando non valido");
        comando.execute();
        comandoCorrente = comando;
    }

    public void annullaComando(){
        if(comandoCorrente == null)
            throw new IllegalStateException("Nessun comando da annullare");
        comandoCorrente.undo();
        comandoCorrente = null;
    }

    public boolean haComandoDaAnnullare(){
        return comandoCorrente != null;
    }

}
