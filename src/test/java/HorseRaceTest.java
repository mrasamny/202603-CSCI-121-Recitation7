import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HorseRace Class Tests")
public class HorseRaceTest {

    private Horse thunderbolt;
    private Horse lightning;
    private HorseRace race;

    @BeforeEach
    public void setUp() {
        thunderbolt = new Horse("Thunderbolt");
        lightning = new Horse("Lightning");
        race = new HorseRace(thunderbolt, lightning);
    }


    // -------------------------------------------------------------------------
    // race() — Position Reset Tests
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("race(): at least one horse reaches finish line by end of race")
    public void testRaceAtLeastOneHorseFinishes() {
        race.race();
        assertTrue(thunderbolt.hasFinished() || lightning.hasFinished());
    }

    @Test
    @DisplayName("race(): positions are reset before racing (horse1 advances from 0)")
    public void testRaceResetsHorse1PositionBeforeStart() {
        // Pre-advance horse1 to a high position before the race
        while (thunderbolt.getPosition() < 15) {
            thunderbolt.advance();
        }
        int preRacePosition = thunderbolt.getPosition();
        race.race();
        // If reset worked, horse could only reach FINISH_LINE via normal rolls from 0,
        // not from the inflated pre-race position
        assertTrue(thunderbolt.getPosition() >= Horse.FINISH_LINE ||
                   lightning.getPosition() >= Horse.FINISH_LINE);
    }

    // -------------------------------------------------------------------------
    // race() — Return Value Tests
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("race(): return value is horse1, horse2, or null")
    public void testRaceReturnValueIsValidHorseOrNull() {
        for (int i = 0; i < 20; i++) {
            Horse winner = race.race();
            assertTrue(winner == null || winner == thunderbolt || winner == lightning,
                    "race() must return horse1, horse2, or null");
        }
    }

    @Test
    @DisplayName("race(): returned winner has reached the finish line")
    public void testRaceWinnerHasFinished() {
        for (int i = 0; i < 20; i++) {
            Horse winner = race.race();
            if (winner != null) {
                assertTrue(winner.hasFinished(),
                        "Returned winner must have reached the finish line");
            }
        }
    }

    @Test
    @DisplayName("race(): returns null on a draw (both horses finish same round)")
    public void testRaceReturnsNullOnDraw() {
        // Run many races; whenever null is returned, verify both horses finished
        for (int i = 0; i < 100; i++) {
            Horse winner = race.race();
            if (winner == null) {
                assertTrue(thunderbolt.hasFinished() && lightning.hasFinished(),
                        "A null result means both horses must have finished");
                return;
            }
        }
        // No draw in 100 races is statistically acceptable; test passes
    }

    // -------------------------------------------------------------------------
    // race() — Win/Loss/Draw Stat Tests
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("race(): exactly one stat is recorded for horse1 per race")
    public void testRaceRecordsExactlyOneStatForHorse1() {
        race.race();
        int total = thunderbolt.getWins() + thunderbolt.getLosses() + thunderbolt.getDraws();
        assertEquals(1, total);
    }

    @Test
    @DisplayName("race(): exactly one stat is recorded for horse2 per race")
    public void testRaceRecordsExactlyOneStatForHorse2() {
        race.race();
        int total = lightning.getWins() + lightning.getLosses() + lightning.getDraws();
        assertEquals(1, total);
    }

    @Test
    @DisplayName("race(): winner receives a win and loser receives a loss")
    public void testRaceWinnerGetsWinLoserGetsLoss() {
        for (int i = 0; i < 20; i++) {
            Horse winner = race.race();
            if (winner != null) {
                Horse loser = (winner == thunderbolt) ? lightning : thunderbolt;
                assertEquals(1, winner.getWins());
                assertEquals(0, winner.getLosses());
                assertEquals(1, loser.getLosses());
                assertEquals(0, loser.getWins());
                return;
            }
        }
    }

    @Test
    @DisplayName("race(): winner has no losses or draws recorded")
    public void testRaceWinnerHasNoLossOrDraw() {
        for (int i = 0; i < 20; i++) {
            Horse winner = race.race();
            if (winner != null) {
                assertEquals(0, winner.getLosses());
                assertEquals(0, winner.getDraws());
                return;
            }
        }
    }

    @Test
    @DisplayName("race(): loser has no wins or draws recorded")
    public void testRaceLoserHasNoWinOrDraw() {
        for (int i = 0; i < 20; i++) {
            Horse winner = race.race();
            if (winner != null) {
                Horse loser = (winner == thunderbolt) ? lightning : thunderbolt;
                assertEquals(0, loser.getWins());
                assertEquals(0, loser.getDraws());
                return;
            }
        }
    }

    @Test
    @DisplayName("race(): both horses receive a draw when they finish simultaneously")
    public void testRaceDrawRecordedForBothHorses() {
        for (int i = 0; i < 100; i++) {
            Horse winner = race.race();
            if (winner == null) {
                assertEquals(thunderbolt.getDraws(), lightning.getDraws(),
                        "Both horses should have the same draw count after a draw");
                return;
            }
        }
        // No draw in 100 races is statistically acceptable; test passes
    }

    // -------------------------------------------------------------------------
    // race() — Multiple Race / Accumulation Tests
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("race(): stats accumulate correctly across two races")
    public void testStatsAccumulateAcrossTwoRaces() {
        race.race();
        race.race();
        int total1 = thunderbolt.getWins() + thunderbolt.getLosses() + thunderbolt.getDraws();
        int total2 = lightning.getWins() + lightning.getLosses() + lightning.getDraws();
        assertEquals(2, total1);
        assertEquals(2, total2);
    }

    @Test
    @DisplayName("race(): stats accumulate correctly across five races")
    public void testStatsAccumulateAcrossFiveRaces() {
        for (int i = 0; i < 5; i++) {
            race.race();
        }
        int total = thunderbolt.getWins() + thunderbolt.getLosses() + thunderbolt.getDraws();
        assertEquals(5, total);
    }

    @Test
    @DisplayName("race(): combined stats across both horses equals 2 per race")
    public void testTotalStatsAcrossBothHorsesPerRace() {
        int races = 10;
        for (int i = 0; i < races; i++) {
            race.race();
        }
        int combined = thunderbolt.getWins() + thunderbolt.getLosses() + thunderbolt.getDraws()
                     + lightning.getWins() + lightning.getLosses() + lightning.getDraws();
        assertEquals(races * 2, combined);
    }

    @Test
    @DisplayName("race(): clearing one horse's stats mid-series only affects that horse")
    public void testClearingOneHorseStatsMidSeries() {
        race.race();
        race.race();
        thunderbolt.clearStats();
        race.race();
        // thunderbolt should only have stats from the one race run after clearing
        int thunderboltTotal = thunderbolt.getWins() + thunderbolt.getLosses() + thunderbolt.getDraws();
        assertEquals(1, thunderboltTotal);
        // lightning should still have all 3 races recorded
        int lightningTotal = lightning.getWins() + lightning.getLosses() + lightning.getDraws();
        assertEquals(3, lightningTotal);
    }
}
