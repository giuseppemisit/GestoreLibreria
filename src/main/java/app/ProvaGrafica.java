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
        // Libro 2
        String isbn2 = "9788804681960";
        String titolo2 = "1984";
        InfoExtra.Valutazione valutazione2 = new InfoExtra.Valutazione(4);
        InfoExtra.StatoLettura statoLettura2 = InfoExtra.StatoLettura.DA_LEGGERE;
        Autore autore3 = new Autore("George", "Orwell");
        Set<Autore> autori2 = new HashSet<>();
        autori2.add(autore3);
        Set<InfoExtra.GenereLibro> generi2 = new HashSet<>();
        generi2.add(InfoExtra.GenereLibro.MANGA);
        generi2.add(InfoExtra.GenereLibro.FANTASCIENZA);
        Libro libro2 = new ConcreteLibro(isbn2, titolo2, valutazione2, statoLettura2, autori2, generi2);

// Libro 3
        String isbn3 = "9788804678731";
        String titolo3 = "Il Signore degli Anelli";
        InfoExtra.Valutazione valutazione3 = new InfoExtra.Valutazione(5);
        InfoExtra.StatoLettura statoLettura3 = InfoExtra.StatoLettura.IN_LETTURA;
        Autore autore4 = new Autore("J.R.R.", "Tolkien");
        Set<Autore> autori3 = new HashSet<>();
        autori3.add(autore4);
        Set<InfoExtra.GenereLibro> generi3 = new HashSet<>();
        generi3.add(InfoExtra.GenereLibro.FANTASY);
        Libro libro3 = new ConcreteLibro(isbn3, titolo3, valutazione3, statoLettura3, autori3, generi3);

// Libro 4
        String isbn4 = "9788850251276";
        String titolo4 = "La solitudine dei numeri primi";
        InfoExtra.Valutazione valutazione4 = new InfoExtra.Valutazione(3);
        InfoExtra.StatoLettura statoLettura4 = InfoExtra.StatoLettura.LETTO;
        Autore autore5 = new Autore("Paolo", "Giordano");
        Set<Autore> autori4 = new HashSet<>();
        autori4.add(autore5);
        Set<InfoExtra.GenereLibro> generi4 = new HashSet<>();
        generi4.add(InfoExtra.GenereLibro.BIOGRAFIA);
        Libro libro4 = new ConcreteLibro(isbn4, titolo4, valutazione4, statoLettura4, autori4, generi4);

// Libro 5
        String isbn5 = "9788804668237";
        String titolo5 = "Harry Potter e la Pietra Filosofale";
        InfoExtra.Valutazione valutazione5 = new InfoExtra.Valutazione(4);
        InfoExtra.StatoLettura statoLettura5 = InfoExtra.StatoLettura.LETTO;
        Autore autore6 = new Autore("J.K.", "Rowling");
        Set<Autore> autori5 = new HashSet<>();
        autori5.add(autore6);
        Set<InfoExtra.GenereLibro> generi5 = new HashSet<>();
        generi5.add(InfoExtra.GenereLibro.FANTASY);
        generi5.add(InfoExtra.GenereLibro.AVVENTURA);
        Libro libro5 = new ConcreteLibro(isbn5, titolo5, valutazione5, statoLettura5, autori5, generi5);

// Libro 6
        String isbn6 = "9780140283334";
        String titolo6 = "Norwegian Wood";
        InfoExtra.Valutazione valutazione6 = new InfoExtra.Valutazione(4);
        InfoExtra.StatoLettura statoLettura6 = InfoExtra.StatoLettura.DA_LEGGERE;
        Autore autore7 = new Autore("Haruki", "Murakami");
        Set<Autore> autori6 = new HashSet<>();
        autori6.add(autore7);
        Set<InfoExtra.GenereLibro> generi6 = new HashSet<>();
        generi6.add(InfoExtra.GenereLibro.RAGAZZI);
        generi6.add(InfoExtra.GenereLibro.VIAGGIO);
        Libro libro6 = new ConcreteLibro(isbn6, titolo6, valutazione6, statoLettura6, autori6, generi6);

        List<Libro> libri = new ArrayList<>();



    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ProvaGrafica();
        });
    }










}