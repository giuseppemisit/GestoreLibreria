package app;

import app.pannelli.PaginaUtente;
import app.pannelli.SelezioneUtente;
import base.libreria.Libreria;
import base.libreria.LibreriaJson;
import controllo.libreria.ConcreteHubLibreria;
import controllo.utenti.ConcreteUserManager;
import controllo.utenti.UserManager;
import controllo.libreria.HubLibreria;

import javax.swing.*;
import java.awt.*;

public class CatalogoApp extends JFrame {

    private UserManager utenti;
    private HubLibreria hubLibreria;

    public CatalogoApp() {}

    public void start() {
        utenti = new ConcreteUserManager();
        String username = new SelezioneUtente().pannelloPrincipale(utenti);

        if (username != null && !username.isEmpty()) {
            Libreria lib = new LibreriaJson(username);
            hubLibreria = new ConcreteHubLibreria(lib);
            new PaginaUtente().pannelloPrincipale(hubLibreria);
        } else {
            System.exit(0);
        }
    }

    public static void restart() {
        for (Window window : Window.getWindows()) {
            window.dispose();
        }
        SwingUtilities.invokeLater(() -> {
            new CatalogoApp().start();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CatalogoApp().start();
        });
    }

}
