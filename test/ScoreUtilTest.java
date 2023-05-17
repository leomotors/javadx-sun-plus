import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import logic.core.PlayResult;
import utils.ScoreUtil;

class ScoreUtilTest {
    private static PlayResult createNoFastLatePlayResult(
            int platinumCriticalPerfect, int criticalPerfect, int perfect,
            int good, int miss, int maxCombo) {
        return new PlayResult() {

            @Override
            public int getMaxCombo() {
                return maxCombo;
            }

            @Override
            public int getPlatinumCriticalPerfect() {
                return platinumCriticalPerfect;
            }

            @Override
            public int getCriticalPerfect() {
                return criticalPerfect;
            }

            @Override
            public int getPerfect() {
                return perfect;
            }

            @Override
            public int getGood() {
                return good;
            }

            @Override
            public int getMiss() {
                return miss;
            }

            @Override
            public int getFastCriticalPerfect() {
                return 0;
            }

            @Override
            public int getLateCriticalPerfect() {
                return 0;
            }

            @Override
            public int getFastPerfect() {
                return 0;
            }

            @Override
            public int getLatePerfect() {
                return 0;
            }

            @Override
            public int getFastGood() {
                return 0;
            }

            @Override
            public int getLateGood() {
                return 0;
            }
        };
    }

    @Test
    void testCalculateScoreTheoreticalValue() {
        var score = ScoreUtilTest.createNoFastLatePlayResult(
                69, 0, 0, 0, 0, 69);

        assertEquals(1_010_000, ScoreUtil.calculateScore(score));
        assertEquals(138, ScoreUtil.calculatePlatinumScore(score));
        assertEquals(138,
                ScoreUtil.calculateMaxPlatinumScore(score));
    }

    @Test
    void testCalculateScore1010000FastLate() {
        var score = ScoreUtilTest.createNoFastLatePlayResult(
                42, 27, 0, 0, 0, 69);

        assertEquals(1_010_000, ScoreUtil.calculateScore(score));
        assertEquals(111, ScoreUtil.calculatePlatinumScore(score));
        assertEquals(138,
                ScoreUtil.calculateMaxPlatinumScore(score));
    }

    @Test
    void testAllPerfect() {
        var score = ScoreUtilTest.createNoFastLatePlayResult(
                0, 0, 69, 0, 0, 69);

        assertEquals(901_785, ScoreUtil.calculateScore(score));
        assertEquals(0, ScoreUtil.calculatePlatinumScore(score));
        assertEquals(138,
                ScoreUtil.calculateMaxPlatinumScore(score));
    }

    @Test
    void testAllPerfectWithCritical() {
        var score = ScoreUtilTest.createNoFastLatePlayResult(
                42, 10, 17, 0, 0, 69);

        assertEquals(983_338, ScoreUtil.calculateScore(score));
        assertEquals(94, ScoreUtil.calculatePlatinumScore(score));
        assertEquals(138,
                ScoreUtil.calculateMaxPlatinumScore(score));
    }
}
