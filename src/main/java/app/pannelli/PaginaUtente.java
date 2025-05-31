package app.pannelli;

import base.libro.ConcreteLibro;
import base.libro.Libro;
import base.utility.Autore;
import base.utility.InfoExtra;
import controllo.libreria.HubLibreria;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
        frame.setSize(800, 600);

        TableModel model = creaModello(hubLibreria.vistaLibreriaCorrente()):
        this.tabella =new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabella);

        JPanel panelloPulsanti = creaPulsanti(
                frame,
                tabella,
                hubLibreria.vistaLibreriaCorrente(),
                hubLibreria.utenteCorrente()
        );

        // Configurazione del layout
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panelloPulsanti, BorderLayout.SOUTH);

        // Registro l'Observer
        hubLibreria.aggiungiObserver(this);

        // Visualizzazione del frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private TableModel creaModello(List<Libro> libri){
        String[] colonne = {"Titolo", "Autori", "Generi", "Valutazione", "Stato lettura", "ISBN"};
        Object[][] dati = new Object[libri.size()][colonne.length];

        for(int i = 0; i < libri.size(); i++){
            Libro libro = libri.get(i);
            dati[i][0] = libro.getTitolo() != null ? libro.getTitolo() : "--";
            dati[i][1] = !libro.getAutori().isEmpty() ? libro.getAutori().stream().map(Object::toString).collect(Collectors.joining(", ")) : "--";
            dati[i][2] = !libro.getGeneri().isEmpty() ? libro.getGeneri().stream().map(Object::toString).collect(Collectors.joining(", ")) : "--";
            dati[i][3] = libro.getValutazione() != null ? libro.getValutazione().getStelle() + " stelle" : "--";
            dati[i][4] = libro.getStatoLettura() != null ? libro.getStatoLettura().toString() : "--";
            dati[i][5] = libro.getIsbn() != null ? libro.getIsbn() : "--";
        }


    }

    public Libro creaLibro(JTable tabella, Libro libro) {
        // Creazione campi
        JTextField titoloField = new JTextField(20);
        JTextField autoriField = new JTextField(20);
        JList<InfoExtra.GenereLibro> generiList = new JList<>(InfoExtra.GenereLibro.values());
        generiList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane generiScrollPane = new JScrollPane(generiList);
        JComboBox<Integer> valutazioneCombo = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        JComboBox<InfoExtra.StatoLettura> statoCombo = new JComboBox<>(InfoExtra.StatoLettura.values());
        JTextField isbnField = new JTextField(13);

        // Popola campi se libro esistente
        if (libro != null) {
            titoloField.setText(libro.getTitolo() != null ? libro.getTitolo() : "");
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
            statoCombo.setSelectedItem(libro.getStatoLettura());
            isbnField.setText(libro.getIsbn() != null ? libro.getIsbn() : "");
        }

        // Validazione ISBN
        isbnField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) || isbnField.getText().length() >= 13) {
                    e.consume();
                }
            }
        });

        // Creazione form
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.add(new JLabel("Titolo:", SwingConstants.RIGHT));
        formPanel.add(titoloField);
        formPanel.add(new JLabel("Autori:", SwingConstants.RIGHT));
        formPanel.add(autoriField);
        formPanel.add(new JLabel("Generi:", SwingConstants.RIGHT));
        formPanel.add(generiScrollPane);
        formPanel.add(new JLabel("Valutazione:", SwingConstants.RIGHT));
        formPanel.add(valutazioneCombo);
        formPanel.add(new JLabel("Stato lettura:", SwingConstants.RIGHT));
        formPanel.add(statoCombo);
        formPanel.add(new JLabel("ISBN (13 cifre):", SwingConstants.RIGHT));
        formPanel.add(isbnField);

        // Mostra dialog
        int result = JOptionPane.showConfirmDialog(
                SwingUtilities.getWindowAncestor(tabella),
                formPanel,
                libro != null ? "Modifica Libro" : "Crea Nuovo Libro",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // Processa risultato
        if (result == JOptionPane.OK_OPTION) {
            try {
                String titolo = titoloField.getText().trim();
                String isbn = isbnField.getText().trim();
                Integer stelle = (Integer) valutazioneCombo.getSelectedItem();

                Set<Autore> autori = Arrays.stream(autoriField.getText().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(nomeCompleto -> {
                            String[] parts = nomeCompleto.split(" ", 2);
                            return new Autore(parts[0], parts.length > 1 ? parts[1] : "");
                        })
                        .collect(Collectors.toSet());

                Set<InfoExtra.GenereLibro> generi = generiList.getSelectedValuesList().isEmpty()
                        ? null
                        : new HashSet<>(generiList.getSelectedValuesList());

                InfoExtra.Valutazione valutazione = stelle != null ? new InfoExtra.Valutazione(stelle) : null;
                InfoExtra.StatoLettura stato = (InfoExtra.StatoLettura) statoCombo.getSelectedItem();

                return new ConcreteLibro(isbn, titolo, valutazione, stato, autori, generi);
            } catch (Exception ex) {
                mostraErrore(tabella, ex.getMessage());
            }
        }
        return null;
    }

    private void mostraMessaggio(Component parent, String messaggio) {
        JOptionPane.showMessageDialog(parent, messaggio, "Informazione", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostraErrore(Component parent, String messaggio) {
        JOptionPane.showMessageDialog(parent, messaggio, "Errore", JOptionPane.ERROR_MESSAGE);
    }


    @Override
    public void update() {

    }
}
