package app;

import base.utenti.ConcreteUserManager;
import controllo.LibreriaHub;
import base.utenti.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class CatalogoLibri extends JFrame implements UserInterface{

    private UserManager utenti;
    private LibreriaHub libreria;

    public CatalogoLibri() {
        utenti = new ConcreteUserManager();
        System.out.println(homeGestioneUtenti());
    }


    // SELEZIONE E GESTIONE DEGLI UTENTI
    @Override
    public String homeGestioneUtenti() {
        JDialog dialog = new JDialog((Frame) null, "Catalogo Libri", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(350, 400);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);

        AtomicReference<String> utenteSelezionato = new AtomicReference<>();

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Titolo
        JLabel titolo = new JLabel("👤 Area utente", JLabel.CENTER);
        titolo.setFont(new Font("Dialog", Font.BOLD, 18));
        titolo.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        mainPanel.add(titolo, BorderLayout.NORTH);

        // Pulsanti
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton accediBtn = new JButton("Accedi");
        JButton nuovoBtn = new JButton("Nuovo Utente");
        JButton eliminaBtn = new JButton("Elimina Utente");
        eliminaBtn.setForeground(Color.RED);

        accediBtn.addActionListener(e -> {
            try {
                String utente = selezionaUtenteEsistente(utenti.utentiRegistrati());
                utenteSelezionato.set(utente);
                dialog.dispose();
            } catch (Exception ex) {
                mostraErrore(ex.getMessage());
            }
        });

        nuovoBtn.addActionListener(e -> {
            try {
                String utenteCreato = creaNuovoUtente();
                boolean esito = utenti.creaNuovoUtente(utenteCreato);
                if (esito) mostraMessaggio("Utente creato con successo!");
                else mostraErrore("Errore: Salvataggio non effettuato");
            } catch (Exception ex) {
                mostraErrore(ex.getMessage());
            }
        });

        eliminaBtn.addActionListener(e -> {
            try {
                String utente = selezionaUtenteEsistente(utenti.utentiRegistrati());
                boolean esito = utenti.eliminaUtente(utente);
                if (esito) mostraMessaggio("Utente eliminato con successo!");
                else mostraErrore("Errore: Utente non eliminato");
            } catch (Exception ex) {
                mostraErrore(ex.getMessage());
            }
        });

        buttonPanel.add(accediBtn);
        buttonPanel.add(nuovoBtn);
        buttonPanel.add(eliminaBtn);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        dialog.add(mainPanel);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        dialog.setVisible(true);
        return utenteSelezionato.get();
    }

    @Override
    public String selezionaUtenteEsistente(Set<String> utenti) {
        if (utenti == null || utenti.isEmpty())
            throw new IllegalArgumentException("Nessun utente registrato");

        String[] utentiValidi = utenti.stream()
                .filter(u -> u != null && !u.trim().isEmpty())
                .toArray(String[]::new);

        if (utentiValidi.length == 0)
            throw new IllegalArgumentException("Nessun utente valido");

        String selezionato = (String) JOptionPane.showInputDialog(
                this, "Seleziona un utente:", "Selezione Utente",
                JOptionPane.QUESTION_MESSAGE, null, utentiValidi, utentiValidi[0]
        );

        if (selezionato == null)
            throw new RuntimeException("Selezione annullata");
        return selezionato;
    }

    @Override
    public String creaNuovoUtente() {
        String nome = JOptionPane.showInputDialog(
                this, "Inserisci il nuovo Username:", "Creazione Nuovo Utente",
                JOptionPane.QUESTION_MESSAGE
        );

        if (nome == null || nome.trim().isEmpty())
            throw new RuntimeException("Operazione annullata");
        return nome.trim();
    }


    // LIBRERIA DELL'UTENTE


    // METODI COMUNI UTILITY
    private void mostraErrore(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, "Errore", JOptionPane.ERROR_MESSAGE);
    }

    private void mostraMessaggio(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, "Informazione", JOptionPane.INFORMATION_MESSAGE);
    }






    public static void main(String[] args) {
        SwingUtilities.invokeLater( () -> { new CatalogoLibri().setVisible(true); } );
    }
}
