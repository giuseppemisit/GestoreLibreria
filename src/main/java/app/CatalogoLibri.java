package app;

import app.utenti.ConcreteUserManager;
import base.libreria.Libreria;
import base.libreria.LibreriaJson;
import base.libro.Libro;
import controllo.ConcreteLibreriaHub;
import controllo.LibreriaHub;
import app.utenti.UserManager;
import esplora.ordina.utility.TipoOrdinamento;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class CatalogoLibri extends JFrame {

    private UserManager utenti;
    private LibreriaHub libreriaHub;

    public CatalogoLibri() {
        utenti = new ConcreteUserManager();
        String username = homeGestioneUtenti();

        if (username != null && !username.isEmpty()) {
            Libreria lib = new LibreriaJson(username);
            libreriaHub = new ConcreteLibreriaHub(lib);
            homeGestioneLibri();
        } else { System.exit(0);}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CatalogoLibri();
        });
    }


    // SELEZIONE E GESTIONE DEGLI UTENTI
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

        accediBtn.setFocusPainted(false);
        nuovoBtn.setFocusPainted(false);
        eliminaBtn.setFocusPainted(false);

        accediBtn.addActionListener(e -> {
            try {
                String utente = selezionaUtenteEsistente(utenti.utentiRegistrati());
                if (utente != null && !utente.isEmpty()) {
                    utenteSelezionato.set(utente);
                    dialog.dispose();
                }
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
            try { eliminaUtente(utenti); }
            catch (Exception ex) { mostraErrore(ex.getMessage()); }
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

    public String creaNuovoUtente() {
        String nome = JOptionPane.showInputDialog(
                this, "Inserisci il nuovo Username:", "Creazione Nuovo Utente",
                JOptionPane.QUESTION_MESSAGE
        );

        if (nome == null || nome.trim().isEmpty())
            throw new RuntimeException("Operazione annullata");
        return nome.trim();
    }

    public void eliminaUtente(UserManager utenti) {
        String utente = selezionaUtenteEsistente(utenti.utentiRegistrati());
        int conferma = JOptionPane.showConfirmDialog(
                this,
                "Sei sicuro di voler eliminare l'utente '" + utente + "' ?",
                "Conferma eliminazione",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (conferma == JOptionPane.OK_OPTION) {
            boolean esito = utenti.eliminaUtente(utente);
            if (esito) {
                mostraMessaggio("Utente eliminato con successo!");
            } else {
                mostraErrore("Errore: Utente non eliminato.");
            }
        }
    }


    // LIBRERIA DELL'UTENTE
    public void homeGestioneLibri(){
        // Creazione del frame principale
        JFrame frame = new JFrame("Catologo di " + libreriaHub.utenteCorrente());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Preparazione della tabella
        JTable tabella = creaTabellaPrincipale(libreriaHub.visualizzaLibri());
        JScrollPane scrollPane = new JScrollPane(tabella);

        // Preparazione dei pulsanti
        JPanel panelloPulsanti = creaPannelloPulsanti(
                frame,
                tabella,
                libreriaHub.visualizzaLibri(),
                libreriaHub.utenteCorrente()
        );

        // Configurazione del layout
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panelloPulsanti, BorderLayout.SOUTH);

        // Visualizzazione del frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JTable creaTabellaPrincipale(List<Libro> libri) {
        String[] colonne = {"Titolo", "Autori", "Genere", "ISBN"};
        Object[][] dati = new Object[libri.size()][4];

        // Popolo i dati
        for (int i = 0; i < libri.size(); i++) {
            Libro libro = libri.get(i);
            dati[i][0] = valoreOrPlaceholder(libro.getTitolo());
            dati[i][1] = valoreOrPlaceholder(convertiGruppoInString(libro.getAutori()));
            dati[i][2] = valoreOrPlaceholder((convertiGruppoInString(libro.getGeneri())));
            dati[i][3] = valoreOrPlaceholder(libro.getIsbn());
        }

        // Creo tabella personalizzata
        JTable tabella = new JTable(dati, colonne) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (comp instanceof JComponent) ((JComponent) comp).setBorder(null);
                return comp;
            }
        };

        tabella.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabella.setFillsViewportHeight(true);
        tabella.setFocusable(false);

        // Gestione click
        tabella.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tabella.rowAtPoint(e.getPoint());
                if (row == -1) { tabella.clearSelection(); } // Deseleziona se clicco su area vuota
                else if (e.getClickCount() == 2) { mostraInfoLibro(libri.get(row)); } // Doppio click
            }
        });
        return tabella;
    }


    /// questi li devo mogliorare
    ///
    private JPanel creaPannelloPulsanti(JFrame frame, JTable tabella, List<Libro> libri, String username) {
        JPanel pannelloPulsanti = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Pulsante Log out
        JButton pulsanteLogout = new JButton("Log out");
        pulsanteLogout.setForeground(Color.RED);
        pulsanteLogout.setFocusPainted(false);
        pulsanteLogout.addActionListener(e -> {
            frame.dispose(); // Chiude la finestra corrente
            this.main(new String[0]); // Riavvia l'applicazione
        });

        // Pulsante Dettagli
        JButton pulsanteDettagli = new JButton("Dettagli");
        pulsanteDettagli.setForeground(new Color(0x007CBE));
        pulsanteDettagli.setFocusPainted(false);
        pulsanteDettagli.addActionListener(e -> {
            int rigaSelezionata = tabella.getSelectedRow();
            if (rigaSelezionata != -1) {
                Libro libroSelezionato = libri.get(rigaSelezionata);
                mostraInfoLibro(libroSelezionato);
            } else {
                JOptionPane.showMessageDialog(
                        frame,
                        "Nessun libro selezionato",
                        "Nessuna selezione",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });


        // Pulsante Ordina per
        JButton pulsanteOrdina = new JButton("Ordina per");
        pulsanteOrdina.setFocusPainted(false);
        pulsanteOrdina.addActionListener(e -> {
            TipoOrdinamento tipo = selezionaOrdinamento(TipoOrdinamento.values());
            visualizzaConOrdinamento(libreriaHub.visualizzaLibriOrdinati(tipo), tipo);
        });

        // Pulsante Filtri
        JButton pulsanteFiltri = new JButton("Filtri");
        pulsanteFiltri.setFocusPainted(false);
        pulsanteFiltri.addActionListener(e -> {
            //Parametri parametri = selezionaParametriFiltro();
            //visualizzaRisultati(libreriaHub.filtraLibri(parametri));
        });

        // Pulsante Cerca
        JButton pulsanteCerca = new JButton("Cerca");
        pulsanteCerca.setFocusPainted(false);
        pulsanteCerca.addActionListener(e -> {
            //Parametri parametri = selezionaParametriRicerca();
            //visualizzaRisultati(libreriaHub.cercaLibri(parametri));
        });

        // Pulsante Aggiungi +
        JButton pulsanteAggiungiLibro = new JButton("Aggiungi +");
        pulsanteAggiungiLibro.setFocusPainted(false);
        pulsanteAggiungiLibro.addActionListener(e -> {
            //Libro nuovoLibro = creaNuovoLibro();
            //libreriaHub.aggiungiLibro(nuovoLibro);
        });

        // Aggiunta dei pulsanti al pannello, nell'ordine richiesto
        pannelloPulsanti.add(pulsanteLogout);
        pannelloPulsanti.add(pulsanteDettagli);
        pannelloPulsanti.add(pulsanteOrdina);
        pannelloPulsanti.add(pulsanteFiltri);
        pannelloPulsanti.add(pulsanteCerca);
        pannelloPulsanti.add(pulsanteAggiungiLibro);
        return pannelloPulsanti;
    }

    public void mostraInfoLibro(Libro libro) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String[] labels = {"Titolo:", "Autori:", "Genere:", "Valutazione:", "Stato di lettura:", "ISBN:"};
        String[] values = {
                libro.getTitolo(),
                libro.getAutori().stream().map(Object::toString).collect(Collectors.joining(", ")),
                libro.getGeneri().stream().map(Object::toString).collect(Collectors.joining(", ")),
                libro.getValutazione().getStelle() + " stelle",
                libro.getStatoLettura().toString(),
                libro.getIsbn()
        };

        for (int i = 0; i < labels.length; i++) {
            panel.add(new JLabel(labels[i]));
            JTextField field = new JTextField(values[i]);
            field.setEditable(false);
            panel.add(field);
            if (i < labels.length - 1) {
                panel.add(Box.createVerticalStrut(10));
            }
        }

        JOptionPane.showMessageDialog(this, panel, "Informazioni Libro", JOptionPane.INFORMATION_MESSAGE);
    }

    public TipoOrdinamento selezionaOrdinamento(TipoOrdinamento[] opzioni) {
        if (opzioni == null || opzioni.length == 0)
            throw new IllegalArgumentException("Nessuna opzione di ordinamento disponibile");

        TipoOrdinamento selezionato = (TipoOrdinamento) JOptionPane.showInputDialog(
                this,
                "Seleziona un criterio di ordinamento:",
                "Ordina per",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opzioni,
                opzioni[0]
        );
        if (selezionato == null)
            throw new RuntimeException("Ordinamento annullato");
        return selezionato;
    }

    private void visualizzaConOrdinamento(List<Libro> lista, TipoOrdinamento tipo){
        JFrame frame = new JFrame("Ordinamento per: " + tipo.name());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 600);

        JTable tabella = creaTabellaPrincipale(lista);
        JScrollPane scrollPane = new JScrollPane(tabella);

        JButton tornaHome = new JButton("Torna alla home");
        tornaHome.addActionListener(e -> frame.dispose());

        JPanel pannelloBottom = new JPanel(new FlowLayout());
        pannelloBottom.add(tornaHome);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(pannelloBottom, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }


    // METODI COMUNI UTILITY
    private void mostraErrore(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, "Errore", JOptionPane.ERROR_MESSAGE);
    }

    private void mostraMessaggio(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, "Informazione", JOptionPane.INFORMATION_MESSAGE);
    }

    private <T> String convertiGruppoInString(Collection<T> elementi) {
        StringBuilder result = new StringBuilder();
        for (T elemento : elementi) {
            if (result.length() > 0) { result.append(", "); }
            result.append(elemento.toString());
        }
        return result.toString();
    }

    private String valoreOrPlaceholder(String valore) {
        return (valore == null || valore.trim().isEmpty()) ? "--" : valore;
    }


}
