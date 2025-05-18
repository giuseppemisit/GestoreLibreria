package ordine;

import base.libro.Libro;

import java.util.List;

public class GestioneOrdinamento {

    private Ordinamento ordinamento;

    public void setOrdinamento(Ordinamento ordinamento) {
        this.ordinamento = ordinamento;
    }

    public void esegui(List<Libro> libri){
        ordinamento.ordina(libri);
    }
}
