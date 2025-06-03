package app.pannelli;

import app.CatalogoApp;
import base.libro.ConcreteLibro;
import base.libro.Libro;
import base.utility.Autore;
import base.utility.InfoExtra;
import controllo.libreria.HubLibreria;
import esplora.interroga.Parametri;
import esplora.interroga.ParametriFiltro;
import esplora.interroga.ParametriRicerca;
import esplora.ordina.utility.TipoOrdinamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PaginaUtente implements Observer {

    private JTable tabella;
    private HubLibreria hubLibreria;

    public PaginaUtente() {}

    public void pannelloPrincipale(HubLibreria hubLibreria){
        this.hubLibreria = hubLibreria;

        JFrame frame = new JFrame("Catalogo di " + hubLibreria.utenteCorrente());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);

        this.tabella = creaTabella(hubLibreria.vistaLibreriaCorrente());
        JScrollPane scrollPane = new JScrollPane(tabella);

        // Pannello dei pulsanti
        JPanel pannelloPulsanti = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton pulsanteLogout = creaPulsanteLogout(frame);
        JButton pulsanteDettagli = creaPulsanteDettagli(tabella);
        JButton pulsanteOrdina = creaPulsanteOrdina();
        JButton pulsanteFiltri = creaPulsanteFiltri();
        JButton pulsanteCerca = creaPulsanteCerca();
        JButton pulsanteAggiungiLibro = creaPulsanteAggiungiLibro(tabella);
        JButton pulsanteAnnulla = creaPulsanteAnnullaModifica(tabella);
        JButton pulsanteRipristinaVista = creaPulsanteRipristinaVista(tabella);

        aggiungiPulsantiAlPannello(pannelloPulsanti,
                pulsanteLogout, pulsanteDettagli, pulsanteOrdina, pulsanteFiltri,
                pulsanteCerca, pulsanteAggiungiLibro, pulsanteAnnulla, pulsanteRipristinaVista);

        // Configurazione del layout
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(pannelloPulsanti, BorderLayout.SOUTH);

        // Registro l'Observer
        hubLibreria.aggiungiObserver(this);

        // Visualizzazione del frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private JTable creaTabella(List<Libro> libri) {
        DefaultTableModel model = creaModello(libri);

        JTable tabella = new JTable(model) {
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

        tabella.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tabella.rowAtPoint(e.getPoint());
                if (row == -1) {
                    tabella.clearSelection();
                } else if (e.getClickCount() == 2) {

                    List<Libro> libriAggiornati = hubLibreria.vistaLibreriaCorrente();

                    if (row >= 0 && row < libriAggiornati.size()) {
                        mostraInfoLibro(tabella, libriAggiornati.get(row));
                    }
                }
            }
        });

        return tabella;
    }

    private DefaultTableModel creaModello(List<Libro> libri) {
        String[] colonne = {"Titolo", "Autori", "Generi", "Valutazione", "Stato lettura", "ISBN"};
        Object[][] dati = new Object[libri.size()][colonne.length];

        for(int i = 0; i < libri.size(); i++){
            Libro libro = libri.get(i);
            dati[i][0] = libro.getTitolo() != null ? libro.getTitolo() : "--";
            dati[i][1] = !libro.getAutori().isEmpty() ? libro.getAutori().stream().map(Object::toString).collect(Collectors.joining(", ")) : "--";
            dati[i][2] = !libro.getGeneri().isEmpty() ? libro.getGeneri().stream().map(Object::toString).collect(Collectors.joining(", ")) : "--";
            dati[i][3] = libro.getValutazione() != null ? libro.getValutazione().getStelle() + " stelle" : "--";
            dati[i][4] = libro.getStatoLettura() != null ? libro.getStatoLettura().name() : "--";
            dati[i][5] = libro.getIsbn() != null ? libro.getIsbn() : "--";
        }

        return new DefaultTableModel(dati, colonne){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }


    private void configuraPulsanteBase(JButton pulsante) {
        pulsante.setFocusPainted(false);
    }

    private JButton creaPulsanteLogout(JFrame frame) {
        JButton pulsante = new JButton("Log out");
        pulsante.setForeground(Color.RED);
        configuraPulsanteBase(pulsante);

        pulsante.addActionListener(e -> {
            frame.dispose();
            CatalogoApp.restart();
        });

        return pulsante;
    }

    private JButton creaPulsanteDettagli(JTable tabella) {
        JButton pulsante = new JButton("Dettagli");
        pulsante.setForeground(new Color(0x007CBE)); // Blu
        configuraPulsanteBase(pulsante);

        pulsante.addActionListener(e -> gestisciVisualizzazioneDettagli(tabella));

        return pulsante;
    }

    private void gestisciVisualizzazioneDettagli(JTable tabella) {
        int rigaSelezionata = tabella.getSelectedRow();

        if (rigaSelezionata != -1) {
            Libro libroSelezionato = hubLibreria.vistaLibreriaCorrente().get(rigaSelezionata);
            mostraInfoLibro(tabella, libroSelezionato);
        } else {
            mostraWarning(tabella, "Nessun libro selezionato");
        }
    }

    private JButton creaPulsanteOrdina() {
        JButton pulsante = new JButton("Ordina per");
        configuraPulsanteBase(pulsante);

        pulsante.addActionListener(e -> {
            try {
                TipoOrdinamento tipo = selezionaOrdinamento(TipoOrdinamento.values());
                hubLibreria.ordinaLibri(tipo);
                mostraMessaggio(tabella, "Ordinamento per: " + tipo.toString());
            } catch (Exception ex) {
                mostraWarning(tabella, ex.getMessage());
            }
        });

        return pulsante;
    }

    private JButton creaPulsanteFiltri() {
        JButton pulsante = new JButton("Filtri");
        configuraPulsanteBase(pulsante);

        pulsante.addActionListener(e -> {
            try {
                Parametri parametriFiltro = selezionaParametriFiltro();

                if (parametriFiltro != null) {
                    if (parametriFiltro.isEmpty()) {
                        mostraWarning(tabella, "Nessun filtro selezionato");
                    } else {
                        hubLibreria.filtraLibri(parametriFiltro);
                    }
                }
            } catch (Exception ex) {
                mostraWarning(tabella, ex.getMessage());
            }
        });

        return pulsante;
    }

    private JButton creaPulsanteCerca() {
        JButton pulsante = new JButton("Cerca");
        configuraPulsanteBase(pulsante);

        pulsante.addActionListener(e -> {
            try {
                Parametri parametri = selezionaParametriRicerca();
                hubLibreria.cercaLibri(parametri);
                mostraMessaggio(tabella, "Ricerca effettuata!");
            } catch (Exception ex) {
                mostraWarning(tabella, ex.getMessage());
            }

        });

        return pulsante;
    }

    private JButton creaPulsanteAggiungiLibro(Component parent) {
        JButton pulsante = new JButton("Aggiungi +");
        configuraPulsanteBase(pulsante);

        pulsante.addActionListener(e -> gestisciAggiuntaLibro(parent));

        return pulsante;
    }

    private void gestisciAggiuntaLibro(Component parent) {
        Libro nuovoLibro = creaLibro(parent, null);

        if (nuovoLibro != null) {
            hubLibreria.eseguiAggiuntaLibro(nuovoLibro);
            mostraMessaggio(parent, "Libro aggiunto con successo!");
        }
    }

    private JButton creaPulsanteAnnullaModifica(Component parent) {
        JButton pulsante = new JButton("Annulla Modifica");
        pulsante.setForeground(new Color(0xFF8C00)); // Arancione
        configuraPulsanteBase(pulsante);

        pulsante.addActionListener(e -> gestisciAnnullaModifica(parent));

        return pulsante;
    }

    private void gestisciAnnullaModifica(Component parent) {
        try {
            hubLibreria.annullaUltimaGestione();
            mostraMessaggio(parent, "Ultimo aggiornamento annullato con successo!");
        } catch (Exception ex) {
            mostraErrore(parent, ex.getMessage());
        }
    }

    private JButton creaPulsanteRipristinaVista(Component parent) {
        JButton pulsante = new JButton("Ripristina Vista");
        pulsante.setForeground(new Color(0xFF8C00));
        configuraPulsanteBase(pulsante);

        pulsante.addActionListener(e -> gestisciRipristinoVista(parent));

        return pulsante;
    }

    private void gestisciRipristinoVista(Component parent) {
        try {
            hubLibreria.vistaLibreriaReset();
            mostraMessaggio(parent, "Reset Vista avvenuto con Successo!");
        } catch (Exception ex) {
            mostraErrore(parent, ex.getMessage());
        }
    }

    private void aggiungiPulsantiAlPannello(JPanel pannello, JButton... pulsanti) {
        for (JButton pulsante : pulsanti) {
            pannello.add(pulsante);
        }
    }

    public void mostraInfoLibro(Component parent, Libro libro) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(creaInfoPanel(libro));
        panel.add(Box.createVerticalStrut(15));

        panel.add(creaButtonPanel(panel, parent, libro));

        JOptionPane.showMessageDialog(tabella, panel, "Informazioni Libro", JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel creaInfoPanel(Libro libro) {
        String[] labels = {"Titolo:", "Autori:", "Generi:", "Valutazione:", "Stato di lettura:", "ISBN:"};
        String[] values = {
                getValueOrDefault(libro.getTitolo()),
                getListAsString(libro.getAutori()),
                getListAsString(libro.getGeneri()),
                getValutazioneString(libro.getValutazione()),
                getValueOrDefault(libro.getStatoLettura()),
                getValueOrDefault(libro.getIsbn())
        };

        JPanel infoPanel = new JPanel(new GridLayout(labels.length, 2, 10, 10));

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setHorizontalAlignment(SwingConstants.RIGHT);

            JTextField field = new JTextField(values[i]);
            field.setEditable(false);

            infoPanel.add(label);
            infoPanel.add(field);
        }

        return infoPanel;
    }

    private JPanel creaButtonPanel(JPanel panel, Component parent, Libro libro) {
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton btnModifica = new JButton("Modifica");
        JButton btnElimina = new JButton("Elimina");

        btnModifica.setFocusPainted(false);
        btnElimina.setFocusPainted(false);
        btnElimina.setForeground(Color.RED);

        btnModifica.addActionListener(e -> gestisciModifica(panel, parent, libro));
        btnElimina.addActionListener(e -> gestisciEliminazione(panel, libro));

        buttonPanel.add(btnModifica);
        buttonPanel.add(btnElimina);

        return buttonPanel;
    }

    private void gestisciModifica(JPanel panel, Component parent, Libro libro) {
        SwingUtilities.getWindowAncestor(panel).dispose();
        try {
            Libro libroModificato = creaLibro(parent, libro);
            hubLibreria.eseguiModificaLibro(libro, libroModificato);
        } catch (Exception ex) {
            mostraWarning(parent, ex.getMessage());
        }

    }

    private void gestisciEliminazione(Component parent, Libro libro) {
        Window finestra = SwingUtilities.getWindowAncestor(parent);
        String messaggio = "Eliminare \"" + libro.getTitolo() + "\"?";

        if (JOptionPane.showConfirmDialog(finestra, messaggio, "Conferma",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            finestra.dispose();

            try {
                hubLibreria.eseguiRimozioneLibro(libro);
                mostraMessaggio(tabella, "Libro eliminato con successo!");
            } catch (Exception ex) {
                mostraErrore(parent, ex.getMessage());
            }
        }
    }


    private Parametri selezionaParametriFiltro() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(tabella), "Filtri Libreria", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(tabella);

        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        mainPanel.add(new JLabel("Genere:"));
        JComboBox<InfoExtra.GenereLibro> genereCombo = creaComboGenere();
        mainPanel.add(genereCombo);

        mainPanel.add(new JLabel("Valutazione:"));
        JComboBox<InfoExtra.Valutazione> valutazioneCombo = creaComboValutazione();
        mainPanel.add(valutazioneCombo);

        mainPanel.add(new JLabel("Stato:"));
        JComboBox<InfoExtra.StatoLettura> statoCombo = creaComboStato();
        mainPanel.add(statoCombo);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton resetBtn = new JButton("Reset");
        JButton cancelBtn = new JButton("Annulla");
        JButton okBtn = new JButton("OK");

        final Parametri[] risultato = {null};

        okBtn.addActionListener(e -> {
            risultato[0] = new ParametriFiltro(
                    (InfoExtra.GenereLibro) genereCombo.getSelectedItem(),
                    (InfoExtra.Valutazione) valutazioneCombo.getSelectedItem(),
                    (InfoExtra.StatoLettura) statoCombo.getSelectedItem()
            );
            dialog.dispose();
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        resetBtn.addActionListener(e -> {
            genereCombo.setSelectedIndex(0);
            valutazioneCombo.setSelectedIndex(0);
            statoCombo.setSelectedIndex(0);
        });

        buttonPanel.add(resetBtn);
        buttonPanel.add(cancelBtn);
        buttonPanel.add(okBtn);

        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);

        return risultato[0];
    }

    private JComboBox<InfoExtra.GenereLibro> creaComboGenere() {
        JComboBox<InfoExtra.GenereLibro> combo = new JComboBox<>();
        combo.addItem(null);
        for (InfoExtra.GenereLibro genere : InfoExtra.GenereLibro.values()) {
            combo.addItem(genere);
        }
        combo.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value == null ? "-- Tutti --" : formattaEnum(value.name()));
            label.setOpaque(true);
            if (isSelected) {
                label.setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
            }
            return label;
        });
        return combo;
    }

    private JComboBox<InfoExtra.Valutazione> creaComboValutazione() {
        JComboBox<InfoExtra.Valutazione> combo = new JComboBox<>();
        combo.addItem(null);
        for (int i = 1; i <= 5; i++) {
            combo.addItem(new InfoExtra.Valutazione(i));
        }
        combo.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value == null ? "-- Tutte --" : value.getStelle() + " stelle");
            label.setOpaque(true);
            if (isSelected) {
                label.setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
            }
            return label;
        });
        return combo;
    }

    private JComboBox<InfoExtra.StatoLettura> creaComboStato() {
        JComboBox<InfoExtra.StatoLettura> combo = new JComboBox<>();
        combo.addItem(null);
        for (InfoExtra.StatoLettura stato : InfoExtra.StatoLettura.values()) {
            combo.addItem(stato);
        }
        combo.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value == null ? "-- Tutti --" : formattaEnum(value.name()));
            label.setOpaque(true);
            if (isSelected) {
                label.setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
            }
            return label;
        });
        return combo;
    }


    private Parametri selezionaParametriRicerca() {

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(tabella), "Ricerca Libri", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(tabella);

        JTextField titoloField = new JTextField(20);
        JTextField autoreField = new JTextField(20);
        JTextField isbnField = new JTextField(20);

        final Parametri[] risultato = {null};

        JPanel mainPanel = creaFormPanel(titoloField, autoreField, isbnField);
        JPanel buttonPanel = creaPulsanti(dialog, risultato, titoloField, autoreField, isbnField);

        dialog.setLayout(new BorderLayout());
        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);

        return risultato[0];
    }

    private JPanel creaFormPanel(JTextField titolo, JTextField autore, JTextField isbn) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        String[] labels = {"Titolo:", "Autore:", "ISBN:"};
        JTextField[] fields = {titolo, autore, isbn};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            panel.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1;
            panel.add(fields[i], gbc);
        }

        return panel;
    }

    private JPanel creaPulsanti(JDialog dialog, Parametri[] risultato, JTextField... fields) {
        JPanel panel = new JPanel(new FlowLayout());

        JButton resetBtn = new JButton("Reset");
        JButton annullaBtn = new JButton("Annulla");
        annullaBtn.setForeground(Color.red);
        JButton cercaBtn = new JButton("Cerca");
        cercaBtn.setForeground(new Color(0x007CBE));

        resetBtn.addActionListener(e -> Arrays.stream(fields).forEach(f -> f.setText("")));

        annullaBtn.addActionListener(e -> {
            risultato[0] = null;
            dialog.dispose();
        });

        cercaBtn.addActionListener(e -> {
            try {
                risultato[0] = creaParametriRicerca(fields[0], fields[1], fields[2]);
                dialog.dispose();
            } catch (Exception ex) {
                mostraWarning(dialog, ex.getMessage());
            }

        });

        KeyListener enterListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        risultato[0] = creaParametriRicerca(fields[0], fields[1], fields[2]);
                        dialog.dispose();
                    } catch (Exception ex) {
                        mostraWarning(dialog, ex.getMessage());
                    }
                }
            }
        };

        Arrays.stream(fields).forEach(field -> field.addKeyListener(enterListener));

        panel.add(resetBtn);
        panel.add(annullaBtn);
        panel.add(cercaBtn);

        return panel;
    }

    private Parametri creaParametriRicerca(JTextField titoloField, JTextField autoreField, JTextField isbnField) {
        String titolo = getTextOrNull(titoloField);
        String nomeCompleto = getTextOrNull(autoreField);
        String isbn = getTextOrNull(isbnField);

        Autore autore = (nomeCompleto != null) ? creaAutore(nomeCompleto) : null;

        return new ParametriRicerca(titolo, autore, isbn);
    }

    private String getTextOrNull(JTextField field) {
        String text = field.getText().trim();
        return text.isEmpty() ? null : text;
    }


    public Libro creaLibro(Component parent, Libro libro) {

        var titoloField = new JTextField(20);
        var autoriField = new JTextField(20);
        var generiList = new JList<>(InfoExtra.GenereLibro.values());
        generiList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        var valutazioneCombo = new JComboBox<>(new Integer[]{null, 1, 2, 3, 4, 5});

        InfoExtra.StatoLettura[] stati = InfoExtra.StatoLettura.values();
        InfoExtra.StatoLettura[] statiConNull = new InfoExtra.StatoLettura[stati.length + 1];
        statiConNull[0] = null;
        System.arraycopy(stati, 0, statiConNull, 1, stati.length);
        var statoCombo = new JComboBox<>(statiConNull);

        var isbnField = createIsbnField();

        if (libro != null) {
            popolaCampi(libro, titoloField, autoriField, generiList, valutazioneCombo, statoCombo, isbnField);
        }

        JPanel formPanel = creaFormPanel(titoloField, autoriField, new JScrollPane(generiList), valutazioneCombo, statoCombo, isbnField);

        if (mostraDialog(formPanel, libro != null)) {
            return creaLibroDaiCampi(parent, titoloField, autoriField, generiList, valutazioneCombo, statoCombo, isbnField);
        }

        return null;
    }

    private JTextField createIsbnField() {
        var isbnField = new JTextField(13);
        isbnField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) || isbnField.getText().length() >= 13) {
                    e.consume();
                }
            }
        });
        return isbnField;
    }

    private void popolaCampi(Libro libro, JTextField titoloField, JTextField autoriField,
                             JList<InfoExtra.GenereLibro> generiList, JComboBox<Integer> valutazioneCombo,
                             JComboBox<InfoExtra.StatoLettura> statoCombo, JTextField isbnField) {

        titoloField.setText(libro.getTitolo() != null ? libro.getTitolo() : "");
        isbnField.setText(libro.getIsbn() != null ? libro.getIsbn() : "");
        statoCombo.setSelectedItem(libro.getStatoLettura());

        if (libro.getAutori() != null) {
            String autoriText = libro.getAutori().stream()
                    .map(a -> a.getNome() + " " + a.getCognome())
                    .collect(Collectors.joining(", "));
            autoriField.setText(autoriText);
        }

        if (libro.getGeneri() != null && !libro.getGeneri().isEmpty()) {
            int[] indici = libro.getGeneri().stream()
                    .mapToInt(genere -> Arrays.asList(InfoExtra.GenereLibro.values()).indexOf(genere))
                    .toArray();
            generiList.setSelectedIndices(indici);
        }

        if (libro.getValutazione() != null) {
            valutazioneCombo.setSelectedItem(libro.getValutazione().getStelle());
        }
    }

    private JPanel creaFormPanel(JTextField titoloField, JTextField autoriField,
                                 JScrollPane generiScrollPane, JComboBox<Integer> valutazioneCombo,
                                 JComboBox<InfoExtra.StatoLettura> statoCombo, JTextField isbnField) {

        var formPanel = new JPanel(new GridBagLayout());
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        generiScrollPane.setPreferredSize(new Dimension(200, 80));

        addFormRowWithConstraints(formPanel, gbc, 0, "Titolo:", titoloField);
        addFormRowWithConstraints(formPanel, gbc, 1, "Autori:", autoriField);
        addFormRowWithConstraints(formPanel, gbc, 2, "Generi:", generiScrollPane);
        addFormRowWithConstraints(formPanel, gbc, 3, "Valutazione:", valutazioneCombo);
        addFormRowWithConstraints(formPanel, gbc, 4, "Stato lettura:", statoCombo);
        addFormRowWithConstraints(formPanel, gbc, 5, "ISBN (13 cifre):", isbnField);

        return formPanel;
    }

    private void addFormRowWithConstraints(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel(labelText, SwingConstants.RIGHT), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);
    }

    private boolean mostraDialog(JPanel formPanel, boolean isModifica) {
        int result = JOptionPane.showConfirmDialog(
                SwingUtilities.getWindowAncestor(tabella),
                formPanel,
                isModifica ? "Modifica Libro" : "Crea Nuovo Libro",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        return result == JOptionPane.OK_OPTION;
    }

    private Libro creaLibroDaiCampi(Component parent, JTextField titoloField, JTextField autoriField,
                                    JList<InfoExtra.GenereLibro> generiList, JComboBox<Integer> valutazioneCombo,
                                    JComboBox<InfoExtra.StatoLettura> statoCombo, JTextField isbnField) {
        try {
            String titolo = titoloField.getText().trim();
            String isbn = isbnField.getText().trim();
            Integer stelle = (Integer) valutazioneCombo.getSelectedItem();

            Set<Autore> autori = parseAutori(autoriField.getText());
            Set<InfoExtra.GenereLibro> generi = parseGeneri(generiList);
            InfoExtra.Valutazione valutazione = stelle != null ? new InfoExtra.Valutazione(stelle) : null;
            InfoExtra.StatoLettura stato = (InfoExtra.StatoLettura) statoCombo.getSelectedItem();

            return new ConcreteLibro(isbn, titolo, valutazione, stato, autori, generi);

        } catch (Exception ex) {
            mostraErrore(parent, ex.getMessage());
            return null;
        }
    }

    private Set<Autore> parseAutori(String autoriText) {
        return Arrays.stream(autoriText.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(this::creaAutore)
                .collect(Collectors.toSet());
    }

    private Autore creaAutore(String nomeCompleto) {
        String[] parts = nomeCompleto.split(" ", 2);
        return new Autore(parts[0], parts.length > 1 ? parts[1] : "");
    }

    private Set<InfoExtra.GenereLibro> parseGeneri(JList<InfoExtra.GenereLibro> generiList) {
        return generiList.getSelectedValuesList().isEmpty()
                ? null
                : new HashSet<>(generiList.getSelectedValuesList());
    }


    public TipoOrdinamento selezionaOrdinamento(TipoOrdinamento[] opzioni) {
        if (opzioni == null || opzioni.length == 0)
            throw new IllegalArgumentException("Nessuna opzione di ordinamento disponibile");

        TipoOrdinamento selezionato = (TipoOrdinamento) JOptionPane.showInputDialog(
                tabella,
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

    private void mostraMessaggio(Component parent, String messaggio) {
        JOptionPane.showMessageDialog(parent, messaggio, "Informazione", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostraWarning(Component parent, String messaggio) {
        JOptionPane.showMessageDialog(parent, messaggio, "Attenzione", JOptionPane.WARNING_MESSAGE);
    }

    private void mostraErrore(Component parent, String messaggio) {
        JOptionPane.showMessageDialog(parent, messaggio, "Errore", JOptionPane.ERROR_MESSAGE);
    }

    private String getValueOrDefault(Object value) {
        return value != null ? value.toString() : "--";
    }

    private String getListAsString(java.util.List<?> list) {
        return !list.isEmpty() ?
                list.stream().map(Object::toString).collect(Collectors.joining(", ")) : "--";
    }

    private String getValutazioneString(Object valutazione) {
        return valutazione != null ? valutazione + " stelle" : "--";
    }

    private String formattaEnum(String nomeEnum) {
        String[] nomeEnumSplit = nomeEnum.split("_");
        StringBuilder sb = new StringBuilder();
        for (String s : nomeEnumSplit) {
            sb.append(s.substring(0, 1).toUpperCase());
            sb.append(s.substring(1).toLowerCase());
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    @Override
    public void update() {
        SwingUtilities.invokeLater(() -> {
            List<Libro> libri = hubLibreria.vistaLibreriaCorrente();
            DefaultTableModel nuovoModello = creaModello(libri);
            tabella.setModel(nuovoModello);
        });
    }

}
