package app;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

        //visualizzaLibri(libri,"Giuseppe");
        List<String> utenti = new ArrayList<>();
        utenti.add("Giuseppe");
        utenti.add("Uberto");
        utenti.add("JK");
        System.out.println(homeGestioneUtenti(utenti));
    }




    public String homeGestioneUtenti(List<String> utenti) {

        final JDialog dialog = new JDialog((Frame) null, "Catalogo Libri", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Pannello principale con layout a bordi
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Aggiunta titolo
        mainPanel.add(creaTitoloConIcona("👤", "Area Utenti"), BorderLayout.NORTH);

        // Pannello con i pulsanti verticali
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Margini

        String[] testiPulsanti = {"Accedi", "Nuovo Utente", "Elimina Utente"};

        JButton accediButton = new JButton(testiPulsanti[0]);
        JButton nuovoUtenteButton = new JButton(testiPulsanti[1]);
        JButton eliminaUtenteButton = new JButton(testiPulsanti[2]);
        eliminaUtenteButton.setForeground(Color.RED); // Testo rosso per enfatizzare

        buttonPanel.add(accediButton);
        buttonPanel.add(nuovoUtenteButton);
        buttonPanel.add(eliminaUtenteButton);

        // Inserimento del pannello pulsanti al centro
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Variabile per salvare la scelta dell'utente
        AtomicReference<String> utenteSelezionato = new AtomicReference<>(null);

        // Action: Accedi
        accediButton.addActionListener(e -> {
            try {
                String utente = selezionaUtenteEsistente(utenti);
                if (utente != null) {
                    utenteSelezionato.set(utente);
                    dialog.dispose(); // Chiudi finestra
                }
            } catch (Exception ex) {
                mostraErrore(ex.getMessage());
            }
        });

        // Action: Crea Nuovo Utente
        nuovoUtenteButton.addActionListener(e -> {
            try {
                String nuovo = creaNuovoUtente();
                if (nuovo != null) {
                    utenteSelezionato.set(nuovo);
                    dialog.dispose();
                }
            } catch (Exception ex) {
                mostraErrore(ex.getMessage());
            }
        });

        // Action: Elimina Utente
        eliminaUtenteButton.addActionListener(e -> {
            try {
                boolean eliminato = eliminaUtente();
                if (eliminato) {
                    utenti.remove(utenteSelezionato.get());
                    dialog.dispose();
                }
                else {
                    mostraErrore("L'utente non esiste");
                }
            }catch (Exception ex){
                mostraErrore(ex.getMessage());
            }
        });

        // Configurazione e visualizzazione della finestra
        dialog.setContentPane(mainPanel);
        dialog.setSize(350, 400);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null); // Centra sullo schermo

        // Se viene chiusa la finestra termina il programma
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Bloccante finché non si chiude
        dialog.setVisible(true);

        // Ritorna l'utente selezionato o creato, oppure null
        return utenteSelezionato.get();
    }

    private JLabel creaTitoloConIcona(String emoji, String testo) {
        JLabel titleLabel = new JLabel(testo, JLabel.CENTER);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        titleLabel.setIcon(emojiToIcon(emoji));
        return titleLabel;
    }

    private void mostraErrore(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, "Errore", JOptionPane.ERROR_MESSAGE);
    }



    public String selezionaUtenteEsistente(List<String> utenti) {
        if (utenti == null || utenti.isEmpty()) {
            throw new IllegalArgumentException("Nessun utente registrato");
        }
        // Filtriamo eventuali elementi nulli dalla lista
        List<String> utentiValidi = utenti.stream()
                .filter(u -> u != null)
                .toList();

        if (utentiValidi.isEmpty())
            throw new IllegalArgumentException("La lista contiene solo elementi nulli");

        String[] opzioni = utentiValidi.toArray(new String[0]);
        String utenteSelezionato = (String) JOptionPane.showInputDialog(
                this,
                "Seleziona un utente:", // Messaggio da mostrare
                "Selezione Utente", // Titolo della finestra
                JOptionPane.QUESTION_MESSAGE, // Tipo di icona
                null, // Icona personalizzata (null->default)
                opzioni, // Array delle opzioni selezionabili
                opzioni[0]  // Valore predefinito (primo elemento)
        );
        if (utenteSelezionato == null)
            throw new RuntimeException("Selezione annullata");
        return utenteSelezionato;
    }

    public String creaNuovoUtente() {
        String nomeUtente = JOptionPane.showInputDialog(
                this,
                "Inserisci il nuovo Username:",
                "Creazione Nuovo Utente",
                JOptionPane.QUESTION_MESSAGE
        );
        if (nomeUtente == null || nomeUtente.trim().isEmpty())
            throw new RuntimeException("Operazione annullata");
        return nomeUtente.trim();
    }

    public boolean eliminaUtente(){

    }



    //*****************************


    private ImageIcon emojiToIcon(String emoji) {
        int dimensione = 40;
        // Creazione di un'etichetta con l'emoji
        JLabel emojiLabel = new JLabel(emoji);
        emojiLabel.setFont(new Font("Dialog", Font.PLAIN, dimensione));
        emojiLabel.setSize(emojiLabel.getPreferredSize());

        // Creazione di un'immagine della dimensione dell'etichetta
        BufferedImage image = new BufferedImage(
                emojiLabel.getWidth(),
                emojiLabel.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        // Rendering dell'etichetta nell'immagine
        Graphics g = image.getGraphics();
        emojiLabel.paint(g);
        g.dispose();

        // Creazione dell'icona dall'immagine
        return new ImageIcon(image);
    }





    public void paginaPrincipale() {

    }












    public void visualizzaLibri(List<Libro> libri, String username) {
        // Creazione del frame principale
        JFrame frame = new JFrame("Lista dei Libri: " + username);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);

        // Preparazione della tabella
        JTable tabella = creaTabellaDatiLibri(libri);
        JScrollPane scrollPane = new JScrollPane(tabella);

        // Preparazione dei pulsanti
        JPanel panelloPulsanti = creaPannelloPulsanti(frame, tabella, libri, username);

        // Configurazione del layout
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panelloPulsanti, BorderLayout.SOUTH);

        // Visualizzazione del frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JTable creaTabellaDatiLibri(List<Libro> libri) {
        // Configurazione delle colonne
        String[] colonneTabella = {"Titolo", "Autori", "ISBN"};
        Object[][] datiTabella = new Object[libri.size()][3];
        
        // Popolamento dei dati
        for (int i = 0; i < libri.size(); i++) {
            Libro libro = libri.get(i);
            datiTabella[i][0] = libro.getTitolo();
            datiTabella[i][1] = convertiAutoriInStringa(libro.getAutori());
            datiTabella[i][2] = libro.getIsbn();
        }
        
        // Creazione e configurazione tabella
        JTable tabella = new JTable(datiTabella, colonneTabella) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rende tutte le celle non modificabili
            }
            // Rimuove la cornice intorno alle celle nella riga selezionata
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);

                // Imposta il bordo a null per rimuovere qualsiasi cornice
                if (comp instanceof JComponent) {
                    ((JComponent) comp).setBorder(null);
                }

                return comp;
            }

        };

        tabella.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabella.setFillsViewportHeight(true);

        // Aggiungi un mouse listener per deselezionare quando si clicca in un'area vuota
        tabella.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tabella.rowAtPoint(e.getPoint());

                // Deseleziona se si clicca in un'area vuota
                if (row == -1) {
                    tabella.clearSelection();
                    return;
                }

                // Gestione doppio click per selezionare e mostrare dettagli
                if (e.getClickCount() == 2 && row != -1) {
                    Libro libroSelezionato = libri.get(row);
                    mostraInfoLibro(libroSelezionato);
                }
            }
        });
        
        return tabella;
    }

    private String convertiAutoriInStringa(Collection<Autore> autori) {
        StringBuilder autoriString = new StringBuilder();
        for (Autore autore : autori) {
            if (autoriString.length() > 0) {
                autoriString.append(", ");
            }
            autoriString.append(autore.toString());
        }
        return autoriString.toString();
    }

    private JPanel creaPannelloPulsanti(JFrame frame, JTable tabella, List<Libro> libri, String username) {
        JPanel panelloPulsanti = new JPanel();
        
        // Pulsante Esci
        JButton pulsanteEsci = new JButton("Esci");
        pulsanteEsci.addActionListener(e -> frame.dispose());
        
        // Pulsante Aggiungi Libro
        JButton pulsanteAggiungiLibro = new JButton("Nuovo Libro");
        pulsanteAggiungiLibro.addActionListener(e -> {
            Libro nuovoLibro = creaNuovoLibro();
            if (nuovoLibro != null) {
                libri.add(nuovoLibro);
                frame.dispose();
                visualizzaLibri(libri, username); // Riapre la finestra con il libro aggiunto
            }
        });
        
        // Pulsante Seleziona
        JButton pulsanteSeleziona = new JButton("Seleziona");
        pulsanteSeleziona.addActionListener(e -> {
            int rigaSelezionata = tabella.getSelectedRow();
            if (rigaSelezionata != -1) {
                Libro libroSelezionato = libri.get(rigaSelezionata);
                mostraInfoLibro(libroSelezionato);
            } else {
                JOptionPane.showMessageDialog(
                    frame,
                    "Seleziona un libro prima di premere questo pulsante",
                    "Nessuna selezione",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        });
        
        // Aggiunta dei pulsanti al pannello
        panelloPulsanti.add(pulsanteEsci);
        panelloPulsanti.add(pulsanteAggiungiLibro);
        panelloPulsanti.add(pulsanteSeleziona);
        
        return panelloPulsanti;
    }





    public void mostraInfoLibro(Libro libro) {
        JPanel panel = panelInfoLibro(libro);
        JOptionPane.showMessageDialog(
                this,
                panel,
                "Informazioni Libro",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    public Libro confermaLibroSelezionato(Libro libro) {
        JPanel panel = panelInfoLibro(libro);
        Object[] options = {"OK", "Annulla"};
        int result = JOptionPane.showOptionDialog(
                this,
                panel,
                "Informazioni Libro",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );
        if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION)
            return null;
        return libro;
    }



    public Libro selezionaLibroEsistente(List<Libro> libri) {
        /**
         * 1. Mostra una lista con String titolo dei libri nella lista
         * 2. seleziono e premo seleziona -> confermaLibroSelezionato(Libro)
         * 3. Se confermaLibroSelezionato(Libro) return Libro = this.Libro
         *      return this.libri
         *    else if return == null:
         *      non fare nulla, tieni la finestra aperta.
         * pulsanti:
         * Seleziona: -> apre ConfermaLibroSelezionato(Libro)
         * Annulla/chiudi finestra -> return null
         */
        // Controllo se la lista è vuota
        if (libri == null || libri.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nessun libro disponibile",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE
            );
            return null;
        }

        // Creo un array di titoli dei libri per visualizzarli
        String[] titoli = new String[libri.size()];
        for (int i = 0; i < libri.size(); i++) {
            titoli[i] = libri.get(i).getTitolo();
        }

        // Mostro una finestra di dialogo per selezionare un libro
        String titoloSelezionato = (String) JOptionPane.showInputDialog(
                this,
                "Seleziona un libro:",
                "Selezione Libro",
                JOptionPane.QUESTION_MESSAGE,
                null,
                titoli,
                titoli[0]
        );

        // Se l'utente annulla la selezione
        if (titoloSelezionato == null) {
            return null;
        }

        // Trovo il libro corrispondente al titolo selezionato
        Libro libroSelezionato = null;
        for (Libro libro : libri) {
            if (libro.getTitolo().equals(titoloSelezionato)) {
                libroSelezionato = libro;
                break;
            }
        }

        // Conferma la selezione del libro
        return confermaLibroSelezionato(libroSelezionato);
    }



    public Libro creaNuovoLibro() {
        /**
         * 1. Titolo : Stringa
         * 2. Autori : JTextFild dei generi nella List<Autori>
         *     Subito sotto Nome: String (non null)
         *                  Cognome: String (non null)
         *                  pulsante Aggiungi -> aggiunge il nuovo autore alla List<Autori>
         *
         * 3. Genere : JTextFild dei generi nella List<InfoText.genere>
         *        subito sotto: menu a tendina:
         *                      default -> nessuno
         *                      Altri campi -> InfoText.genere
         *                      pulsante Aggiungi -> solo se =! nessuno
         *
         * 4. Valutazione: tendina delle opzioni disponibili InfoExtra.valutazione:default -> nessuna
         * 5. Stato di lettura: tendina delle opzioni disponibili InfoExtra.statoLettura: default -> nessuno
         * 6. isbn: String (solo numeri e lunghezza 13 caratteri)
         */
        return null;
    }


    public Libro modificaLibro(Libro originale){
        /**
         *
         */
        return null;
    }


    public Autore creaNuovoAutore() {
        /**
         * 1. Nome : String
         * 2. Cognome: String
         * Pulsanti:
         * Ok -> restiruisce l'autore
         * Annulla/chiudi pannello -> restituisce null
         */
        return null;
    }


    public TipoOrdinamento selezionaOrdinamento() {
        return null;
    }


    public ParametriRicerca creaParametriRicerca() {
        return null;
    }


    public ParametriFiltro creaParametriFiltro() {
        return null;
    }


    public InfoExtra.GenereLibro selezionaGenere() {
        return null;
    }


    public InfoExtra.Valutazione selezionaValutazione() {
        return null;
    }


    public InfoExtra.StatoLettura selezionaStatoLettura() {
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater( () -> { new ProvaGrafica().setVisible(true); } );
    }


    private JPanel panelInfoLibro(Libro libro){
        // Creiamo un panel in cui inserire le informazioni
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Ottengo le informazioni dal libro
        JTextField titoloField = new JTextField(libro.getTitolo());

        StringBuilder autoriString = new StringBuilder();
        for (Autore autore : libro.getAutori()) {
            if (autoriString.length() > 0) {
                autoriString.append(", ");
            }
            autoriString.append(autore.toString());
        }
        JTextField autoriField = new JTextField(autoriString.toString());

        StringBuilder generiString = new StringBuilder();
        for (InfoExtra.GenereLibro genere : libro.getGeneri()) {
            if (generiString.length() > 0) {
                generiString.append(", ");
            }
            generiString.append(genere.toString());
        }
        JTextField genereField = new JTextField(generiString.toString());

        JTextField valutazioneField = new JTextField(libro.getValutazione().getStelle() + " stelle");

        JTextField statoLetturaField = new JTextField(libro.getStatoLettura().toString());

        JTextField isbnField = new JTextField(libro.getIsbn());

        // Impostiamo i campi come non modificabili
        titoloField.setEditable(false);
        autoriField.setEditable(false);
        genereField.setEditable(false);
        valutazioneField.setEditable(false);
        statoLetturaField.setEditable(false);
        isbnField.setEditable(false);

        // Si aggiungono le etichette e i campi al pannello
        panel.add(new JLabel("Titolo:"));
        panel.add(titoloField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Autori:"));
        panel.add(autoriField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Genere:"));
        panel.add(genereField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Valutazione:"));
        panel.add(valutazioneField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Stato di lettura:"));
        panel.add(statoLetturaField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("ISBN:"));
        panel.add(isbnField);

        return panel;
    }

    private List<Autore> nuovaListaAutori(){
        /**
         * 1. JTextFild degli autori aggiungi nella List
         * pulsanti:
         * Salva -> resituisce la List<Autore>
         * Nuovo autore -> apre creaNuovoAutore()
         * Annulla/chiudi pannel -> resituisce null
         */
        return null;
    }

    private List<InfoExtra.GenereLibro> nuovaListaGeneri(){
        /**
         * 1. JTextFild dei generi nella List
         * menù a tendina:
         * valore di default: -> nessuno
         * altri valori: -> InfoExtra.GenereLibro
         *
         * pulsati:
         * Aggiungi Selezionato -> aggiunge il genere selezionato alla List
         * Salva -> Restituisce la List
         * Annula/chiudi finestra -> restituisce null
         */
        return null;
    }


}