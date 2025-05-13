package struttura.informazioni;

public class Autore {

    private String nome;
    private String cognome;
    private String biografia;

    public Autore(String nome, String cognome, String biografia) {
        this.nome = nome;
        this.cognome = cognome;
        this.biografia = biografia;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getBiografia() {
        return biografia;
    }

}