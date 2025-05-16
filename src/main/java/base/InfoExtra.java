package base;

public class InfoExtra {

    public enum StatoLettura{
        LETTO,
        DA_LEGGERE,
        IN_LETTURA
    }

    public enum GenereLibro {
        NARRATIVA,
        FANTASY,
        FANTASCIENZA,
        GIALLO,
        THRILLER,
        HORROR,
        ROMANTICO,
        STORICO,
        AVVENTURA,
        MANGA,
        BIOGRAFIA,
        SAGGIO,
        MANUALE,
        SCIENZA,
        RELIGIONE,
        POLITICA,
        PSICOLOGIA,
        VIAGGIO,
        BAMBINI,
        RAGAZZI
    }

    public static class Valutazione{
        private final int stelle;
        public Valutazione(int stelle){
            if(stelle < 0 || stelle > 5) throw new IllegalArgumentException("Stelle non validi");
            this.stelle = stelle;
        }
        public int getStelle() {
            return stelle;
        }
    }

}