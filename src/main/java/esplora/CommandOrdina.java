package esplora;

import base.libro.Libro;
import esplora.ordina.OrdinamentoContext;
import esplora.ordina.utility.TipoOrdinamento;

import java.util.List;

public class CommandOrdina extends AbstractCommandEsplora {

    private final TipoOrdinamento tipoOrdinamento;

    public CommandOrdina(List<Libro> libri, TipoOrdinamento tipoOrdinamento){
        super(libri);
        if (tipoOrdinamento == null)
            throw new IllegalArgumentException("Il tipo di ordinamento non può essere null");

        this.tipoOrdinamento = tipoOrdinamento;
    }

    @Override
    public List<Libro> execute() {
        OrdinamentoContext context = new OrdinamentoContext();
        context.setOrdinamento(tipoOrdinamento);
        context.esegui(libri);
        return libri;
    }

}
