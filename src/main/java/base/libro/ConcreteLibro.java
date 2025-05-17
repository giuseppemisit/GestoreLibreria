package base.libro;

import base.Autore;
import base.InfoExtra;

import java.util.HashSet;
import java.util.Set;

public class ConcreteLibro extends AbstractLibro {

    public ConcreteLibro(String isbn,
                         String titolo,
                         InfoExtra.Valutazione valutazione,
                         InfoExtra.StatoLettura statoLettura,
                         Set<Autore> autori,
                         Set<InfoExtra.GenereLibro> genere) {

        super(isbn, titolo, valutazione, statoLettura, autori, genere);
    }

    @Override
    public Libro clone() {
        return new ConcreteLibro(
                this.getIsbn(),
                this.getTitolo(),
                this.getValutazione(),
                this.getStatoLettura(),
                new HashSet<>(this.getAutori()),
                new HashSet<>(this.getGeneri())
        );
    }

}
