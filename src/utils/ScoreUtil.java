package utils;

import java.math.BigInteger;

import logic.core.PlayResult;

public final class ScoreUtil {
    private ScoreUtil() {}

    public static final int CRITICAL_PERFECT_WEIGHT = 112_00;
    public static final int PERFECT_WEIGHT = 100_00;
    public static final int GOOD_WEIGHT = 69_42;

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

        var result = numerator.divide(denominator).multiply(multiplier);

        return result.intValue();
    }

    public static int calculateScore(PlayResult playResult) {
        int theoreticalScore = playResult.getTotalNotes()
                * ScoreUtil.CRITICAL_PERFECT_WEIGHT;

        int rawScore = (playResult.getPlatinumCriticalPerfect()
                + playResult.getCriticalPerfect())
                * ScoreUtil.CRITICAL_PERFECT_WEIGHT
                + playResult.getPerfect() * ScoreUtil.PERFECT_WEIGHT
                + playResult.getGood() * ScoreUtil.GOOD_WEIGHT;

        return ScoreUtil.safeScaling(rawScore, theoreticalScore, 1_010_000);
    }

    public static int calculatePlatinumScore(PlayResult playResult) {
        return playResult.getPlatinumCriticalPerfect() * 2
                + playResult.getCriticalPerfect();
    }

    public static int calculateMaxPlatinumScore(PlayResult playResult) {
        return playResult.getTotalNotes() * 2;
    }
}
