package utils;

import java.math.BigInteger;

import logic.core.ClearType;
import logic.core.Judgement;
import logic.core.PlayResult;

public final class ScoreUtil {
    private ScoreUtil() {}

    public static final int CRITICAL_PERFECT_WEIGHT = 112_00;
    public static final int PERFECT_WEIGHT = 100_00;
    public static final int GOOD_WEIGHT = 69_42;

    public static final int MAX_SCORE = 101_0000;

    public static String getRank(int score) {
        if (score >= 1_005_000) {
            return "SSS+";
        } else if (score >= 1_000_000) {
            return "SSS";
        } else if (score >= 995_000) {
            return "SS+";
        } else if (score >= 990_000) {
            return "SS";
        } else if (score >= 980_000) {
            return "S+";
        } else if (score >= 970_000) {
            return "S";
        } else if (score >= 940_000) {
            return "AAA";
        } else if (score >= 900_000) {
            return "AA";
        } else if (score >= 850_000) {
            return "A";
        } else if (score >= 800_000) {
            return "B+";
        } else if (score >= 750_000) {
            return "B";
        } else if (score >= 700_000) {
            return "C+";
        } else if (score >= 650_000) {
            return "C";
        } else if (score >= 600_000) {
            return "D+";
        } else if (score >= 550_000) {
            return "D";
        } else {
            return "F";
        }
    }

    private static int safeScaling(int score, int maxScore, int scale) {
        var numerator = BigInteger.valueOf(score);
        var denominator = BigInteger.valueOf(maxScore);
        var multiplier = BigInteger.valueOf(scale);

        var result = numerator.multiply(multiplier).divide(denominator);

        return result.intValue();
    }

    public static int calculatePartialScore(Judgement judgement) {
        int rawScore = (judgement.getPlatinumCriticalPerfect()
                + judgement.getCriticalPerfect())
                * ScoreUtil.CRITICAL_PERFECT_WEIGHT
                + judgement.getPerfect() * ScoreUtil.PERFECT_WEIGHT
                + judgement.getGood() * ScoreUtil.GOOD_WEIGHT;

        return rawScore;
    }

    /**
     * Get partial score as percentage from 101.00%
     */
    public static double calculatePartialScoreAsPercentage(
            Judgement judgement) {
        int rawScore = ScoreUtil.calculatePartialScore(judgement);
        int totalScore = judgement.getTotalNotes()
                * ScoreUtil.CRITICAL_PERFECT_WEIGHT;

        return rawScore * 101.0 / totalScore;
    }

    public static int calculateRawScore(PlayResult playResult) {
        int rawScore = ScoreUtil.calculatePartialScore(playResult.getTap())
                + ScoreUtil.calculatePartialScore(playResult.getHold())
                + ScoreUtil.calculatePartialScore(playResult.getFlick());

        return rawScore;
    }

    public static int calculateScore(PlayResult playResult) {
        int theoreticalScore = playResult.getTotalNotes()
                * ScoreUtil.CRITICAL_PERFECT_WEIGHT;

        int rawScore = ScoreUtil.calculateRawScore(playResult);

        return ScoreUtil.safeScaling(rawScore, theoreticalScore,
                ScoreUtil.MAX_SCORE);
    }

    public static int calculateScoreTypeMinus(PlayResult playResult) {
        int rawScore = ScoreUtil.calculateRawScore(playResult);

        int remainingNotes = playResult.getTotalNotes()
                - playResult.getPlayedNotes();

        int bestCaseScore = rawScore
                + remainingNotes * ScoreUtil.CRITICAL_PERFECT_WEIGHT;
        int theoreticalScore = playResult.getTotalNotes()
                * ScoreUtil.CRITICAL_PERFECT_WEIGHT;

        return ScoreUtil.safeScaling(bestCaseScore, theoreticalScore,
                ScoreUtil.MAX_SCORE);
    }

    public static int calculatePartialPlatinumScore(Judgement judgement) {
        return judgement.getPlatinumCriticalPerfect() * 2
                + judgement.getCriticalPerfect();
    }

    public static int calculatePlatinumScore(PlayResult playResult) {
        return ScoreUtil.calculatePartialPlatinumScore(playResult.getTap())
                + ScoreUtil.calculatePartialPlatinumScore(playResult.getHold())
                + ScoreUtil
                        .calculatePartialPlatinumScore(playResult.getFlick());
    }

    public static int calculateMaxPlatinumScore(PlayResult playResult) {
        return playResult.getTotalNotes() * 2;
    }

    public static int calculateCurrentMaxPlatinumScore(PlayResult playResult) {
        return playResult.getPlayedNotes() * 2;
    }

    public static boolean isFullCombo(PlayResult playResult) {
        return playResult.getTotalNotes() == playResult.getMaxCombo();
    }

    public static boolean isAllPerfect(PlayResult playResult) {
        return ScoreUtil.isFullCombo(playResult) && playResult
                .getTotalNotes() == (playResult.getPlatinumCriticalPerfect()
                        + playResult.getCriticalPerfect()
                        + playResult.getPerfect());
    }

    public static ClearType getClearType(PlayResult playResult) {
        return ScoreUtil.isAllPerfect(playResult) ? ClearType.ALL_PERFECT
                : ScoreUtil.isFullCombo(playResult) ? ClearType.FULL_COMBO
                        : ClearType.CLEAR;
    }
}
