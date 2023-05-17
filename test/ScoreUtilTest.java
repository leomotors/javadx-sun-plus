import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import logic.core.Judgement;
import logic.core.PlayResult;
import utils.ScoreUtil;

class ScoreUtilTest {
    @Test
    void testCalculateRank() {
        assertEquals(ScoreUtil.getRank(1_005_000), "SSS+");
        assertEquals(ScoreUtil.getRank(1_006_969), "SSS+");

        assertEquals(ScoreUtil.getRank(690_420), "C");
        assertEquals(ScoreUtil.getRank(970_000), "S");
        assertEquals(ScoreUtil.getRank(969_999), "AAA");
    }

    private static PlayResult createNoFastLateTapOnlyPlayResult(
            int platinumCriticalPerfect, int criticalPerfect, int perfect,
            int good, int miss, int maxCombo) {
        return new PlayResult() {

            @Override
            public int getMaxCombo() {
                return maxCombo;
            }

            @Override
            public Judgement getTap() {
                return ScoreUtilTest.createNoFastLatePlayJudgement(
                        platinumCriticalPerfect, criticalPerfect, perfect, good,
                        miss);
            }

            @Override
            public Judgement getHold() {
                return ScoreUtilTest.createNoFastLatePlayJudgement(0, 0, 0, 0,
                        0);
            }

            @Override
            public Judgement getFlick() {
                return ScoreUtilTest.createNoFastLatePlayJudgement(0, 0, 0, 0,
                        0);
            }

        };
    }

    private static Judgement createNoFastLatePlayJudgement(
            int platinumCriticalPerfect, int criticalPerfect, int perfect,
            int good, int miss) {
        return new Judgement() {

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
        var score = ScoreUtilTest.createNoFastLateTapOnlyPlayResult(
                69, 0, 0, 0, 0, 69);

        assertEquals(1_010_000, ScoreUtil.calculateScore(score));
        assertEquals(138, ScoreUtil.calculatePlatinumScore(score));
        assertEquals(138,
                ScoreUtil.calculateMaxPlatinumScore(score));
    }

    @Test
    void testCalculateScore1010000FastLate() {
        var score = ScoreUtilTest.createNoFastLateTapOnlyPlayResult(
                42, 27, 0, 0, 0, 69);

        assertEquals(1_010_000, ScoreUtil.calculateScore(score));
        assertEquals(111, ScoreUtil.calculatePlatinumScore(score));
        assertEquals(138,
                ScoreUtil.calculateMaxPlatinumScore(score));
    }

    @Test
    void testAllPerfect() {
        var score = ScoreUtilTest.createNoFastLateTapOnlyPlayResult(
                0, 0, 69, 0, 0, 69);

        assertEquals(901_785, ScoreUtil.calculateScore(score));
        assertEquals(0, ScoreUtil.calculatePlatinumScore(score));
        assertEquals(138,
                ScoreUtil.calculateMaxPlatinumScore(score));
    }

    @Test
    void testAllPerfectWithCritical() {
        var score = ScoreUtilTest.createNoFastLateTapOnlyPlayResult(
                42, 10, 17, 0, 0, 69);

        assertEquals(983_338, ScoreUtil.calculateScore(score));
        assertEquals(94, ScoreUtil.calculatePlatinumScore(score));
        assertEquals(138,
                ScoreUtil.calculateMaxPlatinumScore(score));
    }

    @Test
    void testComputedProperties() {
        var judgementTap = ScoreUtilTest.createNoFastLatePlayJudgement(
                100, 200, 300, 400, 500);

        var judgementHold = ScoreUtilTest.createNoFastLatePlayJudgement(
                10, 20, 30, 40, 50);

        var judgementFlick = ScoreUtilTest.createNoFastLatePlayJudgement(
                1, 2, 3, 4, 5);

        var score = new PlayResult() {

            @Override
            public int getMaxCombo() {
                return 69;
            }

            @Override
            public Judgement getTap() {
                return judgementTap;
            }

            @Override
            public Judgement getHold() {
                return judgementHold;
            }

            @Override
            public Judgement getFlick() {
                return judgementFlick;
            }
        };

        assertEquals(111, score.getPlatinumCriticalPerfect());
        assertEquals(222, score.getCriticalPerfect());
        assertEquals(333, score.getPerfect());
        assertEquals(444, score.getGood());
        assertEquals(555, score.getMiss());
    }
}
