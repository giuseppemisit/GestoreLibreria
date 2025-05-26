package app;

import com.sun.tools.javac.Main;
import controllo.LibreriaHub;
import base.libro.ConcreteLibro;
import base.libro.Libro;
import base.utility.Autore;
import base.utility.InfoExtra;
import esplora.interroga.ParametriFiltro;
import esplora.interroga.ParametriRicerca;
import esplora.ordina.utility.TipoOrdinamento;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicReference;

public class ProvaGrafica extends JFrame {

    private LibreriaHub mediatore;

    public ProvaGrafica() {
        String isbn = "9788804668237";
        String titolo = "Il Nome della Rosa";
        InfoExtra.Valutazione valutazione = new InfoExtra.Valutazione(5);
        InfoExtra.StatoLettura statoLettura = InfoExtra.StatoLettura.LETTO;
        Autore autore1 = new Autore("Uberto", "Eco");
        Autore autore2 = new Autore("JK", "Rownlig");
        Set<Autore> autori = new HashSet<>();
        autori.add(autore1);
        autori.add(autore2);
        Set<InfoExtra.GenereLibro> generi = new HashSet<>();
        generi.add(InfoExtra.GenereLibro.STORICO);
        generi.add(InfoExtra.GenereLibro.GIALLO);
        Libro libro1 = new ConcreteLibro(isbn, titolo, valutazione, statoLettura, autori, generi);

        String isbn2 = "9788804668237";
        String titolo2 = "Cicale";
        InfoExtra.Valutazione valutazione2 = new InfoExtra.Valutazione(5);
        InfoExtra.StatoLettura statoLettura2 = InfoExtra.StatoLettura.LETTO;
        Autore autore12 = new Autore("casa", "ercolo");
        Autore autore22 = new Autore("JK", "Rownlig");
        Set<Autore> autori2 = new HashSet<>();
        autori2.add(autore12);
        autori2.add(autore22);
        Set<InfoExtra.GenereLibro> generi2 = new HashSet<>();
        generi.add(InfoExtra.GenereLibro.STORICO);
        generi.add(InfoExtra.GenereLibro.GIALLO);
        Libro libro2 = new ConcreteLibro(isbn2, titolo2, valutazione2, statoLettura2, autori2, generi2);

        List<Libro> libri = new ArrayList<>();
        libri.add(libro1);
        libri.add(libro2);



    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ProvaGrafica();
        });
    }










}