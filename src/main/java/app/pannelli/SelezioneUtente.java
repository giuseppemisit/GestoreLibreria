package app.pannelli;

import controllo.utenti.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SelezioneUtente {

    public SelezioneUtente() {}

    public String pannelloPrincipale(UserManager utenti) {
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

        accediBtn.setFocusPainted(false);
        nuovoBtn.setFocusPainted(false);
        eliminaBtn.setFocusPainted(false);

        accediBtn.addActionListener(e -> {
            try {
                String utente = selezionaUtenteEsistente(dialog, utenti.utentiRegistrati());
                if (utente != null && !utente.isEmpty()) {
                    utenteSelezionato.set(utente);
                    dialog.dispose();
                }
            } catch (Exception ex) {
                mostraWarning(dialog, ex.getMessage());
            }
        });

        nuovoBtn.addActionListener(e -> {
            try {
                String utenteCreato = creaNuovoUtente(dialog);
                boolean esito = utenti.creaNuovoUtente(utenteCreato);
                if (esito) mostraMessaggio(dialog, "Utente creato con successo!");
                else mostraErrore(dialog, "Errore: Salvataggio non effettuato");
            } catch (Exception ex) {
                mostraWarning(dialog, ex.getMessage());
            }
        });

        eliminaBtn.addActionListener(e -> {
            try {
                eliminaUtente(dialog, utenti);
            }
            catch (Exception ex) {
                mostraWarning(dialog, ex.getMessage());
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

    private String selezionaUtenteEsistente(Component parent, List<String> utenti) {
        if (utenti == null || utenti.isEmpty())
            throw new IllegalArgumentException("Nessun utente registrato");

        String[] utentiValidi = utenti.stream()
                .filter(u -> u != null && !u.trim().isEmpty())
                .toArray(String[]::new);

        if (utentiValidi.length == 0)
            throw new IllegalArgumentException("Nessun utente valido");

        String[] opzioni = new String[utentiValidi.length + 1];
        opzioni[0] = "--";
        System.arraycopy(utentiValidi, 0, opzioni, 1, utentiValidi.length);

        String selezionato = (String) JOptionPane.showInputDialog(
                parent, "Seleziona un utente:", "Selezione Utente",
                JOptionPane.QUESTION_MESSAGE, null, opzioni, opzioni[0]
        );

        if (selezionato == null)
            throw new RuntimeException("Selezione annullata");

        return selezionato.equals("--") ? null : selezionato;
    }

    private String creaNuovoUtente(Component parent) {
        String nome = JOptionPane.showInputDialog(
                parent, "Inserisci il nuovo Username:", "Creazione Nuovo Utente",
                JOptionPane.QUESTION_MESSAGE
        );

        if (nome == null || nome.trim().isEmpty())
            throw new RuntimeException("Operazione annullata");
        return nome.trim();
    }

    private void eliminaUtente(Component parent, UserManager utenti) {
        String utente = selezionaUtenteEsistente(parent, utenti.utentiRegistrati());
        if (utente != null) {
            int conferma = JOptionPane.showConfirmDialog(
                    parent,
                    "Sei sicuro di voler eliminare l'utente '" + utente + "' ?",
                    "Conferma eliminazione",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (conferma == JOptionPane.OK_OPTION) {
                boolean esito = utenti.eliminaUtente(utente);
                if (esito) {
                    mostraMessaggio(parent, "Utente eliminato con successo!");
                } else {
                    mostraErrore(parent, "Errore: Utente non eliminato.");
                }
            }
        }
    }

    private void mostraMessaggio(Component parent, String messaggio) {
        JOptionPane.showMessageDialog(parent, messaggio, "Informazione", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostraWarning(Component parent, String messaggio) {
        JOptionPane.showMessageDialog(parent, messaggio, "Attenzione", JOptionPane.WARNING_MESSAGE);
    }

    private void mostraErrore(Component parent, String messaggio) {
        JOptionPane.showMessageDialog(parent, messaggio, "Errore", JOptionPane.ERROR_MESSAGE);
    }

}
