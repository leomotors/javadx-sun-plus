package utils;

import logic.core.Judgement;
import logic.core.PlayResult;

public class ScoreUtilMock {
    public static PlayResult createNoFastLateTapOnlyPlayResult(
            int platinumCriticalPerfect, int criticalPerfect, int perfect,
            int good, int miss, int maxCombo) {
        return new PlayResult() {

            @Override
            public int getMaxCombo() {
                return maxCombo;
            }

            @Override
            public Judgement getTap() {
                return ScoreUtilMock.createNoFastLatePlayJudgement(
                        platinumCriticalPerfect, criticalPerfect, perfect, good,
                        miss);
            }

            @Override
            public Judgement getHold() {
                return ScoreUtilMock.createNoFastLatePlayJudgement(0, 0, 0, 0,
                        0);
            }

            @Override
            public Judgement getFlick() {
                return ScoreUtilMock.createNoFastLatePlayJudgement(0, 0, 0, 0,
                        0);
            }

        };
    }

    public static Judgement createNoFastLatePlayJudgement(
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

    public static PlayResult getMockPlayResult() {
        var mockTapJudgement = new Judgement() {

            @Override
            public int getPlatinumCriticalPerfect() {
                return 220;
            }

            @Override
            public int getCriticalPerfect() {
                return 116;
            }

            @Override
            public int getPerfect() {
                return 36;
            }

            @Override
            public int getGood() {
                return 4;
            }

            @Override
            public int getMiss() {
                return 3;
            }

            @Override
            public int getFastCriticalPerfect() {
                return 54;
            }

            @Override
            public int getLateCriticalPerfect() {
                return 62;
            }

            @Override
            public int getFastPerfect() {
                return 13;
            }

            @Override
            public int getLatePerfect() {
                return 23;
            }

            @Override
            public int getFastGood() {
                return 1;
            }

            @Override
            public int getLateGood() {
                return 3;
            }
        };

        var mockHoldJudgement = new Judgement() {

            @Override
            public int getPlatinumCriticalPerfect() {
                return 400;
            }

            @Override
            public int getCriticalPerfect() {
                return 0;
            }

            @Override
            public int getPerfect() {
                return 12;
            }

            @Override
            public int getGood() {
                return 5;
            }

            @Override
            public int getMiss() {
                return 2;
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

        var mockFlickJudgement = new Judgement() {

            @Override
            public int getPlatinumCriticalPerfect() {
                return 22;
            }

            @Override
            public int getCriticalPerfect() {
                return 9;
            }

            @Override
            public int getPerfect() {
                return 4;
            }

            @Override
            public int getGood() {
                return 2;
            }

            @Override
            public int getMiss() {
                return 1;
            }

            @Override
            public int getFastCriticalPerfect() {
                return 4;
            }

            @Override
            public int getLateCriticalPerfect() {
                return 5;
            }

            @Override
            public int getFastPerfect() {
                return 1;
            }

            @Override
            public int getLatePerfect() {
                return 1;
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

        var mockPlayResult = new PlayResult() {

            @Override
            public int getMaxCombo() {
                return 555;
            }

            @Override
            public Judgement getTap() {
                return mockTapJudgement;
            }

            @Override
            public Judgement getHold() {
                return mockHoldJudgement;
            }

            @Override
            public Judgement getFlick() {
                return mockFlickJudgement;
            }
        };

        return mockPlayResult;
    }
}
