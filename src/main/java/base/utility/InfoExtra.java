package base.utility;

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Valutazione)) return false;
            Valutazione that = (Valutazione) o;
            return stelle == that.stelle;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(stelle);
        }

        @Override
        public String toString() {
            return stelle + " stelle";
        }
    }

}