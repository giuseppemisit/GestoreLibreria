package struttura.informazioni;

public class AttributiSoggettivi {

    public enum StatoDiLettura{
        LETTO,
        DA_LEGGERE,
        IN_LETTURA
    }

    public enum Valutazione{
        UNA_STELLA(1), DUE_STELLE(2), TRE_STELLE(3), QUATTRO_STELLE(4), CINQUE_STELLE(5);
        private final int stelle;

        Valutazione(int stelle) {
            this.stelle = stelle;
        }

        public int getStelle() {
            return stelle;
        }
    }

}