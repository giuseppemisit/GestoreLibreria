package app;

import base.libro.Libro;
import base.utility.Autore;
import base.utility.InfoExtra;
import esplora.interroga.ParametriFiltro;
import esplora.interroga.ParametriRicerca;
import esplora.ordina.utility.TipoOrdinamento;

import java.util.List;

public interface UserInterface {

    String homeGestioneUtenti(List<String> utenti);

    String selezionaUtenteEsistente(List<String> utenti);




}
