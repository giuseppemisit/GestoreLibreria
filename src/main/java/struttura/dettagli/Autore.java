package struttura.dettagli;

public class Autore {

    private String nome;
    private String cognome;
    private String biografia;

    public Autore(String nome, String cognome, String biografia) {
        this.nome = nome;
        this.cognome = cognome;
        this.biografia = biografia;
    }

    public String getNome(){
        return nome;
    }

    public String getCognome(){
        return cognome;
    }

    public String getBiografia(){
        return biografia;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Autore: ").append(nome).append(" ").append(cognome).append("\n");
        sb.append("Biografia:\n").append(biografia).append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof Autore)) return false;

        Autore a = (Autore) obj;
        if(!(a.nome.equals(this.nome))) return false;
        if(!(a.cognome.equals(this.cognome))) return false;
        if(!(a.biografia.equals(this.biografia))) return false;
        return true;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + nome.hashCode();
        result = prime * result + cognome.hashCode();
        result = prime * result + biografia.hashCode();
        return result;
    }

}