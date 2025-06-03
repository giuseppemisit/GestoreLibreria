import base.libreria.LibreriaJson;
import base.libro.ConcreteLibro;
import base.libro.Libro;
import base.utility.Autore;
import base.utility.InfoExtra;
import controllo.utenti.ConcreteUserManager;
import controllo.utenti.UserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class LibreriaJsonTest {

    private LibreriaJson libreria;
    private final String utenteTest = "marioRossi";

    // Oggetti libro per i test
    private Libro libro1;
    private Libro libro2;
    private Libro libro3;

    @BeforeEach
    void setUp() {

        UserManager userManager = new ConcreteUserManager();
        if(userManager.esisteUtente(utenteTest))
            userManager.eliminaUtente(utenteTest);

        userManager.creaNuovoUtente(utenteTest);
        libreria = new LibreriaJson(utenteTest);

        // Creazione libri di test
        Set<Autore> autori1 = new HashSet<>();
        autori1.add(new Autore("Alessandro", "Manzoni"));
        Set<InfoExtra.GenereLibro> generi1 = new HashSet<>();
        generi1.add(InfoExtra.GenereLibro.ROMANTICO);

        Set<Autore> autori2 = new HashSet<>();
        autori2.add(new Autore("Italo", "Calvino"));
        Set<InfoExtra.GenereLibro> generi2 = new HashSet<>();
        generi2.add(InfoExtra.GenereLibro.NARRATIVA);
        generi2.add(InfoExtra.GenereLibro.STORICO);

        Set<Autore> autori3 = new HashSet<>();
        autori3.add(new Autore("Umberto", "Eco"));
        autori3.add(new Autore("Co", "Autore"));
        Set<InfoExtra.GenereLibro> generi3 = new HashSet<>();
        generi3.add(InfoExtra.GenereLibro.SAGGIO);

        libro1 = new ConcreteLibro("9788804123451", "I Promessi Sposi", new InfoExtra.Valutazione(5), InfoExtra.StatoLettura.LETTO, autori1, generi1);
        libro2 = new ConcreteLibro("9788804678902", "Il Barone Rampante", new InfoExtra.Valutazione(4), InfoExtra.StatoLettura.IN_LETTURA, autori2, generi2);
        libro3 = new ConcreteLibro("9788804111113", "Il Nome della Rosa", null, InfoExtra.StatoLettura.DA_LEGGERE, autori3, generi3);
    }

    @Test
    @DisplayName("Test costruttore - verifica inizializzazione corretta")
    void testCostruttore() {
        LibreriaJson nuovaLibreria = new LibreriaJson("test.user");

        assertNotNull(nuovaLibreria);
        assertEquals("test.user", nuovaLibreria.utenteCorrente());
        assertNotNull(nuovaLibreria.getLibriUtente());
        assertTrue(nuovaLibreria.getLibriUtente().isEmpty());
    }

    @Test
    @DisplayName("Test utenteCorrente - ritorna utente corretto")
    void testUtenteCorrente() {
        assertEquals(utenteTest, libreria.utenteCorrente());
    }

    @Test
    @DisplayName("Test getLibriUtente - lista inizialmente vuota")
    void testGetLibriUtenteVuota() {
        List<Libro> libri = libreria.getLibriUtente();

        assertNotNull(libri);
        assertTrue(libri.isEmpty());
    }

    @Test
    @DisplayName("Test getLibriUtente - ritorna copia della lista")
    void testGetLibriUtenteRestituisceCopia() {
        libreria.aggiungiLibro(libro1);

        List<Libro> libri1 = libreria.getLibriUtente();
        List<Libro> libri2 = libreria.getLibriUtente();

        // Verifica che siano oggetti diversi (copie)
        assertNotSame(libri1, libri2);
        assertEquals(libri1, libri2);

        // Modifica una copia non deve influenzare l'originale
        libri1.clear();
        assertFalse(libreria.getLibriUtente().isEmpty());
    }

    @Test
    @DisplayName("Test aggiungiLibro - aggiunta con successo")
    void testAggiungiLibroSuccesso() {
        boolean risultato = libreria.aggiungiLibro(libro1);

        assertTrue(risultato);
        assertEquals(1, libreria.getLibriUtente().size());
        assertTrue(libreria.getLibriUtente().contains(libro1));
    }

    @Test
    @DisplayName("Test aggiungiLibro - libro duplicato")
    void testAggiungiLibroDuplicato() {
        libreria.aggiungiLibro(libro1);
        boolean risultato = libreria.aggiungiLibro(libro1);

        assertFalse(risultato);
        assertEquals(1, libreria.getLibriUtente().size());
    }

    @Test
    @DisplayName("Test aggiungiLibro - libri multipli")
    void testAggiungiLibriMultipli() {
        assertTrue(libreria.aggiungiLibro(libro1));
        assertTrue(libreria.aggiungiLibro(libro2));
        assertTrue(libreria.aggiungiLibro(libro3));

        assertEquals(3, libreria.getLibriUtente().size());
        assertTrue(libreria.getLibriUtente().contains(libro1));
        assertTrue(libreria.getLibriUtente().contains(libro2));
        assertTrue(libreria.getLibriUtente().contains(libro3));
    }

    @Test
    @DisplayName("Test rimuoviLibro - rimozione con successo")
    void testRimuoviLibroSuccesso() {
        libreria.aggiungiLibro(libro1);
        boolean risultato = libreria.rimuoviLibro(libro1);

        assertTrue(risultato);
        assertTrue(libreria.getLibriUtente().isEmpty());
    }

    @Test
    @DisplayName("Test rimuoviLibro - libro non presente")
    void testRimuoviLibroNonPresente() {
        boolean risultato = libreria.rimuoviLibro(libro1);

        assertFalse(risultato);
        assertTrue(libreria.getLibriUtente().isEmpty());
    }

    @Test
    @DisplayName("Test rimuoviLibro - da lista con più elementi")
    void testRimuoviLibroDaListaMultipla() {
        libreria.aggiungiLibro(libro1);
        libreria.aggiungiLibro(libro2);
        libreria.aggiungiLibro(libro3);

        boolean risultato = libreria.rimuoviLibro(libro2);

        assertTrue(risultato);
        assertEquals(2, libreria.getLibriUtente().size());
        assertFalse(libreria.getLibriUtente().contains(libro2));
        assertTrue(libreria.getLibriUtente().contains(libro1));
        assertTrue(libreria.getLibriUtente().contains(libro3));
    }

    @Test
    @DisplayName("Test modificaLibro - modifica con successo")
    void testModificaLibroSuccesso() {
        libreria.aggiungiLibro(libro1);

        Set<Autore> autori = new HashSet<>();
        autori.add(new Autore("Umberto", "Eco"));
        Set<InfoExtra.GenereLibro> generi = new HashSet<>();
        generi.add(InfoExtra.GenereLibro.SAGGIO);
        Libro libroModificato = new ConcreteLibro("9788845292613", "Il Nome della Rosa - Edizione Speciale", null, InfoExtra.StatoLettura.DA_LEGGERE, autori, generi);

        boolean risultato = libreria.modificaLibro(libro1, libroModificato);

        assertTrue(risultato);
        assertEquals(1, libreria.getLibriUtente().size());
        assertFalse(libreria.getLibriUtente().contains(libro1));
        assertTrue(libreria.getLibriUtente().contains(libroModificato));
    }

    @Test
    @DisplayName("Test modificaLibro - libro originale null")
    void testModificaLibroOriginaleNull() {
        boolean risultato = libreria.modificaLibro(null, libro2);

        assertFalse(risultato);
    }

    @Test
    @DisplayName("Test modificaLibro - libro modificato null")
    void testModificaLibroModificatoNull() {
        libreria.aggiungiLibro(libro1);
        boolean risultato = libreria.modificaLibro(libro1, null);

        assertFalse(risultato);
        assertTrue(libreria.getLibriUtente().contains(libro1));
    }

    @Test
    @DisplayName("Test modificaLibro - libro originale non presente")
    void testModificaLibroNonPresente() {
        libreria.aggiungiLibro(libro1);
        boolean risultato = libreria.modificaLibro(libro2, libro3);

        assertFalse(risultato);
        assertEquals(1, libreria.getLibriUtente().size());
        assertTrue(libreria.getLibriUtente().contains(libro1));
    }

    @Test
    @DisplayName("Test toString - formato corretto")
    void testToString() {
        String stringResult = libreria.toString();

        assertNotNull(stringResult);
        assertTrue(stringResult.contains("Libreria{"));
        assertTrue(stringResult.contains("storageService="));
        assertTrue(stringResult.contains("utenteCorrente='" + utenteTest + "'"));
        assertTrue(stringResult.contains("libriUtente="));
    }

    @Test
    @DisplayName("Test equals - oggetti uguali")
    void testEqualsOggettiUguali() {
        LibreriaJson libreria1 = new LibreriaJson(utenteTest);
        LibreriaJson libreria2 = new LibreriaJson(utenteTest);

        libreria1.aggiungiLibro(libro1);
        libreria2.aggiungiLibro(libro1);

        assertEquals(libreria1, libreria2);
    }

    @Test
    @DisplayName("Test equals - stesso oggetto")
    void testEqualsStessoOggetto() {
        assertEquals(libreria, libreria);
    }

    @Test
    @DisplayName("Test equals - oggetto null")
    void testEqualsOggettoNull() {
        assertNotEquals(null, libreria);
    }

    @Test
    @DisplayName("Test equals - oggetto di tipo diverso")
    void testEqualsTipoDiverso() {
        assertNotEquals("stringa", libreria);
    }

    @Test
    @DisplayName("Test equals - utenti diversi")
    void testEqualsUtentiDiversi() {
        LibreriaJson altraLibreria = new LibreriaJson("altro.utente");

        assertNotEquals(libreria, altraLibreria);
    }

    @Test
    @DisplayName("Test equals - libri diversi")
    void testEqualsLibriDiversi() {
        LibreriaJson libreria1 = new LibreriaJson(utenteTest);
        LibreriaJson libreria2 = new LibreriaJson(utenteTest);

        libreria1.aggiungiLibro(libro1);
        libreria2.aggiungiLibro(libro2);

        assertNotEquals(libreria1, libreria2);
    }

    @Test
    @DisplayName("Test hashCode - coerenza con equals")
    void testHashCodeCoerenza() {
        LibreriaJson libreria1 = new LibreriaJson(utenteTest);
        LibreriaJson libreria2 = new LibreriaJson(utenteTest);

        libreria1.aggiungiLibro(libro1);
        libreria2.aggiungiLibro(libro1);

        assertEquals(libreria1, libreria2);
        assertEquals(libreria1.hashCode(), libreria2.hashCode());
    }

    @Test
    @DisplayName("Test hashCode - oggetti diversi")
    void testHashCodeOggettiDiversi() {
        LibreriaJson libreria1 = new LibreriaJson(utenteTest);
        LibreriaJson libreria2 = new LibreriaJson("altro.utente");

        assertNotEquals(libreria1.hashCode(), libreria2.hashCode());
    }

    @Test
    @DisplayName("Test thread safety - accesso concorrente")
    void testThreadSafety() throws InterruptedException {
        final int numeroThread = 10;
        final int numeroOperazioni = 10;
        Thread[] threads = new Thread[numeroThread];

        String[] isbnBase = {
                "9788804123451", "9788845292613", "9788804679394", "9788845292620", "9788817123456",
                "9788804567890", "9788845234567", "9788804345678", "9788845456789", "9788804678901"
        };

        for (int i = 0; i < numeroThread; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < numeroOperazioni; j++) {
                    Set<Autore> autori = new HashSet<>();
                    autori.add(new Autore("Autore" + threadId, "Cognome" + j));
                    Set<InfoExtra.GenereLibro> generi = new HashSet<>();
                    generi.add(InfoExtra.GenereLibro.ROMANTICO);

                    String isbnValido = isbnBase[threadId].substring(0, 12) + ((j % 10));

                    Libro libro = new ConcreteLibro(isbnValido,
                            "Titolo" + threadId + "-" + j,
                            new InfoExtra.Valutazione(3),
                            InfoExtra.StatoLettura.DA_LEGGERE,
                            autori,
                            generi);

                    libreria.aggiungiLibro(libro);
                }
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        // Verifico che tutti i libri siano stati aggiunti
        assertEquals(numeroThread * numeroOperazioni, libreria.getLibriUtente().size());
    }
}
