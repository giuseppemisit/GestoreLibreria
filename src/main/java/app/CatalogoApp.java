package app;

import app.pannelli.SelezioneUtente;
import controllo.utenti.ConcreteUserManager;
import controllo.utenti.UserManager;
import controllo.libreria.HubLibreria;

import javax.swing.*;

public class CatalogoApp extends JFrame {

    private UserManager utenti;
    private HubLibreria hubLibreria;

    public CatalogoApp() {
        utenti = new ConcreteUserManager();
        String username = new SelezioneUtente().pannelloPrincipale(utenti);


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CatalogoApp();
        });
    }


}
