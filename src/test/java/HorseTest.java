import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Horse Class Tests")
public class HorseTest {

    private Horse horse;

    @BeforeEach
    public void setUp() {
        horse = new Horse("Thunderbolt");
    }

    // -------------------------------------------------------------------------
    // advance() Tests
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("advance(): position increases after one advance call")
    public void testAdvanceIncreasesPosition() {
        int before = horse.getPosition();
        int roll = horse.advance();
        assertEquals(before + roll, horse.getPosition());
    }

    @Test
    @DisplayName("advance(): roll is always between 1 and 6 inclusive")
    public void testAdvanceRollInRange() {
        for (int i = 0; i < 200; i++) {
            Horse h = new Horse("Test");
            int roll = h.advance();
            assertTrue(roll >= 1 && roll <= 6,
                    "Expected roll between 1 and 6 but got: " + roll);
        }
    }

    @Test
    @DisplayName("advance(): position accumulates correctly across multiple calls")
    public void testAdvanceAccumulatesPosition() {
        int total = 0;
        for (int i = 0; i < 5; i++) {
            total += horse.advance();
        }
        assertEquals(total, horse.getPosition());
    }

    @Test
    @DisplayName("advance(): returns the same value added to position")
    public void testAdvanceReturnValueMatchesPositionChange() {
        int before = horse.getPosition();
        int roll = horse.advance();
        int after = horse.getPosition();
        assertEquals(roll, after - before);
    }

    // -------------------------------------------------------------------------
    // hasFinished() Tests
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("hasFinished(): returns false at starting position 0")
    public void testHasFinishedFalseAtStart() {
        assertFalse(horse.hasFinished());
    }

    @Test
    @DisplayName("hasFinished(): returns false when position is below finish line")
    public void testHasFinishedFalseBelowFinishLine() {
        while (horse.getPosition() < Horse.FINISH_LINE - 6) {
            horse.advance();
        }
        // Position is still safely below finish line
        if (horse.getPosition() < Horse.FINISH_LINE) {
            assertFalse(horse.hasFinished());
        }
    }

    @Test
    @DisplayName("hasFinished(): returns true once finish line is reached or passed")
    public void testHasFinishedTrueAtOrPastFinishLine() {
        while (!horse.hasFinished()) {
            horse.advance();
        }
        assertTrue(horse.hasFinished());
        assertTrue(horse.getPosition() >= Horse.FINISH_LINE);
    }

    @Test
    @DisplayName("hasFinished(): FINISH_LINE constant is 20")
    public void testFinishLineConstant() {
        assertEquals(20, Horse.FINISH_LINE);
    }

    // -------------------------------------------------------------------------
    // resetPosition() Tests
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("resetPosition(): sets position back to 0 after advancing")
    public void testResetPositionToZero() {
        horse.advance();
        horse.advance();
        horse.resetPosition();
        assertEquals(0, horse.getPosition());
    }

    @Test
    @DisplayName("resetPosition(): hasFinished() returns false after reset")
    public void testResetPositionClearsFinished() {
        while (!horse.hasFinished()) {
            horse.advance();
        }
        horse.resetPosition();
        assertFalse(horse.hasFinished());
    }

    @Test
    @DisplayName("resetPosition(): does not affect wins")
    public void testResetPositionDoesNotAffectWins() {
        horse.recordWin();
        horse.advance();
        horse.resetPosition();
        assertEquals(1, horse.getWins());
    }

    @Test
    @DisplayName("resetPosition(): does not affect losses")
    public void testResetPositionDoesNotAffectLosses() {
        horse.recordLoss();
        horse.advance();
        horse.resetPosition();
        assertEquals(1, horse.getLosses());
    }

    @Test
    @DisplayName("resetPosition(): does not affect draws")
    public void testResetPositionDoesNotAffectDraws() {
        horse.recordDraw();
        horse.advance();
        horse.resetPosition();
        assertEquals(1, horse.getDraws());
    }

    // -------------------------------------------------------------------------
    // recordWin() / recordLoss() / recordDraw() Tests
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("recordWin(): increments wins by 1")
    public void testRecordWinIncrementsOnce() {
        horse.recordWin();
        assertEquals(1, horse.getWins());
    }

    @Test
    @DisplayName("recordWin(): increments wins correctly across multiple calls")
    public void testRecordWinMultipleTimes() {
        horse.recordWin();
        horse.recordWin();
        horse.recordWin();
        assertEquals(3, horse.getWins());
    }

    @Test
    @DisplayName("recordWin(): does not affect losses or draws")
    public void testRecordWinDoesNotAffectOtherStats() {
        horse.recordWin();
        assertEquals(0, horse.getLosses());
        assertEquals(0, horse.getDraws());
    }

    @Test
    @DisplayName("recordLoss(): increments losses by 1")
    public void testRecordLossIncrementsOnce() {
        horse.recordLoss();
        assertEquals(1, horse.getLosses());
    }

    @Test
    @DisplayName("recordLoss(): increments losses correctly across multiple calls")
    public void testRecordLossMultipleTimes() {
        horse.recordLoss();
        horse.recordLoss();
        assertEquals(2, horse.getLosses());
    }

    @Test
    @DisplayName("recordLoss(): does not affect wins or draws")
    public void testRecordLossDoesNotAffectOtherStats() {
        horse.recordLoss();
        assertEquals(0, horse.getWins());
        assertEquals(0, horse.getDraws());
    }

    @Test
    @DisplayName("recordDraw(): increments draws by 1")
    public void testRecordDrawIncrementsOnce() {
        horse.recordDraw();
        assertEquals(1, horse.getDraws());
    }

    @Test
    @DisplayName("recordDraw(): increments draws correctly across multiple calls")
    public void testRecordDrawMultipleTimes() {
        horse.recordDraw();
        horse.recordDraw();
        assertEquals(2, horse.getDraws());
    }

    @Test
    @DisplayName("recordDraw(): does not affect wins or losses")
    public void testRecordDrawDoesNotAffectOtherStats() {
        horse.recordDraw();
        assertEquals(0, horse.getWins());
        assertEquals(0, horse.getLosses());
    }

    @Test
    @DisplayName("Stats: wins, losses, and draws accumulate independently")
    public void testMixedStatsAccumulateIndependently() {
        horse.recordWin();
        horse.recordWin();
        horse.recordLoss();
        horse.recordDraw();
        horse.recordDraw();
        horse.recordDraw();
        assertEquals(2, horse.getWins());
        assertEquals(1, horse.getLosses());
        assertEquals(3, horse.getDraws());
    }
}
