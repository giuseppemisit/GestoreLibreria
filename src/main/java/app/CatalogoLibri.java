package app;

import controllo.ConcreteLibreriaHub;
import controllo.LibreriaHub;

import javax.swing.*;

public class CatalogoLibri extends JFrame{

    private
    private LibreriaHub mediator;
    private Libreria

    public CatalogoLibri() {
        mediator = new ConcreteLibreriaHub()
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater( () -> { new CatalogoLibri().setVisible(true); } );
    }
}
