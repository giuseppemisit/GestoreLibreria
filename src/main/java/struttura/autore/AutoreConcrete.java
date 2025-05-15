package struttura.autore;

public class AutoreConcrete implements Autore{

    private String nome;
    private String cognome;
    private String biografia;

    public AutoreConcrete(String nome, String cognome, String biografia) {

        if(nome == null || nome.isBlank())
            throw new IllegalArgumentException("Non è stato inserito alcun nome");

        //Il cognome e la biografia non li considero obbligatori.

        this.nome = nome;
        this.cognome = cognome;
        this.biografia = biografia;
    }

    @Override
    public String nomeAutore() {
        return nome + " " + cognome;
    }

    @Override
    public String infoAutore() {
        StringBuilder sb = new StringBuilder();
        sb.append("AUTORE: ").append(nome).append(" ").append(cognome).append("\n");

        if(biografia == null || biografia.isBlank())
            sb.append("\nBIOGRAFIA: biografia non presente!\n");
        else
            sb.append("\nBIOGRAFIA:\n").append(biografia);

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof AutoreConcrete)) return false;

        AutoreConcrete a = (AutoreConcrete) obj;
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