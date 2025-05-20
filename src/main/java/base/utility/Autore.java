package base.utility;

public class Autore {

    private String nome;
    private String cognome;

    public Autore(String nome, String cognome) {
        if(nome == null || nome.isBlank())
            throw new IllegalArgumentException("Inserire un nome");
        if(cognome == null || cognome.isBlank())
            throw new IllegalArgumentException("Inserire un cognome");
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }
    public String getCognome() {
        return cognome;
    }

    public void setNome(String nome) {
        if(nome == null || nome.isBlank())
            throw new IllegalArgumentException("Inserire almeno un nome");
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public String toString() {
        return nome + " " + cognome;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof Autore)) return false;

        Autore a = (Autore) obj;
        if(!(this.nome.equals(a.nome))) return false;
        if(!(this.cognome.equals(a.cognome))) return false;
        return true;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + nome.hashCode();
        result = prime * result + cognome.hashCode();
        return result;
    }

}
