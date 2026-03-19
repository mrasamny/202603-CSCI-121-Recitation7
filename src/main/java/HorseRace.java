/**
 * Represents a race between two horses.
 * The race proceeds in rounds until at least one horse reaches the finish line.
 */
public class HorseRace {

    private Horse horse1;
    private Horse horse2;

    /**
     * Creates a HorseRace between two horses.
     */
    public HorseRace(Horse horse1, Horse horse2) {
        this.horse1 = horse1;
        this.horse2 = horse2;
    }

    public Horse getHorse1() {
        return horse1;
    }

    public Horse getHorse2() {
        return horse2;
    }

    /**
     * Runs the race. Each round both horses advance until at least one finishes.
     * Updates win/loss/draw statistics for both horses.
     * @return the winning horse, or null if it is a draw
     */
    public Horse race() {
        // TODO: replace this line with your code.
    }
}
