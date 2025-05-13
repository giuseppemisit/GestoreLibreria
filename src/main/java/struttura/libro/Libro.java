package struttura.libro;

public interface Libro {

    /**
     * Restituisce il titolo del libro
     * @return String
     */
    String getTitolo();

    Autore getAutore();

    int getISBN();

    

}
