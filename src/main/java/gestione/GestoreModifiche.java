package gestione;

public class GestoreModifiche {

    private Modifiche comandoCorrente;

    public void eseguiComando(Modifiche comando){
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
