package com.doubleshan.scenery.vo;

import java.util.Map;

public class RankingEntryVO {
    private String userId;
    private int score;
    private int rank;

    public static RankingEntryVO from(Map<String, Object> m) {
        if (m == null)
            return null;
        RankingEntryVO vo = new RankingEntryVO();
        vo.userId = (String) m.getOrDefault("user_id", "");
        Object scoreObj = m.get("score");
        vo.score = scoreObj instanceof Number ? ((Number) scoreObj).intValue() : 0;
        Object rankObj = m.get("rank");
        vo.rank = rankObj instanceof Number ? ((Number) rankObj).intValue() : 0;
        return vo;
    }

    public String getUserId() {
        return userId;
    }

    public int getScore() {
        return score;
    }

    public int getRank() {
        return rank;
    }
}
