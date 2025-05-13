package struttura.informazioni;

public class Genere {

    private String nome;
    private String categoria;
    private String descrizione;

    public Genere(String nome, String categoria, String descrizione) {
        this.nome = nome;
        this.categoria = categoria;
        this.descrizione = descrizione;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }
}
